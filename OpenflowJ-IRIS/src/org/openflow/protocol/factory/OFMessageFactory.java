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
	public OFError createError() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFError();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFError();
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
	public OFStatisticsAggregateRequest createStatisticsAggregateRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateRequest();
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
	public OFActionCopyTtlIn createActionCopyTtlIn() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionCopyTtlIn();
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
	public OFStatisticsVendorRequest createStatisticsVendorRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsVendorRequest();
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
	public OFActionGroup createActionGroup() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionGroup();
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
	public OFFlowModifyStrict createFlowModifyStrict() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowModifyStrict();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowModifyStrict();
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
	public OFTableFeaturePropertyApplyActions createTableFeaturePropertyApplyActions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplyActions();
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
	public OFMultipartGroupFeaturesRequest createMultipartGroupFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupFeaturesRequest();
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
	public OFTableFeaturePropertyNextTables createTableFeaturePropertyNextTables() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyNextTables();
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
	public OFTableFeaturePropertyWriteSetfieldMiss createTableFeaturePropertyWriteSetfieldMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteSetfieldMiss();
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
	public OFActionStripVlan createActionStripVlan() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionStripVlan();
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
	public OFOxm createOxm() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFOxm();
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
	public OFActionId createActionId() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionId();
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
	public OFTableFeaturePropertyInstructionsMiss createTableFeaturePropertyInstructionsMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyInstructionsMiss();
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
	public OFMultipartPortDescRequest createMultipartPortDescRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartPortDescRequest();
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
	public OFMultipartTableReply createMultipartTableReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartTableReply();
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
	public OFTableFeaturePropertyNextTablesMiss createTableFeaturePropertyNextTablesMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyNextTablesMiss();
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
	public OFFlowAdd createFlowAdd() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowAdd();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowAdd();
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
	public OFActionSetField createActionSetField() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionSetField();
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
	public OFFeaturesRequest createFeaturesRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFeaturesRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFeaturesRequest();
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
	public OFActionPushMpls createActionPushMpls() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPushMpls();
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
	public OFMultipartMeterConfigRequest createMultipartMeterConfigRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterConfigRequest();
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
	public OFMultipartGroupFeaturesReply createMultipartGroupFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupFeaturesReply();
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
	public OFMultipartReply createMultipartReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartReply();
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
	public OFGetAsyncRequest createGetAsyncRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGetAsyncRequest();
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
	public OFStatisticsFlowReply createStatisticsFlowReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowReply();
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
	public OFActionPushVlan createActionPushVlan() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPushVlan();
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
	public OFGetConfigRequest createGetConfigRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFGetConfigRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGetConfigRequest();
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
	public OFVendor createVendor() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFVendor();
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
	public OFGroupDescStatsEntry createGroupDescStatsEntry() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGroupDescStatsEntry();
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
	public OFMultipartGroupReply createMultipartGroupReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupReply();
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
	public OFActionSetTpDst createActionSetTpDst() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetTpDst();
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
	public OFActionSetTpSrc createActionSetTpSrc() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetTpSrc();
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
	public OFPortStatsEntry createPortStatsEntry() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPortStatsEntry();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPortStatsEntry();
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
	public OFFlowDeleteStrict createFlowDeleteStrict() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowDeleteStrict();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowDeleteStrict();
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
	public OFHelloElemVersionbitmap createHelloElemVersionbitmap() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFHelloElemVersionbitmap();
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
	public OFStatisticsFlowRequest createStatisticsFlowRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowRequest();
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
	public OFStatisticsAggregateReply createStatisticsAggregateReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateReply();
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
	public OFFlowModify createFlowModify() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFFlowModify();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFFlowModify();
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
	public OFMessage createMessage() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFMessage();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMessage();
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
	public OFMultipartTableFeaturesReply createMultipartTableFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartTableFeaturesReply();
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
	public OFMultipartMeterConfigReply createMultipartMeterConfigReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterConfigReply();
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
	public OFQueueGetConfigReply createQueueGetConfigReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueueGetConfigReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueueGetConfigReply();
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
	public OFInstructionGotoTable createInstructionGotoTable() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionGotoTable();
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
	public OFActionPopMpls createActionPopMpls() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPopMpls();
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
	public OFMultipartQueueRequest createMultipartQueueRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartQueueRequest();
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
	public OFTableFeaturePropertyWriteActionsMiss createTableFeaturePropertyWriteActionsMiss() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteActionsMiss();
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
	public OFMeterBandDrop createMeterBandDrop() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterBandDrop();
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
	public OFActionOutput createActionOutput() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionOutput();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionOutput();
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
	public OFMultipartAggregateRequest createMultipartAggregateRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartAggregateRequest();
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
	public OFEchoReply createEchoReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFEchoReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFEchoReply();
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
	public OFMultipartExperimenterRequest createMultipartExperimenterRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartExperimenterRequest();
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
	public OFTableFeaturePropertyApplySetfield createTableFeaturePropertyApplySetfield() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplySetfield();
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
	public OFStatisticsDescRequest createStatisticsDescRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsDescRequest();
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
	public OFMeterBandExperimenter createMeterBandExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMeterBandExperimenter();
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
	public OFQueuePropertyMinRate createQueuePropertyMinRate() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFQueuePropertyMinRate();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFQueuePropertyMinRate();
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
	public OFBucketCounter createBucketCounter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFBucketCounter();
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
	public OFActionCopyTtlOut createActionCopyTtlOut() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionCopyTtlOut();
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
	public OFTableMod createTableMod() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableMod();
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
	public OFActionDecMplsTtl createActionDecMplsTtl() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionDecMplsTtl();
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
	public OFMultipartMeterReply createMultipartMeterReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterReply();
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
	public OFEchoRequest createEchoRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFEchoRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFEchoRequest();
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
	public OFTableFeaturePropertyWriteSetfield createTableFeaturePropertyWriteSetfield() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteSetfield();
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
	public OFInstructionClearActions createInstructionClearActions() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionClearActions();
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
	public OFBarrierReply createBarrierReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFBarrierReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFBarrierReply();
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
	public OFActionSetQueue createActionSetQueue() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionSetQueue();
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
	public OFMultipartMeterFeaturesRequest createMultipartMeterFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartMeterFeaturesRequest();
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
	public OFTableFeaturePropertyExperimenter createTableFeaturePropertyExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyExperimenter();
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
	public OFStatisticsVendorReply createStatisticsVendorReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsVendorReply();
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
	public OFInstructionExperimenter createInstructionExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFInstructionExperimenter();
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
	public OFMultipartAggregateReply createMultipartAggregateReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartAggregateReply();
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
	public OFHelloElem createHelloElem() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFHelloElem();
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
	public OFMultipartGroupDescRequest createMultipartGroupDescRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartGroupDescRequest();
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
	public OFActionSetNwSrc createActionSetNwSrc() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFActionSetNwSrc();
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
	public OFActionExperimenter createActionExperimenter() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionExperimenter();
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
	public OFActionPushPbb createActionPushPbb() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFActionPushPbb();
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
	public OFMultipartFlowRequest createMultipartFlowRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartFlowRequest();
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
	public OFMultipartQueueReply createMultipartQueueReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMultipartQueueReply();
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
	public OFBucket createBucket() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFBucket();
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
	public OFTableFeaturePropertyMatch createTableFeaturePropertyMatch() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyMatch();
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
	public OFGetConfigReply createGetConfigReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFGetConfigReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGetConfigReply();
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
	public OFGroupMod createGroupMod() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFGroupMod();
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
	public OFPacketQueue createPacketQueue() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFPacketQueue();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFPacketQueue();
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
	public OFStatisticsPortReply createStatisticsPortReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsPortReply();
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
	public OFStatisticsQueueReply createStatisticsQueueReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsQueueReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueReply.");
		}
	}
}
