package etri.sdn.controller.module.statemanager;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.projectfloodlight.openflow.protocol.OFFactories;
import org.projectfloodlight.openflow.protocol.OFFeaturesReply;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFPortDescStatsRequest;
import org.projectfloodlight.openflow.protocol.OFStatsReply;
import org.projectfloodlight.openflow.protocol.OFStatsRequestFlags;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.util.HexString;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

import etri.sdn.controller.protocol.OFProtocol;
import etri.sdn.controller.protocol.io.IOFSwitch;
import etri.sdn.controller.util.StackTrace;

public class RESTFeaturesApi extends Restlet {
	private OFProtocol protocol;
	private OFMStateManager manager;
	private List<SimpleModule> modules;
	
	public RESTFeaturesApi(OFProtocol protocol,
						   OFMStateManager manager,
						   List<SimpleModule> modules) {
		this.protocol = protocol;
		this.manager = manager;
		this.modules = modules;
		
		System.out.println(protocol + " " + manager + " " + modules);
	}
	
	private HashMap<String, OFPortDescStatsReply> get13features(IOFSwitch sw) {
		OFPortDescStatsRequest pdreq = OFFactories.getFactory(sw.getVersion()).portDescStatsRequest(EnumSet.noneOf(OFStatsRequestFlags.class));
		
		// the switch supports version 1.3
		List<OFStatsReply> reply = protocol.getSwitchStatistics( sw, pdreq );
		
		if ( reply != null && ! reply.isEmpty() ) {
			HashMap<String, OFPortDescStatsReply> result = new HashMap<String, OFPortDescStatsReply>();
			result.put( sw.getStringId(), (OFPortDescStatsReply) reply.remove(0) );
			
			return result;
		} else {
			return null;
		}
	}
	
	private HashMap<String, OFFeaturesReply> get10features(IOFSwitch sw) {
		// this switch version is lower than 1.3. It does not support OFStatisticsPortDescRequest
		OFFeaturesReply reply = protocol.getFeaturesReply(sw);
		
		HashMap<String, OFFeaturesReply> result = new HashMap<String, OFFeaturesReply>();
		result.put( sw.getStringId(), reply );
		
		return result;
	}
	
	private void sendReply(Object result, Response response) {
		// create an object mapper.
		ObjectMapper om = new ObjectMapper();
		for ( SimpleModule module : this.modules ) {
			om.registerModule(module);
		}

		try {
			String r = om./*writerWithDefaultPrettyPrinter().*/writeValueAsString(result);
			response.setEntity(r, MediaType.APPLICATION_JSON);
		} catch (Exception e) {
			OFMStateManager.logger.error("error={}", StackTrace.of(e));
			return;
		}
	}
	
	@Override
	public void handle(Request request, Response response) {
		
		List<IOFSwitch> switches = new LinkedList<>();
		String switchIdStr = (String) request.getAttributes().get("switchid");
		if ( switchIdStr.equals("all") ) {
			switches.addAll( manager.getController().getSwitches() );
		} else {
			Long switchId = HexString.toLong(switchIdStr);
			IOFSwitch sw = manager.getController().getSwitch(switchId);
			if ( sw == null ) {
				return;		// switch is not completely set up.
			}
			switches.add( sw );
		}
		
		List<Object> results = new LinkedList<>();
		for ( IOFSwitch sw : switches ) {
			
			if ( sw.getVersion() == OFVersion.OF_10 ) {
				Object res = this.get10features( sw );
				if ( res != null ) {
					results.add( res );
				}
			} else {
				Object res = this.get13features( sw );
				if ( res != null ) {
					results.add( res );
				}
			}
		}
		
		if ( results.size() == 1 && !switchIdStr.equals("all") ) {
			sendReply( results.get(0), response );
		} else {
			sendReply( results, response );
		}
	}
}