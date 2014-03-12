package etri.sdn.controller.module.flowcache;

import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.util.HexString;

public class OFMatchWithSwDpid {
    protected Match match;
    protected long  switchDataPathId;

    public OFMatchWithSwDpid(Match ofm, long swDpid) {
    	//	this.ofMatch = (OFMatch) ofm.clone();
		this.match = ofm;
    	this.switchDataPathId = swDpid;
    }
    public Match getOfMatch() {
		return match;
	}

	public void setOfMatch(Match ofMatch) {
		//	this.ofMatch = (OFMatch) ofMatch.clone();
		this.match = ofMatch;
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
        return "OFMatchWithSwDpid [" + HexString.toHexString(switchDataPathId) + match + "]";
    }
}
