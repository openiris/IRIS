package etri.sdn.controller.protocol;

import org.projectfloodlight.openflow.protocol.OFDescStatsReply;
import org.projectfloodlight.openflow.protocol.OFFeaturesReply;

public class SwitchInfo {
	private OFDescStatsReply	descStatsReply = null;
	private OFFeaturesReply	featuresReply = null;
	
	public SwitchInfo() {
		// does nothing
	}
	
	public void setDescStatsReply(OFDescStatsReply r) {
		this.descStatsReply = r;
	}
	
	public OFDescStatsReply getDescStatsReply() {
		return this.descStatsReply;
	}
	
	public void setFeaturesReply(OFFeaturesReply r) {
		this.featuresReply = r;
	}
	
	public OFFeaturesReply getFeaturesReply() {
		return this.featuresReply;
	}
	
	/*
	 * convenient functions
	 */ 
	
	public long getBuffers() {
		if ( this.featuresReply != null ) {
			this.featuresReply.getNBuffers();
		}
		return 0;
	}
}