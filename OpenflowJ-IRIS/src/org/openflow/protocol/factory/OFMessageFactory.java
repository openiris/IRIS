package org.openflow.protocol.factory;

import org.openflow.protocol.interfaces.*;

public class OFMessageFactory {

	/**
	 * String such as '1.0' and '1.3' are allowed.
	 */
	String version;
	
	public OFMessageFactory(String version) {
		this.version = version;
	}
	
	public OFActionVendor createActionVendor() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionVendor();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionVendor.");
		}
	}
	
	public OFActionVendor asActionVendor(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionVendor) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionVendor.");
		}
	}
	
	public OFError createError() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFError();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFError();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFError.");
		}
	}
	
	public OFError asError(OFMessage m) {
		switch(version) {
		case "1.0": return (OFError) m;
		case "1.3": return (OFError) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFError.");
		}
	}
	
	public OFStatisticsRequest createStatisticsRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsRequest.");
		}
	}
	
	public OFStatisticsRequest asStatisticsRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsRequest.");
		}
	}
	
	public OFStatisticsAggregateRequest createStatisticsAggregateRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateRequest.");
		}
	}
	
	public OFStatisticsAggregateRequest asStatisticsAggregateRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsAggregateRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateRequest.");
		}
	}
	
	public OFActionSetNwTtl createActionSetNwTtl() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionSetNwTtl();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwTtl.");
		}
	}
	
	public OFActionSetNwTtl asActionSetNwTtl(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionSetNwTtl) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwTtl.");
		}
	}
	
	public OFActionCopyTtlIn createActionCopyTtlIn() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionCopyTtlIn();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionCopyTtlIn.");
		}
	}
	
	public OFActionCopyTtlIn asActionCopyTtlIn(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionCopyTtlIn) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionCopyTtlIn.");
		}
	}
	
	public OFTableFeaturePropertyWildcards createTableFeaturePropertyWildcards() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWildcards();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWildcards.");
		}
	}
	
	public OFTableFeaturePropertyWildcards asTableFeaturePropertyWildcards(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyWildcards) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWildcards.");
		}
	}
	
	public OFStatisticsVendorRequest createStatisticsVendorRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsVendorRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsVendorRequest.");
		}
	}
	
	public OFStatisticsVendorRequest asStatisticsVendorRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsVendorRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsVendorRequest.");
		}
	}
	
	public OFMatch createMatch() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFMatch();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMatch();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatch.");
		}
	}
	
	public OFMatch asMatch(OFMessage m) {
		switch(version) {
		case "1.0": return (OFMatch) m;
		case "1.3": return (OFMatch) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatch.");
		}
	}
	
	public OFActionGroup createActionGroup() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionGroup();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionGroup.");
		}
	}
	
	public OFActionGroup asActionGroup(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionGroup) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionGroup.");
		}
	}
	
	public OFTableFeaturePropertyExperimenterMiss createTableFeaturePropertyExperimenterMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyExperimenterMiss();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyExperimenterMiss.");
		}
	}
	
	public OFTableFeaturePropertyExperimenterMiss asTableFeaturePropertyExperimenterMiss(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyExperimenterMiss) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyExperimenterMiss.");
		}
	}
	
	public OFFlowModifyStrict createFlowModifyStrict() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowModifyStrict();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowModifyStrict();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowModifyStrict.");
		}
	}
	
	public OFFlowModifyStrict asFlowModifyStrict(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowModifyStrict) m;
		case "1.3": return (OFFlowModifyStrict) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowModifyStrict.");
		}
	}
	
	public OFInstructionWriteMetadata createInstructionWriteMetadata() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionWriteMetadata();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionWriteMetadata.");
		}
	}
	
	public OFInstructionWriteMetadata asInstructionWriteMetadata(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstructionWriteMetadata) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionWriteMetadata.");
		}
	}
	
	public OFTableFeaturePropertyApplyActions createTableFeaturePropertyApplyActions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplyActions();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplyActions.");
		}
	}
	
	public OFTableFeaturePropertyApplyActions asTableFeaturePropertyApplyActions(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyApplyActions) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplyActions.");
		}
	}
	
	public OFSetConfig createSetConfig() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFSetConfig();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFSetConfig();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFSetConfig.");
		}
	}
	
	public OFSetConfig asSetConfig(OFMessage m) {
		switch(version) {
		case "1.0": return (OFSetConfig) m;
		case "1.3": return (OFSetConfig) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFSetConfig.");
		}
	}
	
	public OFMultipartGroupFeaturesRequest createMultipartGroupFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupFeaturesRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupFeaturesRequest.");
		}
	}
	
	public OFMultipartGroupFeaturesRequest asMultipartGroupFeaturesRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartGroupFeaturesRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupFeaturesRequest.");
		}
	}
	
	public OFMultipartTableRequest createMultipartTableRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartTableRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableRequest.");
		}
	}
	
	public OFMultipartTableRequest asMultipartTableRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartTableRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableRequest.");
		}
	}
	
	public OFTableFeaturePropertyNextTables createTableFeaturePropertyNextTables() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyNextTables();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyNextTables.");
		}
	}
	
	public OFTableFeaturePropertyNextTables asTableFeaturePropertyNextTables(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyNextTables) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyNextTables.");
		}
	}
	
	public OFActionOpaqueEnqueue createActionOpaqueEnqueue() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionOpaqueEnqueue();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionOpaqueEnqueue.");
		}
	}
	
	public OFActionOpaqueEnqueue asActionOpaqueEnqueue(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionOpaqueEnqueue) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionOpaqueEnqueue.");
		}
	}
	
	public OFTableFeaturePropertyWriteSetfieldMiss createTableFeaturePropertyWriteSetfieldMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteSetfieldMiss();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteSetfieldMiss.");
		}
	}
	
	public OFTableFeaturePropertyWriteSetfieldMiss asTableFeaturePropertyWriteSetfieldMiss(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyWriteSetfieldMiss) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteSetfieldMiss.");
		}
	}
	
	public OFMatchOxm createMatchOxm() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMatchOxm();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatchOxm.");
		}
	}
	
	public OFMatchOxm asMatchOxm(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMatchOxm) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatchOxm.");
		}
	}
	
	public OFActionStripVlan createActionStripVlan() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionStripVlan();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionStripVlan.");
		}
	}
	
	public OFActionStripVlan asActionStripVlan(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionStripVlan) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionStripVlan.");
		}
	}
	
	public OFMultipartGroupRequest createMultipartGroupRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupRequest.");
		}
	}
	
	public OFMultipartGroupRequest asMultipartGroupRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartGroupRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupRequest.");
		}
	}
	
	public OFOxm createOxm() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFOxm();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFOxm.");
		}
	}
	
	public OFOxm asOxm(OFMessage m) {
		switch(version) {
		case "1.3": return (OFOxm) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFOxm.");
		}
	}
	
	public OFQueuePropertyMaxRate createQueuePropertyMaxRate() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueuePropertyMaxRate();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyMaxRate.");
		}
	}
	
	public OFQueuePropertyMaxRate asQueuePropertyMaxRate(OFMessage m) {
		switch(version) {
		case "1.3": return (OFQueuePropertyMaxRate) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyMaxRate.");
		}
	}
	
	public OFActionId createActionId() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionId();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionId.");
		}
	}
	
	public OFActionId asActionId(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionId) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionId.");
		}
	}
	
	public OFQueueGetConfigRequest createQueueGetConfigRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueueGetConfigRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueueGetConfigRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueGetConfigRequest.");
		}
	}
	
	public OFQueueGetConfigRequest asQueueGetConfigRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFQueueGetConfigRequest) m;
		case "1.3": return (OFQueueGetConfigRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueGetConfigRequest.");
		}
	}
	
	public OFTableFeaturePropertyInstructionsMiss createTableFeaturePropertyInstructionsMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyInstructionsMiss();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyInstructionsMiss.");
		}
	}
	
	public OFTableFeaturePropertyInstructionsMiss asTableFeaturePropertyInstructionsMiss(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyInstructionsMiss) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyInstructionsMiss.");
		}
	}
	
	public OFActionSetNwDst createActionSetNwDst() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetNwDst();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwDst.");
		}
	}
	
	public OFActionSetNwDst asActionSetNwDst(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetNwDst) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwDst.");
		}
	}
	
	public OFMultipartPortDescRequest createMultipartPortDescRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartPortDescRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortDescRequest.");
		}
	}
	
	public OFMultipartPortDescRequest asMultipartPortDescRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartPortDescRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortDescRequest.");
		}
	}
	
	public OFPortStatus createPortStatus() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPortStatus();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPortStatus();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortStatus.");
		}
	}
	
	public OFPortStatus asPortStatus(OFMessage m) {
		switch(version) {
		case "1.0": return (OFPortStatus) m;
		case "1.3": return (OFPortStatus) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortStatus.");
		}
	}
	
	public OFMultipartTableReply createMultipartTableReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartTableReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableReply.");
		}
	}
	
	public OFMultipartTableReply asMultipartTableReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartTableReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableReply.");
		}
	}
	
	public OFStatisticsDescReply createStatisticsDescReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsDescReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescReply.");
		}
	}
	
	public OFStatisticsDescReply asStatisticsDescReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsDescReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescReply.");
		}
	}
	
	public OFTableFeaturePropertyNextTablesMiss createTableFeaturePropertyNextTablesMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyNextTablesMiss();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyNextTablesMiss.");
		}
	}
	
	public OFTableFeaturePropertyNextTablesMiss asTableFeaturePropertyNextTablesMiss(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyNextTablesMiss) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyNextTablesMiss.");
		}
	}
	
	public OFActionSetDlSrc createActionSetDlSrc() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetDlSrc();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetDlSrc.");
		}
	}
	
	public OFActionSetDlSrc asActionSetDlSrc(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetDlSrc) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetDlSrc.");
		}
	}
	
	public OFFlowAdd createFlowAdd() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowAdd();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowAdd();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowAdd.");
		}
	}
	
	public OFFlowAdd asFlowAdd(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowAdd) m;
		case "1.3": return (OFFlowAdd) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowAdd.");
		}
	}
	
	public OFMultipartMeterFeaturesReply createMultipartMeterFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterFeaturesReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterFeaturesReply.");
		}
	}
	
	public OFMultipartMeterFeaturesReply asMultipartMeterFeaturesReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartMeterFeaturesReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterFeaturesReply.");
		}
	}
	
	public OFActionSetField createActionSetField() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionSetField();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetField.");
		}
	}
	
	public OFActionSetField asActionSetField(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionSetField) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetField.");
		}
	}
	
	public OFMeterBandStats createMeterBandStats() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterBandStats();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandStats.");
		}
	}
	
	public OFMeterBandStats asMeterBandStats(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterBandStats) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandStats.");
		}
	}
	
	public OFFeaturesRequest createFeaturesRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFeaturesRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFeaturesRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFeaturesRequest.");
		}
	}
	
	public OFFeaturesRequest asFeaturesRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFeaturesRequest) m;
		case "1.3": return (OFFeaturesRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFeaturesRequest.");
		}
	}
	
	public OFStatistics createStatistics() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatistics();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatistics.");
		}
	}
	
	public OFStatistics asStatistics(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatistics) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatistics.");
		}
	}
	
	public OFActionPushMpls createActionPushMpls() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPushMpls();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPushMpls.");
		}
	}
	
	public OFActionPushMpls asActionPushMpls(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionPushMpls) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPushMpls.");
		}
	}
	
	public OFActionSetVlanId createActionSetVlanId() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetVlanId();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetVlanId.");
		}
	}
	
	public OFActionSetVlanId asActionSetVlanId(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetVlanId) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetVlanId.");
		}
	}
	
	public OFMultipartMeterConfigRequest createMultipartMeterConfigRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterConfigRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterConfigRequest.");
		}
	}
	
	public OFMultipartMeterConfigRequest asMultipartMeterConfigRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartMeterConfigRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterConfigRequest.");
		}
	}
	
	public OFMultipartTableFeaturesRequest createMultipartTableFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartTableFeaturesRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableFeaturesRequest.");
		}
	}
	
	public OFMultipartTableFeaturesRequest asMultipartTableFeaturesRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartTableFeaturesRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableFeaturesRequest.");
		}
	}
	
	public OFMultipartGroupFeaturesReply createMultipartGroupFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupFeaturesReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupFeaturesReply.");
		}
	}
	
	public OFMultipartGroupFeaturesReply asMultipartGroupFeaturesReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartGroupFeaturesReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupFeaturesReply.");
		}
	}
	
	public OFStatisticsTableReply createStatisticsTableReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsTableReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableReply.");
		}
	}
	
	public OFStatisticsTableReply asStatisticsTableReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsTableReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableReply.");
		}
	}
	
	public OFMultipartReply createMultipartReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartReply.");
		}
	}
	
	public OFMultipartReply asMultipartReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartReply.");
		}
	}
	
	public OFStatisticsPortRequest createStatisticsPortRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsPortRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortRequest.");
		}
	}
	
	public OFStatisticsPortRequest asStatisticsPortRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsPortRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortRequest.");
		}
	}
	
	public OFGetAsyncRequest createGetAsyncRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGetAsyncRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetAsyncRequest.");
		}
	}
	
	public OFGetAsyncRequest asGetAsyncRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFGetAsyncRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetAsyncRequest.");
		}
	}
	
	public OFGetAsyncReply createGetAsyncReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGetAsyncReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetAsyncReply.");
		}
	}
	
	public OFGetAsyncReply asGetAsyncReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFGetAsyncReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetAsyncReply.");
		}
	}
	
	public OFStatisticsFlowReply createStatisticsFlowReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowReply.");
		}
	}
	
	public OFStatisticsFlowReply asStatisticsFlowReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsFlowReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowReply.");
		}
	}
	
	public OFBarrierRequest createBarrierRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFBarrierRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFBarrierRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBarrierRequest.");
		}
	}
	
	public OFBarrierRequest asBarrierRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFBarrierRequest) m;
		case "1.3": return (OFBarrierRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBarrierRequest.");
		}
	}
	
	public OFActionPushVlan createActionPushVlan() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPushVlan();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPushVlan.");
		}
	}
	
	public OFActionPushVlan asActionPushVlan(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionPushVlan) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPushVlan.");
		}
	}
	
	public OFPacketOut createPacketOut() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPacketOut();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPacketOut();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketOut.");
		}
	}
	
	public OFPacketOut asPacketOut(OFMessage m) {
		switch(version) {
		case "1.0": return (OFPacketOut) m;
		case "1.3": return (OFPacketOut) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketOut.");
		}
	}
	
	public OFGetConfigRequest createGetConfigRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFGetConfigRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGetConfigRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetConfigRequest.");
		}
	}
	
	public OFGetConfigRequest asGetConfigRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFGetConfigRequest) m;
		case "1.3": return (OFGetConfigRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetConfigRequest.");
		}
	}
	
	public OFInstructionMeter createInstructionMeter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionMeter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionMeter.");
		}
	}
	
	public OFInstructionMeter asInstructionMeter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstructionMeter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionMeter.");
		}
	}
	
	public OFVendor createVendor() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFVendor();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFVendor.");
		}
	}
	
	public OFVendor asVendor(OFMessage m) {
		switch(version) {
		case "1.0": return (OFVendor) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFVendor.");
		}
	}
	
	public OFMatchStandard createMatchStandard() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMatchStandard();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatchStandard.");
		}
	}
	
	public OFMatchStandard asMatchStandard(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMatchStandard) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatchStandard.");
		}
	}
	
	public OFGroupDescStatsEntry createGroupDescStatsEntry() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGroupDescStatsEntry();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGroupDescStatsEntry.");
		}
	}
	
	public OFGroupDescStatsEntry asGroupDescStatsEntry(OFMessage m) {
		switch(version) {
		case "1.3": return (OFGroupDescStatsEntry) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGroupDescStatsEntry.");
		}
	}
	
	public OFMultipartFlowReply createMultipartFlowReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartFlowReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartFlowReply.");
		}
	}
	
	public OFMultipartFlowReply asMultipartFlowReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartFlowReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartFlowReply.");
		}
	}
	
	public OFMultipartGroupReply createMultipartGroupReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupReply.");
		}
	}
	
	public OFMultipartGroupReply asMultipartGroupReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartGroupReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupReply.");
		}
	}
	
	public OFActionSetDlDst createActionSetDlDst() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetDlDst();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetDlDst.");
		}
	}
	
	public OFActionSetDlDst asActionSetDlDst(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetDlDst) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetDlDst.");
		}
	}
	
	public OFActionSetTpDst createActionSetTpDst() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetTpDst();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetTpDst.");
		}
	}
	
	public OFActionSetTpDst asActionSetTpDst(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetTpDst) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetTpDst.");
		}
	}
	
	public OFRoleRequest createRoleRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFRoleRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFRoleRequest.");
		}
	}
	
	public OFRoleRequest asRoleRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFRoleRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFRoleRequest.");
		}
	}
	
	public OFActionSetTpSrc createActionSetTpSrc() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetTpSrc();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetTpSrc.");
		}
	}
	
	public OFActionSetTpSrc asActionSetTpSrc(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetTpSrc) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetTpSrc.");
		}
	}
	
	public OFFlowMod createFlowMod() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowMod();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowMod();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowMod.");
		}
	}
	
	public OFFlowMod asFlowMod(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowMod) m;
		case "1.3": return (OFFlowMod) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowMod.");
		}
	}
	
	public OFPortStatsEntry createPortStatsEntry() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPortStatsEntry();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPortStatsEntry();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortStatsEntry.");
		}
	}
	
	public OFPortStatsEntry asPortStatsEntry(OFMessage m) {
		switch(version) {
		case "1.0": return (OFPortStatsEntry) m;
		case "1.3": return (OFPortStatsEntry) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortStatsEntry.");
		}
	}
	
	public OFFlowStatsEntry createFlowStatsEntry() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowStatsEntry();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowStatsEntry();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowStatsEntry.");
		}
	}
	
	public OFFlowStatsEntry asFlowStatsEntry(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowStatsEntry) m;
		case "1.3": return (OFFlowStatsEntry) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowStatsEntry.");
		}
	}
	
	public OFFlowDeleteStrict createFlowDeleteStrict() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowDeleteStrict();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowDeleteStrict();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowDeleteStrict.");
		}
	}
	
	public OFFlowDeleteStrict asFlowDeleteStrict(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowDeleteStrict) m;
		case "1.3": return (OFFlowDeleteStrict) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowDeleteStrict.");
		}
	}
	
	public OFFlowDelete createFlowDelete() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowDelete();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowDelete();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowDelete.");
		}
	}
	
	public OFFlowDelete asFlowDelete(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowDelete) m;
		case "1.3": return (OFFlowDelete) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowDelete.");
		}
	}
	
	public OFHelloElemVersionbitmap createHelloElemVersionbitmap() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFHelloElemVersionbitmap();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFHelloElemVersionbitmap.");
		}
	}
	
	public OFHelloElemVersionbitmap asHelloElemVersionbitmap(OFMessage m) {
		switch(version) {
		case "1.3": return (OFHelloElemVersionbitmap) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFHelloElemVersionbitmap.");
		}
	}
	
	public OFHello createHello() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFHello();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFHello();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFHello.");
		}
	}
	
	public OFHello asHello(OFMessage m) {
		switch(version) {
		case "1.0": return (OFHello) m;
		case "1.3": return (OFHello) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFHello.");
		}
	}
	
	public OFStatisticsFlowRequest createStatisticsFlowRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowRequest.");
		}
	}
	
	public OFStatisticsFlowRequest asStatisticsFlowRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsFlowRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowRequest.");
		}
	}
	
	public OFMeterBand createMeterBand() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterBand();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBand.");
		}
	}
	
	public OFMeterBand asMeterBand(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterBand) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBand.");
		}
	}
	
	public OFStatisticsAggregateReply createStatisticsAggregateReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateReply.");
		}
	}
	
	public OFStatisticsAggregateReply asStatisticsAggregateReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsAggregateReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateReply.");
		}
	}
	
	public OFSetAsync createSetAsync() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFSetAsync();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFSetAsync.");
		}
	}
	
	public OFSetAsync asSetAsync(OFMessage m) {
		switch(version) {
		case "1.3": return (OFSetAsync) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFSetAsync.");
		}
	}
	
	public OFFlowModify createFlowModify() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowModify();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowModify();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowModify.");
		}
	}
	
	public OFFlowModify asFlowModify(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowModify) m;
		case "1.3": return (OFFlowModify) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowModify.");
		}
	}
	
	public OFTableFeatureProperty createTableFeatureProperty() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeatureProperty();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeatureProperty.");
		}
	}
	
	public OFTableFeatureProperty asTableFeatureProperty(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeatureProperty) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeatureProperty.");
		}
	}
	
	public OFMessage createMessage() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFMessage();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMessage();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMessage.");
		}
	}
	
	public OFMessage asMessage(OFMessage m) {
		switch(version) {
		case "1.0": return (OFMessage) m;
		case "1.3": return (OFMessage) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMessage.");
		}
	}
	
	public OFTableFeatures createTableFeatures() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeatures();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeatures.");
		}
	}
	
	public OFTableFeatures asTableFeatures(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeatures) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeatures.");
		}
	}
	
	public OFMultipartTableFeaturesReply createMultipartTableFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartTableFeaturesReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableFeaturesReply.");
		}
	}
	
	public OFMultipartTableFeaturesReply asMultipartTableFeaturesReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartTableFeaturesReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartTableFeaturesReply.");
		}
	}
	
	public OFTableStatsEntry createTableStatsEntry() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFTableStatsEntry();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableStatsEntry();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableStatsEntry.");
		}
	}
	
	public OFTableStatsEntry asTableStatsEntry(OFMessage m) {
		switch(version) {
		case "1.0": return (OFTableStatsEntry) m;
		case "1.3": return (OFTableStatsEntry) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableStatsEntry.");
		}
	}
	
	public OFMultipartMeterConfigReply createMultipartMeterConfigReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterConfigReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterConfigReply.");
		}
	}
	
	public OFMultipartMeterConfigReply asMultipartMeterConfigReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartMeterConfigReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterConfigReply.");
		}
	}
	
	public OFMultipartMeterRequest createMultipartMeterRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterRequest.");
		}
	}
	
	public OFMultipartMeterRequest asMultipartMeterRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartMeterRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterRequest.");
		}
	}
	
	public OFQueueGetConfigReply createQueueGetConfigReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueueGetConfigReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueueGetConfigReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueGetConfigReply.");
		}
	}
	
	public OFQueueGetConfigReply asQueueGetConfigReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFQueueGetConfigReply) m;
		case "1.3": return (OFQueueGetConfigReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueGetConfigReply.");
		}
	}
	
	public OFMultipartDescRequest createMultipartDescRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartDescRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartDescRequest.");
		}
	}
	
	public OFMultipartDescRequest asMultipartDescRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartDescRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartDescRequest.");
		}
	}
	
	public OFInstructionGotoTable createInstructionGotoTable() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionGotoTable();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionGotoTable.");
		}
	}
	
	public OFInstructionGotoTable asInstructionGotoTable(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstructionGotoTable) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionGotoTable.");
		}
	}
	
	public OFActionPopVlan createActionPopVlan() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPopVlan();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPopVlan.");
		}
	}
	
	public OFActionPopVlan asActionPopVlan(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionPopVlan) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPopVlan.");
		}
	}
	
	public OFActionPopMpls createActionPopMpls() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPopMpls();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPopMpls.");
		}
	}
	
	public OFActionPopMpls asActionPopMpls(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionPopMpls) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPopMpls.");
		}
	}
	
	public OFMeterBandDscpRemark createMeterBandDscpRemark() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterBandDscpRemark();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandDscpRemark.");
		}
	}
	
	public OFMeterBandDscpRemark asMeterBandDscpRemark(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterBandDscpRemark) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandDscpRemark.");
		}
	}
	
	public OFMultipartQueueRequest createMultipartQueueRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartQueueRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartQueueRequest.");
		}
	}
	
	public OFMultipartQueueRequest asMultipartQueueRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartQueueRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartQueueRequest.");
		}
	}
	
	public OFMultipartExperimenterReply createMultipartExperimenterReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartExperimenterReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartExperimenterReply.");
		}
	}
	
	public OFMultipartExperimenterReply asMultipartExperimenterReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartExperimenterReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartExperimenterReply.");
		}
	}
	
	public OFTableFeaturePropertyWriteActionsMiss createTableFeaturePropertyWriteActionsMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteActionsMiss();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteActionsMiss.");
		}
	}
	
	public OFTableFeaturePropertyWriteActionsMiss asTableFeaturePropertyWriteActionsMiss(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyWriteActionsMiss) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteActionsMiss.");
		}
	}
	
	public OFActionSetNwTos createActionSetNwTos() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetNwTos();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwTos.");
		}
	}
	
	public OFActionSetNwTos asActionSetNwTos(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetNwTos) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwTos.");
		}
	}
	
	public OFMeterBandDrop createMeterBandDrop() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterBandDrop();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandDrop.");
		}
	}
	
	public OFMeterBandDrop asMeterBandDrop(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterBandDrop) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandDrop.");
		}
	}
	
	public OFActionDecNwTtl createActionDecNwTtl() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionDecNwTtl();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionDecNwTtl.");
		}
	}
	
	public OFActionDecNwTtl asActionDecNwTtl(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionDecNwTtl) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionDecNwTtl.");
		}
	}
	
	public OFActionOutput createActionOutput() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionOutput();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionOutput();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionOutput.");
		}
	}
	
	public OFActionOutput asActionOutput(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionOutput) m;
		case "1.3": return (OFActionOutput) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionOutput.");
		}
	}
	
	public OFQueueProperty createQueueProperty() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueueProperty();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueueProperty();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueProperty.");
		}
	}
	
	public OFQueueProperty asQueueProperty(OFMessage m) {
		switch(version) {
		case "1.0": return (OFQueueProperty) m;
		case "1.3": return (OFQueueProperty) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueProperty.");
		}
	}
	
	public OFMultipartAggregateRequest createMultipartAggregateRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartAggregateRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartAggregateRequest.");
		}
	}
	
	public OFMultipartAggregateRequest asMultipartAggregateRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartAggregateRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartAggregateRequest.");
		}
	}
	
	public OFMultipart createMultipart() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipart();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipart.");
		}
	}
	
	public OFMultipart asMultipart(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipart) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipart.");
		}
	}
	
	public OFEchoReply createEchoReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFEchoReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFEchoReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFEchoReply.");
		}
	}
	
	public OFEchoReply asEchoReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFEchoReply) m;
		case "1.3": return (OFEchoReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFEchoReply.");
		}
	}
	
	public OFQueueStatsEntry createQueueStatsEntry() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueueStatsEntry();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueueStatsEntry();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueStatsEntry.");
		}
	}
	
	public OFQueueStatsEntry asQueueStatsEntry(OFMessage m) {
		switch(version) {
		case "1.0": return (OFQueueStatsEntry) m;
		case "1.3": return (OFQueueStatsEntry) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueStatsEntry.");
		}
	}
	
	public OFMultipartExperimenterRequest createMultipartExperimenterRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartExperimenterRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartExperimenterRequest.");
		}
	}
	
	public OFMultipartExperimenterRequest asMultipartExperimenterRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartExperimenterRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartExperimenterRequest.");
		}
	}
	
	public OFExperimenter createExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFExperimenter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFExperimenter.");
		}
	}
	
	public OFExperimenter asExperimenter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFExperimenter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFExperimenter.");
		}
	}
	
	public OFTableFeaturePropertyApplySetfield createTableFeaturePropertyApplySetfield() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplySetfield();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplySetfield.");
		}
	}
	
	public OFTableFeaturePropertyApplySetfield asTableFeaturePropertyApplySetfield(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyApplySetfield) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplySetfield.");
		}
	}
	
	public OFTableFeaturePropertyWriteActions createTableFeaturePropertyWriteActions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteActions();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteActions.");
		}
	}
	
	public OFTableFeaturePropertyWriteActions asTableFeaturePropertyWriteActions(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyWriteActions) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteActions.");
		}
	}
	
	public OFStatisticsDescRequest createStatisticsDescRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsDescRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescRequest.");
		}
	}
	
	public OFStatisticsDescRequest asStatisticsDescRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsDescRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescRequest.");
		}
	}
	
	public OFFeaturesReply createFeaturesReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFeaturesReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFeaturesReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFeaturesReply.");
		}
	}
	
	public OFFeaturesReply asFeaturesReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFeaturesReply) m;
		case "1.3": return (OFFeaturesReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFeaturesReply.");
		}
	}
	
	public OFMeterBandExperimenter createMeterBandExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterBandExperimenter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandExperimenter.");
		}
	}
	
	public OFMeterBandExperimenter asMeterBandExperimenter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterBandExperimenter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandExperimenter.");
		}
	}
	
	public OFInstruction createInstruction() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstruction();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstruction.");
		}
	}
	
	public OFInstruction asInstruction(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstruction) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstruction.");
		}
	}
	
	public OFQueuePropertyMinRate createQueuePropertyMinRate() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueuePropertyMinRate();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueuePropertyMinRate();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyMinRate.");
		}
	}
	
	public OFQueuePropertyMinRate asQueuePropertyMinRate(OFMessage m) {
		switch(version) {
		case "1.0": return (OFQueuePropertyMinRate) m;
		case "1.3": return (OFQueuePropertyMinRate) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyMinRate.");
		}
	}
	
	public OFMultipartDescReply createMultipartDescReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartDescReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartDescReply.");
		}
	}
	
	public OFMultipartDescReply asMultipartDescReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartDescReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartDescReply.");
		}
	}
	
	public OFBucketCounter createBucketCounter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFBucketCounter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBucketCounter.");
		}
	}
	
	public OFBucketCounter asBucketCounter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFBucketCounter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBucketCounter.");
		}
	}
	
	public OFAction createAction() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFAction();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFAction();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFAction.");
		}
	}
	
	public OFAction asAction(OFMessage m) {
		switch(version) {
		case "1.0": return (OFAction) m;
		case "1.3": return (OFAction) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFAction.");
		}
	}
	
	public OFActionCopyTtlOut createActionCopyTtlOut() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionCopyTtlOut();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionCopyTtlOut.");
		}
	}
	
	public OFActionCopyTtlOut asActionCopyTtlOut(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionCopyTtlOut) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionCopyTtlOut.");
		}
	}
	
	public OFGroupStatsEntry createGroupStatsEntry() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGroupStatsEntry();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGroupStatsEntry.");
		}
	}
	
	public OFGroupStatsEntry asGroupStatsEntry(OFMessage m) {
		switch(version) {
		case "1.3": return (OFGroupStatsEntry) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGroupStatsEntry.");
		}
	}
	
	public OFTableMod createTableMod() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableMod();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableMod.");
		}
	}
	
	public OFTableMod asTableMod(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableMod) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableMod.");
		}
	}
	
	public OFInstructionWriteActions createInstructionWriteActions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionWriteActions();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionWriteActions.");
		}
	}
	
	public OFInstructionWriteActions asInstructionWriteActions(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstructionWriteActions) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionWriteActions.");
		}
	}
	
	public OFActionDecMplsTtl createActionDecMplsTtl() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionDecMplsTtl();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionDecMplsTtl.");
		}
	}
	
	public OFActionDecMplsTtl asActionDecMplsTtl(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionDecMplsTtl) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionDecMplsTtl.");
		}
	}
	
	public OFRoleReply createRoleReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFRoleReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFRoleReply.");
		}
	}
	
	public OFRoleReply asRoleReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFRoleReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFRoleReply.");
		}
	}
	
	public OFMultipartMeterReply createMultipartMeterReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterReply.");
		}
	}
	
	public OFMultipartMeterReply asMultipartMeterReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartMeterReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterReply.");
		}
	}
	
	public OFMeterFeatures createMeterFeatures() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterFeatures();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterFeatures.");
		}
	}
	
	public OFMeterFeatures asMeterFeatures(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterFeatures) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterFeatures.");
		}
	}
	
	public OFEchoRequest createEchoRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFEchoRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFEchoRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFEchoRequest.");
		}
	}
	
	public OFEchoRequest asEchoRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFEchoRequest) m;
		case "1.3": return (OFEchoRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFEchoRequest.");
		}
	}
	
	public OFTableFeaturePropertyApplySetfieldMiss createTableFeaturePropertyApplySetfieldMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplySetfieldMiss();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplySetfieldMiss.");
		}
	}
	
	public OFTableFeaturePropertyApplySetfieldMiss asTableFeaturePropertyApplySetfieldMiss(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyApplySetfieldMiss) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplySetfieldMiss.");
		}
	}
	
	public OFTableFeaturePropertyWriteSetfield createTableFeaturePropertyWriteSetfield() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteSetfield();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteSetfield.");
		}
	}
	
	public OFTableFeaturePropertyWriteSetfield asTableFeaturePropertyWriteSetfield(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyWriteSetfield) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteSetfield.");
		}
	}
	
	public OFInstructionApplyActions createInstructionApplyActions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionApplyActions();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionApplyActions.");
		}
	}
	
	public OFInstructionApplyActions asInstructionApplyActions(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstructionApplyActions) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionApplyActions.");
		}
	}
	
	public OFInstructionClearActions createInstructionClearActions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionClearActions();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionClearActions.");
		}
	}
	
	public OFInstructionClearActions asInstructionClearActions(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstructionClearActions) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionClearActions.");
		}
	}
	
	public OFPacketIn createPacketIn() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPacketIn();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPacketIn();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketIn.");
		}
	}
	
	public OFPacketIn asPacketIn(OFMessage m) {
		switch(version) {
		case "1.0": return (OFPacketIn) m;
		case "1.3": return (OFPacketIn) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketIn.");
		}
	}
	
	public OFBarrierReply createBarrierReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFBarrierReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFBarrierReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBarrierReply.");
		}
	}
	
	public OFBarrierReply asBarrierReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFBarrierReply) m;
		case "1.3": return (OFBarrierReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBarrierReply.");
		}
	}
	
	public OFMultipartPortStatsRequest createMultipartPortStatsRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartPortStatsRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortStatsRequest.");
		}
	}
	
	public OFMultipartPortStatsRequest asMultipartPortStatsRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartPortStatsRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortStatsRequest.");
		}
	}
	
	public OFActionSetQueue createActionSetQueue() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionSetQueue();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetQueue.");
		}
	}
	
	public OFActionSetQueue asActionSetQueue(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionSetQueue) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetQueue.");
		}
	}
	
	public OFMultipartPortDescReply createMultipartPortDescReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartPortDescReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortDescReply.");
		}
	}
	
	public OFMultipartPortDescReply asMultipartPortDescReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartPortDescReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortDescReply.");
		}
	}
	
	public OFMultipartMeterFeaturesRequest createMultipartMeterFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterFeaturesRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterFeaturesRequest.");
		}
	}
	
	public OFMultipartMeterFeaturesRequest asMultipartMeterFeaturesRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartMeterFeaturesRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartMeterFeaturesRequest.");
		}
	}
	
	public OFMeterMod createMeterMod() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterMod();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterMod.");
		}
	}
	
	public OFMeterMod asMeterMod(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterMod) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterMod.");
		}
	}
	
	public OFTableFeaturePropertyExperimenter createTableFeaturePropertyExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyExperimenter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyExperimenter.");
		}
	}
	
	public OFTableFeaturePropertyExperimenter asTableFeaturePropertyExperimenter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyExperimenter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyExperimenter.");
		}
	}
	
	public OFActionSetMplsTtl createActionSetMplsTtl() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionSetMplsTtl();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetMplsTtl.");
		}
	}
	
	public OFActionSetMplsTtl asActionSetMplsTtl(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionSetMplsTtl) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetMplsTtl.");
		}
	}
	
	public OFStatisticsVendorReply createStatisticsVendorReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsVendorReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsVendorReply.");
		}
	}
	
	public OFStatisticsVendorReply asStatisticsVendorReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsVendorReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsVendorReply.");
		}
	}
	
	public OFQueuePropertyExperimenter createQueuePropertyExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueuePropertyExperimenter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyExperimenter.");
		}
	}
	
	public OFQueuePropertyExperimenter asQueuePropertyExperimenter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFQueuePropertyExperimenter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyExperimenter.");
		}
	}
	
	public OFInstructionExperimenter createInstructionExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionExperimenter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionExperimenter.");
		}
	}
	
	public OFInstructionExperimenter asInstructionExperimenter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFInstructionExperimenter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionExperimenter.");
		}
	}
	
	public OFMultipartRequest createMultipartRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartRequest.");
		}
	}
	
	public OFMultipartRequest asMultipartRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartRequest.");
		}
	}
	
	public OFMultipartAggregateReply createMultipartAggregateReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartAggregateReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartAggregateReply.");
		}
	}
	
	public OFMultipartAggregateReply asMultipartAggregateReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartAggregateReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartAggregateReply.");
		}
	}
	
	public OFTableFeaturePropertyInstructions createTableFeaturePropertyInstructions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyInstructions();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyInstructions.");
		}
	}
	
	public OFTableFeaturePropertyInstructions asTableFeaturePropertyInstructions(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyInstructions) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyInstructions.");
		}
	}
	
	public OFHelloElem createHelloElem() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFHelloElem();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFHelloElem.");
		}
	}
	
	public OFHelloElem asHelloElem(OFMessage m) {
		switch(version) {
		case "1.3": return (OFHelloElem) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFHelloElem.");
		}
	}
	
	public OFActionSetVlanPcp createActionSetVlanPcp() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetVlanPcp();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetVlanPcp.");
		}
	}
	
	public OFActionSetVlanPcp asActionSetVlanPcp(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetVlanPcp) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetVlanPcp.");
		}
	}
	
	public OFMultipartGroupDescRequest createMultipartGroupDescRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupDescRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupDescRequest.");
		}
	}
	
	public OFMultipartGroupDescRequest asMultipartGroupDescRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartGroupDescRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupDescRequest.");
		}
	}
	
	public OFMultipartGroupDescReply createMultipartGroupDescReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupDescReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupDescReply.");
		}
	}
	
	public OFMultipartGroupDescReply asMultipartGroupDescReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartGroupDescReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartGroupDescReply.");
		}
	}
	
	public OFActionSetNwSrc createActionSetNwSrc() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetNwSrc();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwSrc.");
		}
	}
	
	public OFActionSetNwSrc asActionSetNwSrc(OFMessage m) {
		switch(version) {
		case "1.0": return (OFActionSetNwSrc) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwSrc.");
		}
	}
	
	public OFPortMod createPortMod() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPortMod();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPortMod();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortMod.");
		}
	}
	
	public OFPortMod asPortMod(OFMessage m) {
		switch(version) {
		case "1.0": return (OFPortMod) m;
		case "1.3": return (OFPortMod) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortMod.");
		}
	}
	
	public OFActionExperimenter createActionExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionExperimenter();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionExperimenter.");
		}
	}
	
	public OFActionExperimenter asActionExperimenter(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionExperimenter) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionExperimenter.");
		}
	}
	
	public OFMeterStats createMeterStats() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterStats();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterStats.");
		}
	}
	
	public OFMeterStats asMeterStats(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMeterStats) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterStats.");
		}
	}
	
	public OFActionPushPbb createActionPushPbb() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPushPbb();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPushPbb.");
		}
	}
	
	public OFActionPushPbb asActionPushPbb(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionPushPbb) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPushPbb.");
		}
	}
	
	public OFFlowRemoved createFlowRemoved() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowRemoved();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowRemoved();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowRemoved.");
		}
	}
	
	public OFFlowRemoved asFlowRemoved(OFMessage m) {
		switch(version) {
		case "1.0": return (OFFlowRemoved) m;
		case "1.3": return (OFFlowRemoved) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowRemoved.");
		}
	}
	
	public OFMultipartFlowRequest createMultipartFlowRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartFlowRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartFlowRequest.");
		}
	}
	
	public OFMultipartFlowRequest asMultipartFlowRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartFlowRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartFlowRequest.");
		}
	}
	
	public OFPortDesc createPortDesc() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPortDesc();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPortDesc();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortDesc.");
		}
	}
	
	public OFPortDesc asPortDesc(OFMessage m) {
		switch(version) {
		case "1.0": return (OFPortDesc) m;
		case "1.3": return (OFPortDesc) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortDesc.");
		}
	}
	
	public OFMultipartQueueReply createMultipartQueueReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartQueueReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartQueueReply.");
		}
	}
	
	public OFMultipartQueueReply asMultipartQueueReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartQueueReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartQueueReply.");
		}
	}
	
	public OFQueuePropertyNone createQueuePropertyNone() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueuePropertyNone();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyNone.");
		}
	}
	
	public OFQueuePropertyNone asQueuePropertyNone(OFMessage m) {
		switch(version) {
		case "1.0": return (OFQueuePropertyNone) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyNone.");
		}
	}
	
	public OFBucket createBucket() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFBucket();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBucket.");
		}
	}
	
	public OFBucket asBucket(OFMessage m) {
		switch(version) {
		case "1.3": return (OFBucket) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBucket.");
		}
	}
	
	public OFStatisticsTableRequest createStatisticsTableRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsTableRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableRequest.");
		}
	}
	
	public OFStatisticsTableRequest asStatisticsTableRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsTableRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableRequest.");
		}
	}
	
	public OFTableFeaturePropertyMatch createTableFeaturePropertyMatch() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyMatch();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyMatch.");
		}
	}
	
	public OFTableFeaturePropertyMatch asTableFeaturePropertyMatch(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyMatch) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyMatch.");
		}
	}
	
	public OFStatisticsQueueRequest createStatisticsQueueRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsQueueRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueRequest.");
		}
	}
	
	public OFStatisticsQueueRequest asStatisticsQueueRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsQueueRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueRequest.");
		}
	}
	
	public OFGetConfigReply createGetConfigReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFGetConfigReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGetConfigReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetConfigReply.");
		}
	}
	
	public OFGetConfigReply asGetConfigReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFGetConfigReply) m;
		case "1.3": return (OFGetConfigReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetConfigReply.");
		}
	}
	
	public OFStatisticsReply createStatisticsReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsReply.");
		}
	}
	
	public OFStatisticsReply asStatisticsReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsReply.");
		}
	}
	
	public OFGroupMod createGroupMod() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGroupMod();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGroupMod.");
		}
	}
	
	public OFGroupMod asGroupMod(OFMessage m) {
		switch(version) {
		case "1.3": return (OFGroupMod) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGroupMod.");
		}
	}
	
	public OFTableFeaturePropertyApplyActionsMiss createTableFeaturePropertyApplyActionsMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplyActionsMiss();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplyActionsMiss.");
		}
	}
	
	public OFTableFeaturePropertyApplyActionsMiss asTableFeaturePropertyApplyActionsMiss(OFMessage m) {
		switch(version) {
		case "1.3": return (OFTableFeaturePropertyApplyActionsMiss) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplyActionsMiss.");
		}
	}
	
	public OFPacketQueue createPacketQueue() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPacketQueue();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPacketQueue();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketQueue.");
		}
	}
	
	public OFPacketQueue asPacketQueue(OFMessage m) {
		switch(version) {
		case "1.0": return (OFPacketQueue) m;
		case "1.3": return (OFPacketQueue) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketQueue.");
		}
	}
	
	public OFMultipartPortStatsReply createMultipartPortStatsReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartPortStatsReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortStatsReply.");
		}
	}
	
	public OFMultipartPortStatsReply asMultipartPortStatsReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFMultipartPortStatsReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMultipartPortStatsReply.");
		}
	}
	
	public OFStatisticsPortReply createStatisticsPortReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsPortReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortReply.");
		}
	}
	
	public OFStatisticsPortReply asStatisticsPortReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsPortReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortReply.");
		}
	}
	
	public OFActionPopPbb createActionPopPbb() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPopPbb();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPopPbb.");
		}
	}
	
	public OFActionPopPbb asActionPopPbb(OFMessage m) {
		switch(version) {
		case "1.3": return (OFActionPopPbb) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPopPbb.");
		}
	}
	
	public OFStatisticsQueueReply createStatisticsQueueReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsQueueReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueReply.");
		}
	}
	
	public OFStatisticsQueueReply asStatisticsQueueReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsQueueReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueReply.");
		}
	}
	
}
