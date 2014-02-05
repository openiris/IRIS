package etri.sdn.controller.module.flowcache;

import org.openflow.protocol.ver1_0.messages.OFMatch;
import org.openflow.util.HexString;

public class OFMatchWithSwDpid {
    protected OFMatch ofMatch;
    protected long  switchDataPathId;

    public OFMatchWithSwDpid() {
    	this.ofMatch = new OFMatch();
    	this.switchDataPathId = 0;
    }
    
    public OFMatchWithSwDpid(OFMatch ofm, long swDpid) {
    	//	this.ofMatch = (OFMatch) ofm.clone();
		this.ofMatch = new OFMatch(ofm);
    	this.switchDataPathId = swDpid;
    }
    public OFMatch getOfMatch() {
		return ofMatch;
	}

	public void setOfMatch(OFMatch ofMatch) {
		//	this.ofMatch = (OFMatch) ofMatch.clone();
		this.ofMatch = new OFMatch(ofMatch);
	}

	public long getSwitchDataPathId() {
        return this.switchDataPathId;
    }

    public OFMatchWithSwDpid setSwitchDataPathId(long dpid) {
        this.switchDataPathId = dpid;
        return this;
    }
    
    @Override
    public String toString() {
        return "OFMatchWithSwDpid [" + HexString.toHexString(switchDataPathId) + ofMatch + "]";
    }
}
