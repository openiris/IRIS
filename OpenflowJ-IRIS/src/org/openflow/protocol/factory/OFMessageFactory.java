package org.openflow.protocol.factory;

import org.openflow.protocol.interfaces.*;

public class OFMessageFactory {
	
	/**
	 * You cannot create OFMessageFactory object.
	 */
	private OFMessageFactory() {
	}
	
	public static OFError createError(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFError();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFError();
		default: return null;
		}
	}
	
	public static OFError asError(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFError) m;
		case 0x04: return (OFError) m;
		default: return null;
		}
	}
	
	public static OFStatisticsRequest createStatisticsRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsRequest asStatisticsRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsRequest) m;
		case 0x04: return (OFStatisticsRequest) m;
		default: return null;
		}
	}
	
	public static OFStatisticsAggregateRequest createStatisticsAggregateRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsAggregateRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsAggregateRequest asStatisticsAggregateRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsAggregateRequest) m;
		case 0x04: return (OFStatisticsAggregateRequest) m;
		default: return null;
		}
	}
	
	public static OFActionSetNwTtl createActionSetNwTtl(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionSetNwTtl();
		default: return null;
		}
	}
	
	public static OFActionSetNwTtl asActionSetNwTtl(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionSetNwTtl) m;
		default: return null;
		}
	}
	
	public static OFActionCopyTtlIn createActionCopyTtlIn(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionCopyTtlIn();
		default: return null;
		}
	}
	
	public static OFActionCopyTtlIn asActionCopyTtlIn(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionCopyTtlIn) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWildcards createTableFeaturePropertyWildcards(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWildcards();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWildcards asTableFeaturePropertyWildcards(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyWildcards) m;
		default: return null;
		}
	}
	
	public static OFStatisticsVendorRequest createStatisticsVendorRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsVendorRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsVendorRequest asStatisticsVendorRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsVendorRequest) m;
		default: return null;
		}
	}
	
	public static OFMatch createMatch(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFMatch();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMatchOxm();
		default: return null;
		}
	}
	
	public static OFMatch asMatch(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFMatch) m;
		case 0x04: return (OFMatchOxm) m;
		default: return null;
		}
	}
	
	public static org.openflow.protocol.interfaces.OFMatch.Builder createMatchBuilder(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFMatch.Builder();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMatchOxm.Builder();
		default: return null; 
		}
	}
	
	public static OFActionGroup createActionGroup(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionGroup();
		default: return null;
		}
	}
	
	public static OFActionGroup asActionGroup(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionGroup) m;
		default: return null;
		}
	}
	
	public static OFStatisticsMeterFeaturesRequest createStatisticsMeterFeaturesRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterFeaturesRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsMeterFeaturesRequest asStatisticsMeterFeaturesRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsMeterFeaturesRequest) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyExperimenterMiss createTableFeaturePropertyExperimenterMiss(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyExperimenterMiss();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyExperimenterMiss asTableFeaturePropertyExperimenterMiss(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyExperimenterMiss) m;
		default: return null;
		}
	}
	
	public static OFFlowModifyStrict createFlowModifyStrict(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowModifyStrict();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowModifyStrict();
		default: return null;
		}
	}
	
	public static OFFlowModifyStrict asFlowModifyStrict(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowModifyStrict) m;
		case 0x04: return (OFFlowModifyStrict) m;
		default: return null;
		}
	}
	
	public static OFInstructionWriteMetadata createInstructionWriteMetadata(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstructionWriteMetadata();
		default: return null;
		}
	}
	
	public static OFInstructionWriteMetadata asInstructionWriteMetadata(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstructionWriteMetadata) m;
		default: return null;
		}
	}
	
	public static OFSetConfig createSetConfig(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFSetConfig();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFSetConfig();
		default: return null;
		}
	}
	
	public static OFSetConfig asSetConfig(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFSetConfig) m;
		case 0x04: return (OFSetConfig) m;
		default: return null;
		}
	}
	
	public static OFActionSetDlSrc createActionSetDlSrc(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetDlSrc();
		default: return null;
		}
	}
	
	public static OFActionSetDlSrc asActionSetDlSrc(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetDlSrc) m;
		default: return null;
		}
	}
	
	public static OFFeaturesReply createFeaturesReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFeaturesReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFeaturesReply();
		default: return null;
		}
	}
	
	public static OFFeaturesReply asFeaturesReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFeaturesReply) m;
		case 0x04: return (OFFeaturesReply) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyNextTables createTableFeaturePropertyNextTables(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyNextTables();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyNextTables asTableFeaturePropertyNextTables(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyNextTables) m;
		default: return null;
		}
	}
	
	public static OFActionOpaqueEnqueue createActionOpaqueEnqueue(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionOpaqueEnqueue();
		default: return null;
		}
	}
	
	public static OFActionOpaqueEnqueue asActionOpaqueEnqueue(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionOpaqueEnqueue) m;
		default: return null;
		}
	}
	
	public static OFMatchOxm createMatchOxm(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMatchOxm();
		default: return null;
		}
	}
	
	public static OFMatchOxm asMatchOxm(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMatchOxm) m;
		default: return null;
		}
	}
	
	public static OFStatisticsMeterReply createStatisticsMeterReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterReply();
		default: return null;
		}
	}
	
	public static OFStatisticsMeterReply asStatisticsMeterReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsMeterReply) m;
		default: return null;
		}
	}
	
	public static OFActionStripVlan createActionStripVlan(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionStripVlan();
		default: return null;
		}
	}
	
	public static OFActionStripVlan asActionStripVlan(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionStripVlan) m;
		default: return null;
		}
	}
	
	public static OFPacketOut createPacketOut(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFPacketOut();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFPacketOut();
		default: return null;
		}
	}
	
	public static OFPacketOut asPacketOut(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFPacketOut) m;
		case 0x04: return (OFPacketOut) m;
		default: return null;
		}
	}
	
	public static OFOxm createOxm(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFOxm();
		default: return null;
		}
	}
	
	public static OFOxm asOxm(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFOxm) m;
		default: return null;
		}
	}
	
	public static OFQueuePropertyMaxRate createQueuePropertyMaxRate(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFQueuePropertyMaxRate();
		default: return null;
		}
	}
	
	public static OFQueuePropertyMaxRate asQueuePropertyMaxRate(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFQueuePropertyMaxRate) m;
		default: return null;
		}
	}
	
	public static OFActionId createActionId(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionId();
		default: return null;
		}
	}
	
	public static OFActionId asActionId(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionId) m;
		default: return null;
		}
	}
	
	public static OFQueueGetConfigRequest createQueueGetConfigRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFQueueGetConfigRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFQueueGetConfigRequest();
		default: return null;
		}
	}
	
	public static OFQueueGetConfigRequest asQueueGetConfigRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFQueueGetConfigRequest) m;
		case 0x04: return (OFQueueGetConfigRequest) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyInstructionsMiss createTableFeaturePropertyInstructionsMiss(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyInstructionsMiss();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyInstructionsMiss asTableFeaturePropertyInstructionsMiss(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyInstructionsMiss) m;
		default: return null;
		}
	}
	
	public static OFActionSetNwDst createActionSetNwDst(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetNwDst();
		default: return null;
		}
	}
	
	public static OFActionSetNwDst asActionSetNwDst(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetNwDst) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteSetfieldMiss createTableFeaturePropertyWriteSetfieldMiss(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteSetfieldMiss();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteSetfieldMiss asTableFeaturePropertyWriteSetfieldMiss(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyWriteSetfieldMiss) m;
		default: return null;
		}
	}
	
	public static OFPortStatus createPortStatus(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFPortStatus();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFPortStatus();
		default: return null;
		}
	}
	
	public static OFPortStatus asPortStatus(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFPortStatus) m;
		case 0x04: return (OFPortStatus) m;
		default: return null;
		}
	}
	
	public static OFStatisticsTableFeaturesReply createStatisticsTableFeaturesReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableFeaturesReply();
		default: return null;
		}
	}
	
	public static OFStatisticsTableFeaturesReply asStatisticsTableFeaturesReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsTableFeaturesReply) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyNextTablesMiss createTableFeaturePropertyNextTablesMiss(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyNextTablesMiss();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyNextTablesMiss asTableFeaturePropertyNextTablesMiss(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyNextTablesMiss) m;
		default: return null;
		}
	}
	
	public static OFStatisticsExperimenterReply createStatisticsExperimenterReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsExperimenterReply();
		default: return null;
		}
	}
	
	public static OFStatisticsExperimenterReply asStatisticsExperimenterReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsExperimenterReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsPortDescReply createStatisticsPortDescReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortDescReply();
		default: return null;
		}
	}
	
	public static OFStatisticsPortDescReply asStatisticsPortDescReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsPortDescReply) m;
		default: return null;
		}
	}
	
	public static OFFlowAdd createFlowAdd(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowAdd();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowAdd();
		default: return null;
		}
	}
	
	public static OFFlowAdd asFlowAdd(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowAdd) m;
		case 0x04: return (OFFlowAdd) m;
		default: return null;
		}
	}
	
	public static OFGetAsyncReply createGetAsyncReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFGetAsyncReply();
		default: return null;
		}
	}
	
	public static OFGetAsyncReply asGetAsyncReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFGetAsyncReply) m;
		default: return null;
		}
	}
	
	public static OFActionSetField createActionSetField(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionSetField();
		default: return null;
		}
	}
	
	public static OFActionSetField asActionSetField(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionSetField) m;
		default: return null;
		}
	}
	
	public static OFMeterBandStats createMeterBandStats(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterBandStats();
		default: return null;
		}
	}
	
	public static OFMeterBandStats asMeterBandStats(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterBandStats) m;
		default: return null;
		}
	}
	
	public static OFFeaturesRequest createFeaturesRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFeaturesRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFeaturesRequest();
		default: return null;
		}
	}
	
	public static OFFeaturesRequest asFeaturesRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFeaturesRequest) m;
		case 0x04: return (OFFeaturesRequest) m;
		default: return null;
		}
	}
	
	public static OFStatisticsPortStatsRequest createStatisticsPortStatsRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortStatsRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsPortStatsRequest asStatisticsPortStatsRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsPortStatsRequest) m;
		default: return null;
		}
	}
	
	public static OFStatistics createStatistics(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatistics();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatistics();
		default: return null;
		}
	}
	
	public static OFStatistics asStatistics(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatistics) m;
		case 0x04: return (OFStatistics) m;
		default: return null;
		}
	}
	
	public static OFActionPushMpls createActionPushMpls(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionPushMpls();
		default: return null;
		}
	}
	
	public static OFActionPushMpls asActionPushMpls(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionPushMpls) m;
		default: return null;
		}
	}
	
	public static OFActionSetVlanId createActionSetVlanId(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetVlanId();
		default: return null;
		}
	}
	
	public static OFActionSetVlanId asActionSetVlanId(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetVlanId) m;
		default: return null;
		}
	}
	
	public static OFStatisticsTableReply createStatisticsTableReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsTableReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableReply();
		default: return null;
		}
	}
	
	public static OFStatisticsTableReply asStatisticsTableReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsTableReply) m;
		case 0x04: return (OFStatisticsTableReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsPortRequest createStatisticsPortRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsPortRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsPortRequest asStatisticsPortRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsPortRequest) m;
		default: return null;
		}
	}
	
	public static OFStatisticsPortDescRequest createStatisticsPortDescRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortDescRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsPortDescRequest asStatisticsPortDescRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsPortDescRequest) m;
		default: return null;
		}
	}
	
	public static OFGetAsyncRequest createGetAsyncRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFGetAsyncRequest();
		default: return null;
		}
	}
	
	public static OFGetAsyncRequest asGetAsyncRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFGetAsyncRequest) m;
		default: return null;
		}
	}
	
	public static OFStatisticsFlowReply createStatisticsFlowReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsFlowReply();
		default: return null;
		}
	}
	
	public static OFStatisticsFlowReply asStatisticsFlowReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsFlowReply) m;
		case 0x04: return (OFStatisticsFlowReply) m;
		default: return null;
		}
	}
	
	public static OFBarrierRequest createBarrierRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFBarrierRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFBarrierRequest();
		default: return null;
		}
	}
	
	public static OFBarrierRequest asBarrierRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFBarrierRequest) m;
		case 0x04: return (OFBarrierRequest) m;
		default: return null;
		}
	}
	
	public static OFActionPushVlan createActionPushVlan(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionPushVlan();
		default: return null;
		}
	}
	
	public static OFActionPushVlan asActionPushVlan(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionPushVlan) m;
		default: return null;
		}
	}
	
	public static OFActionVendor createActionVendor(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionVendor();
		default: return null;
		}
	}
	
	public static OFActionVendor asActionVendor(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionVendor) m;
		default: return null;
		}
	}
	
	public static OFGetConfigRequest createGetConfigRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFGetConfigRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFGetConfigRequest();
		default: return null;
		}
	}
	
	public static OFGetConfigRequest asGetConfigRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFGetConfigRequest) m;
		case 0x04: return (OFGetConfigRequest) m;
		default: return null;
		}
	}
	
	public static OFInstructionMeter createInstructionMeter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstructionMeter();
		default: return null;
		}
	}
	
	public static OFInstructionMeter asInstructionMeter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstructionMeter) m;
		default: return null;
		}
	}
	
	public static OFVendor createVendor(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFVendor();
		default: return null;
		}
	}
	
	public static OFVendor asVendor(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFVendor) m;
		default: return null;
		}
	}
	
	public static OFMatchStandard createMatchStandard(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMatchStandard();
		default: return null;
		}
	}
	
	public static OFMatchStandard asMatchStandard(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMatchStandard) m;
		default: return null;
		}
	}
	
	public static OFGroupDescStatsEntry createGroupDescStatsEntry(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFGroupDescStatsEntry();
		default: return null;
		}
	}
	
	public static OFGroupDescStatsEntry asGroupDescStatsEntry(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFGroupDescStatsEntry) m;
		default: return null;
		}
	}
	
	public static OFActionSetDlDst createActionSetDlDst(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetDlDst();
		default: return null;
		}
	}
	
	public static OFActionSetDlDst asActionSetDlDst(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetDlDst) m;
		default: return null;
		}
	}
	
	public static OFStatisticsExperimenterRequest createStatisticsExperimenterRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsExperimenterRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsExperimenterRequest asStatisticsExperimenterRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsExperimenterRequest) m;
		default: return null;
		}
	}
	
	public static OFActionSetTpDst createActionSetTpDst(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetTpDst();
		default: return null;
		}
	}
	
	public static OFActionSetTpDst asActionSetTpDst(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetTpDst) m;
		default: return null;
		}
	}
	
	public static OFRoleRequest createRoleRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFRoleRequest();
		default: return null;
		}
	}
	
	public static OFRoleRequest asRoleRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFRoleRequest) m;
		default: return null;
		}
	}
	
	public static OFActionSetTpSrc createActionSetTpSrc(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetTpSrc();
		default: return null;
		}
	}
	
	public static OFActionSetTpSrc asActionSetTpSrc(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetTpSrc) m;
		default: return null;
		}
	}
	
	public static OFFlowMod createFlowMod(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowMod();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowMod();
		default: return null;
		}
	}
	
	public static OFFlowMod asFlowMod(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowMod) m;
		case 0x04: return (OFFlowMod) m;
		default: return null;
		}
	}
	
	public static OFPortStatsEntry createPortStatsEntry(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFPortStatsEntry();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFPortStatsEntry();
		default: return null;
		}
	}
	
	public static OFPortStatsEntry asPortStatsEntry(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFPortStatsEntry) m;
		case 0x04: return (OFPortStatsEntry) m;
		default: return null;
		}
	}
	
	public static OFFlowStatsEntry createFlowStatsEntry(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowStatsEntry();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowStatsEntry();
		default: return null;
		}
	}
	
	public static OFFlowStatsEntry asFlowStatsEntry(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowStatsEntry) m;
		case 0x04: return (OFFlowStatsEntry) m;
		default: return null;
		}
	}
	
	public static OFFlowDeleteStrict createFlowDeleteStrict(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowDeleteStrict();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowDeleteStrict();
		default: return null;
		}
	}
	
	public static OFFlowDeleteStrict asFlowDeleteStrict(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowDeleteStrict) m;
		case 0x04: return (OFFlowDeleteStrict) m;
		default: return null;
		}
	}
	
	public static OFFlowDelete createFlowDelete(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowDelete();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowDelete();
		default: return null;
		}
	}
	
	public static OFFlowDelete asFlowDelete(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowDelete) m;
		case 0x04: return (OFFlowDelete) m;
		default: return null;
		}
	}
	
	public static OFHelloElemVersionbitmap createHelloElemVersionbitmap(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFHelloElemVersionbitmap();
		default: return null;
		}
	}
	
	public static OFHelloElemVersionbitmap asHelloElemVersionbitmap(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFHelloElemVersionbitmap) m;
		default: return null;
		}
	}
	
	public static OFHello createHello(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFHello();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFHello();
		default: return null;
		}
	}
	
	public static OFHello asHello(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFHello) m;
		case 0x04: return (OFHello) m;
		default: return null;
		}
	}
	
	public static OFStatisticsFlowRequest createStatisticsFlowRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsFlowRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsFlowRequest asStatisticsFlowRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsFlowRequest) m;
		case 0x04: return (OFStatisticsFlowRequest) m;
		default: return null;
		}
	}
	
	public static OFInstructionExperimenter createInstructionExperimenter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstructionExperimenter();
		default: return null;
		}
	}
	
	public static OFInstructionExperimenter asInstructionExperimenter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstructionExperimenter) m;
		default: return null;
		}
	}
	
	public static OFStatisticsAggregateReply createStatisticsAggregateReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsAggregateReply();
		default: return null;
		}
	}
	
	public static OFStatisticsAggregateReply asStatisticsAggregateReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsAggregateReply) m;
		case 0x04: return (OFStatisticsAggregateReply) m;
		default: return null;
		}
	}
	
	public static OFSetAsync createSetAsync(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFSetAsync();
		default: return null;
		}
	}
	
	public static OFSetAsync asSetAsync(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFSetAsync) m;
		default: return null;
		}
	}
	
	public static OFFlowModify createFlowModify(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowModify();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowModify();
		default: return null;
		}
	}
	
	public static OFFlowModify asFlowModify(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowModify) m;
		case 0x04: return (OFFlowModify) m;
		default: return null;
		}
	}
	
	public static OFTableFeatureProperty createTableFeatureProperty(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeatureProperty();
		default: return null;
		}
	}
	
	public static OFTableFeatureProperty asTableFeatureProperty(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeatureProperty) m;
		default: return null;
		}
	}
	
	public static OFMessage createMessage(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFMessage();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMessage();
		default: return null;
		}
	}
	
	public static OFMessage asMessage(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFMessage) m;
		case 0x04: return (OFMessage) m;
		default: return null;
		}
	}
	
	public static OFTableFeatures createTableFeatures(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeatures();
		default: return null;
		}
	}
	
	public static OFTableFeatures asTableFeatures(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeatures) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplyActions createTableFeaturePropertyApplyActions(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplyActions();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplyActions asTableFeaturePropertyApplyActions(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyApplyActions) m;
		default: return null;
		}
	}
	
	public static OFQueueGetConfigReply createQueueGetConfigReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFQueueGetConfigReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFQueueGetConfigReply();
		default: return null;
		}
	}
	
	public static OFQueueGetConfigReply asQueueGetConfigReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFQueueGetConfigReply) m;
		case 0x04: return (OFQueueGetConfigReply) m;
		default: return null;
		}
	}
	
	public static OFInstructionGotoTable createInstructionGotoTable(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstructionGotoTable();
		default: return null;
		}
	}
	
	public static OFInstructionGotoTable asInstructionGotoTable(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstructionGotoTable) m;
		default: return null;
		}
	}
	
	public static OFActionPopVlan createActionPopVlan(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionPopVlan();
		default: return null;
		}
	}
	
	public static OFActionPopVlan asActionPopVlan(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionPopVlan) m;
		default: return null;
		}
	}
	
	public static OFActionPopMpls createActionPopMpls(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionPopMpls();
		default: return null;
		}
	}
	
	public static OFActionPopMpls asActionPopMpls(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionPopMpls) m;
		default: return null;
		}
	}
	
	public static OFMeterBandDscpRemark createMeterBandDscpRemark(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterBandDscpRemark();
		default: return null;
		}
	}
	
	public static OFMeterBandDscpRemark asMeterBandDscpRemark(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterBandDscpRemark) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplySetfieldMiss createTableFeaturePropertyApplySetfieldMiss(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplySetfieldMiss();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplySetfieldMiss asTableFeaturePropertyApplySetfieldMiss(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyApplySetfieldMiss) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteActionsMiss createTableFeaturePropertyWriteActionsMiss(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteActionsMiss();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteActionsMiss asTableFeaturePropertyWriteActionsMiss(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyWriteActionsMiss) m;
		default: return null;
		}
	}
	
	public static OFActionSetNwTos createActionSetNwTos(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetNwTos();
		default: return null;
		}
	}
	
	public static OFActionSetNwTos asActionSetNwTos(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetNwTos) m;
		default: return null;
		}
	}
	
	public static OFMeterBandDrop createMeterBandDrop(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterBandDrop();
		default: return null;
		}
	}
	
	public static OFMeterBandDrop asMeterBandDrop(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterBandDrop) m;
		default: return null;
		}
	}
	
	public static OFActionDecNwTtl createActionDecNwTtl(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionDecNwTtl();
		default: return null;
		}
	}
	
	public static OFActionDecNwTtl asActionDecNwTtl(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionDecNwTtl) m;
		default: return null;
		}
	}
	
	public static OFActionOutput createActionOutput(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionOutput();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionOutput();
		default: return null;
		}
	}
	
	public static OFActionOutput asActionOutput(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionOutput) m;
		case 0x04: return (OFActionOutput) m;
		default: return null;
		}
	}
	
	public static OFQueueProperty createQueueProperty(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFQueueProperty();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFQueueProperty();
		default: return null;
		}
	}
	
	public static OFQueueProperty asQueueProperty(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFQueueProperty) m;
		case 0x04: return (OFQueueProperty) m;
		default: return null;
		}
	}
	
	public static OFStatisticsGroupFeaturesRequest createStatisticsGroupFeaturesRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupFeaturesRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsGroupFeaturesRequest asStatisticsGroupFeaturesRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsGroupFeaturesRequest) m;
		default: return null;
		}
	}
	
	public static OFTableStatsEntry createTableStatsEntry(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFTableStatsEntry();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableStatsEntry();
		default: return null;
		}
	}
	
	public static OFTableStatsEntry asTableStatsEntry(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFTableStatsEntry) m;
		case 0x04: return (OFTableStatsEntry) m;
		default: return null;
		}
	}
	
	public static OFEchoReply createEchoReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFEchoReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFEchoReply();
		default: return null;
		}
	}
	
	public static OFEchoReply asEchoReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFEchoReply) m;
		case 0x04: return (OFEchoReply) m;
		default: return null;
		}
	}
	
	public static OFQueueStatsEntry createQueueStatsEntry(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFQueueStatsEntry();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFQueueStatsEntry();
		default: return null;
		}
	}
	
	public static OFQueueStatsEntry asQueueStatsEntry(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFQueueStatsEntry) m;
		case 0x04: return (OFQueueStatsEntry) m;
		default: return null;
		}
	}
	
	public static OFStatisticsGroupRequest createStatisticsGroupRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsGroupRequest asStatisticsGroupRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsGroupRequest) m;
		default: return null;
		}
	}
	
	public static OFExperimenter createExperimenter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFExperimenter();
		default: return null;
		}
	}
	
	public static OFExperimenter asExperimenter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFExperimenter) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplySetfield createTableFeaturePropertyApplySetfield(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplySetfield();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplySetfield asTableFeaturePropertyApplySetfield(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyApplySetfield) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteActions createTableFeaturePropertyWriteActions(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteActions();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteActions asTableFeaturePropertyWriteActions(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyWriteActions) m;
		default: return null;
		}
	}
	
	public static OFStatisticsDescRequest createStatisticsDescRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsDescRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsDescRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsDescRequest asStatisticsDescRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsDescRequest) m;
		case 0x04: return (OFStatisticsDescRequest) m;
		default: return null;
		}
	}
	
	public static OFStatisticsGroupDescRequest createStatisticsGroupDescRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupDescRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsGroupDescRequest asStatisticsGroupDescRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsGroupDescRequest) m;
		default: return null;
		}
	}
	
	public static OFMeterBandExperimenter createMeterBandExperimenter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterBandExperimenter();
		default: return null;
		}
	}
	
	public static OFMeterBandExperimenter asMeterBandExperimenter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterBandExperimenter) m;
		default: return null;
		}
	}
	
	public static OFInstruction createInstruction(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstruction();
		default: return null;
		}
	}
	
	public static OFInstruction asInstruction(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstruction) m;
		default: return null;
		}
	}
	
	public static OFQueuePropertyMinRate createQueuePropertyMinRate(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFQueuePropertyMinRate();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFQueuePropertyMinRate();
		default: return null;
		}
	}
	
	public static OFQueuePropertyMinRate asQueuePropertyMinRate(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFQueuePropertyMinRate) m;
		case 0x04: return (OFQueuePropertyMinRate) m;
		default: return null;
		}
	}
	
	public static OFStatisticsDescReply createStatisticsDescReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsDescReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsDescReply();
		default: return null;
		}
	}
	
	public static OFStatisticsDescReply asStatisticsDescReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsDescReply) m;
		case 0x04: return (OFStatisticsDescReply) m;
		default: return null;
		}
	}
	
	public static OFBucketCounter createBucketCounter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFBucketCounter();
		default: return null;
		}
	}
	
	public static OFBucketCounter asBucketCounter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFBucketCounter) m;
		default: return null;
		}
	}
	
	public static OFAction createAction(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFAction();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFAction();
		default: return null;
		}
	}
	
	public static OFAction asAction(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFAction) m;
		case 0x04: return (OFAction) m;
		default: return null;
		}
	}
	
	public static OFActionCopyTtlOut createActionCopyTtlOut(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionCopyTtlOut();
		default: return null;
		}
	}
	
	public static OFActionCopyTtlOut asActionCopyTtlOut(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionCopyTtlOut) m;
		default: return null;
		}
	}
	
	public static OFGroupStatsEntry createGroupStatsEntry(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFGroupStatsEntry();
		default: return null;
		}
	}
	
	public static OFGroupStatsEntry asGroupStatsEntry(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFGroupStatsEntry) m;
		default: return null;
		}
	}
	
	public static OFTableMod createTableMod(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableMod();
		default: return null;
		}
	}
	
	public static OFTableMod asTableMod(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableMod) m;
		default: return null;
		}
	}
	
	public static OFInstructionWriteActions createInstructionWriteActions(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstructionWriteActions();
		default: return null;
		}
	}
	
	public static OFInstructionWriteActions asInstructionWriteActions(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstructionWriteActions) m;
		default: return null;
		}
	}
	
	public static OFActionDecMplsTtl createActionDecMplsTtl(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionDecMplsTtl();
		default: return null;
		}
	}
	
	public static OFActionDecMplsTtl asActionDecMplsTtl(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionDecMplsTtl) m;
		default: return null;
		}
	}
	
	public static OFStatisticsPortStatsReply createStatisticsPortStatsReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortStatsReply();
		default: return null;
		}
	}
	
	public static OFStatisticsPortStatsReply asStatisticsPortStatsReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsPortStatsReply) m;
		default: return null;
		}
	}
	
	public static OFRoleReply createRoleReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFRoleReply();
		default: return null;
		}
	}
	
	public static OFRoleReply asRoleReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFRoleReply) m;
		default: return null;
		}
	}
	
	public static OFMeterFeatures createMeterFeatures(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterFeatures();
		default: return null;
		}
	}
	
	public static OFMeterFeatures asMeterFeatures(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterFeatures) m;
		default: return null;
		}
	}
	
	public static OFEchoRequest createEchoRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFEchoRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFEchoRequest();
		default: return null;
		}
	}
	
	public static OFEchoRequest asEchoRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFEchoRequest) m;
		case 0x04: return (OFEchoRequest) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteSetfield createTableFeaturePropertyWriteSetfield(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyWriteSetfield();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyWriteSetfield asTableFeaturePropertyWriteSetfield(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyWriteSetfield) m;
		default: return null;
		}
	}
	
	public static OFInstructionApplyActions createInstructionApplyActions(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstructionApplyActions();
		default: return null;
		}
	}
	
	public static OFInstructionApplyActions asInstructionApplyActions(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstructionApplyActions) m;
		default: return null;
		}
	}
	
	public static OFInstructionClearActions createInstructionClearActions(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFInstructionClearActions();
		default: return null;
		}
	}
	
	public static OFInstructionClearActions asInstructionClearActions(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFInstructionClearActions) m;
		default: return null;
		}
	}
	
	public static OFPacketIn createPacketIn(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFPacketIn();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFPacketIn();
		default: return null;
		}
	}
	
	public static OFPacketIn asPacketIn(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFPacketIn) m;
		case 0x04: return (OFPacketIn) m;
		default: return null;
		}
	}
	
	public static OFBarrierReply createBarrierReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFBarrierReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFBarrierReply();
		default: return null;
		}
	}
	
	public static OFBarrierReply asBarrierReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFBarrierReply) m;
		case 0x04: return (OFBarrierReply) m;
		default: return null;
		}
	}
	
	public static OFActionSetQueue createActionSetQueue(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionSetQueue();
		default: return null;
		}
	}
	
	public static OFActionSetQueue asActionSetQueue(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionSetQueue) m;
		default: return null;
		}
	}
	
	public static OFMeterMod createMeterMod(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterMod();
		default: return null;
		}
	}
	
	public static OFMeterMod asMeterMod(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterMod) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyExperimenter createTableFeaturePropertyExperimenter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyExperimenter();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyExperimenter asTableFeaturePropertyExperimenter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyExperimenter) m;
		default: return null;
		}
	}
	
	public static OFActionSetMplsTtl createActionSetMplsTtl(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionSetMplsTtl();
		default: return null;
		}
	}
	
	public static OFActionSetMplsTtl asActionSetMplsTtl(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionSetMplsTtl) m;
		default: return null;
		}
	}
	
	public static OFStatisticsVendorReply createStatisticsVendorReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsVendorReply();
		default: return null;
		}
	}
	
	public static OFStatisticsVendorReply asStatisticsVendorReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsVendorReply) m;
		default: return null;
		}
	}
	
	public static OFQueuePropertyExperimenter createQueuePropertyExperimenter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFQueuePropertyExperimenter();
		default: return null;
		}
	}
	
	public static OFQueuePropertyExperimenter asQueuePropertyExperimenter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFQueuePropertyExperimenter) m;
		default: return null;
		}
	}
	
	public static OFMeterBand createMeterBand(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterBand();
		default: return null;
		}
	}
	
	public static OFMeterBand asMeterBand(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterBand) m;
		default: return null;
		}
	}
	
	public static OFStatisticsMeterRequest createStatisticsMeterRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsMeterRequest asStatisticsMeterRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsMeterRequest) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyInstructions createTableFeaturePropertyInstructions(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyInstructions();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyInstructions asTableFeaturePropertyInstructions(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyInstructions) m;
		default: return null;
		}
	}
	
	public static OFHelloElem createHelloElem(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFHelloElem();
		default: return null;
		}
	}
	
	public static OFHelloElem asHelloElem(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFHelloElem) m;
		default: return null;
		}
	}
	
	public static OFActionSetVlanPcp createActionSetVlanPcp(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetVlanPcp();
		default: return null;
		}
	}
	
	public static OFActionSetVlanPcp asActionSetVlanPcp(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetVlanPcp) m;
		default: return null;
		}
	}
	
	public static OFStatisticsMeterConfigReply createStatisticsMeterConfigReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterConfigReply();
		default: return null;
		}
	}
	
	public static OFStatisticsMeterConfigReply asStatisticsMeterConfigReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsMeterConfigReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsGroupReply createStatisticsGroupReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupReply();
		default: return null;
		}
	}
	
	public static OFStatisticsGroupReply asStatisticsGroupReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsGroupReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsMeterConfigRequest createStatisticsMeterConfigRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterConfigRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsMeterConfigRequest asStatisticsMeterConfigRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsMeterConfigRequest) m;
		default: return null;
		}
	}
	
	public static OFActionSetNwSrc createActionSetNwSrc(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFActionSetNwSrc();
		default: return null;
		}
	}
	
	public static OFActionSetNwSrc asActionSetNwSrc(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFActionSetNwSrc) m;
		default: return null;
		}
	}
	
	public static OFPortMod createPortMod(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFPortMod();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFPortMod();
		default: return null;
		}
	}
	
	public static OFPortMod asPortMod(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFPortMod) m;
		case 0x04: return (OFPortMod) m;
		default: return null;
		}
	}
	
	public static OFActionExperimenter createActionExperimenter(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionExperimenter();
		default: return null;
		}
	}
	
	public static OFActionExperimenter asActionExperimenter(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionExperimenter) m;
		default: return null;
		}
	}
	
	public static OFMeterStats createMeterStats(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFMeterStats();
		default: return null;
		}
	}
	
	public static OFMeterStats asMeterStats(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFMeterStats) m;
		default: return null;
		}
	}
	
	public static OFActionPushPbb createActionPushPbb(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionPushPbb();
		default: return null;
		}
	}
	
	public static OFActionPushPbb asActionPushPbb(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionPushPbb) m;
		default: return null;
		}
	}
	
	public static OFFlowRemoved createFlowRemoved(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFFlowRemoved();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFFlowRemoved();
		default: return null;
		}
	}
	
	public static OFFlowRemoved asFlowRemoved(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFFlowRemoved) m;
		case 0x04: return (OFFlowRemoved) m;
		default: return null;
		}
	}
	
	public static OFPortDesc createPortDesc(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFPortDesc();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFPortDesc();
		default: return null;
		}
	}
	
	public static OFPortDesc asPortDesc(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFPortDesc) m;
		case 0x04: return (OFPortDesc) m;
		default: return null;
		}
	}
	
	public static OFBucket createBucket(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFBucket();
		default: return null;
		}
	}
	
	public static OFBucket asBucket(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFBucket) m;
		default: return null;
		}
	}
	
	public static OFQueuePropertyNone createQueuePropertyNone(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFQueuePropertyNone();
		default: return null;
		}
	}
	
	public static OFQueuePropertyNone asQueuePropertyNone(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFQueuePropertyNone) m;
		default: return null;
		}
	}
	
	public static OFStatisticsTableRequest createStatisticsTableRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsTableRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsTableRequest asStatisticsTableRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsTableRequest) m;
		case 0x04: return (OFStatisticsTableRequest) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyMatch createTableFeaturePropertyMatch(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyMatch();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyMatch asTableFeaturePropertyMatch(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyMatch) m;
		default: return null;
		}
	}
	
	public static OFStatisticsQueueRequest createStatisticsQueueRequest(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsQueueRequest();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsQueueRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsQueueRequest asStatisticsQueueRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsQueueRequest) m;
		case 0x04: return (OFStatisticsQueueRequest) m;
		default: return null;
		}
	}
	
	public static OFGetConfigReply createGetConfigReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFGetConfigReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFGetConfigReply();
		default: return null;
		}
	}
	
	public static OFGetConfigReply asGetConfigReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFGetConfigReply) m;
		case 0x04: return (OFGetConfigReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsReply createStatisticsReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsReply();
		default: return null;
		}
	}
	
	public static OFStatisticsReply asStatisticsReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsReply) m;
		case 0x04: return (OFStatisticsReply) m;
		default: return null;
		}
	}
	
	public static OFGroupMod createGroupMod(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFGroupMod();
		default: return null;
		}
	}
	
	public static OFGroupMod asGroupMod(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFGroupMod) m;
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplyActionsMiss createTableFeaturePropertyApplyActionsMiss(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFTableFeaturePropertyApplyActionsMiss();
		default: return null;
		}
	}
	
	public static OFTableFeaturePropertyApplyActionsMiss asTableFeaturePropertyApplyActionsMiss(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFTableFeaturePropertyApplyActionsMiss) m;
		default: return null;
		}
	}
	
	public static OFPacketQueue createPacketQueue(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFPacketQueue();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFPacketQueue();
		default: return null;
		}
	}
	
	public static OFPacketQueue asPacketQueue(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFPacketQueue) m;
		case 0x04: return (OFPacketQueue) m;
		default: return null;
		}
	}
	
	public static OFStatisticsMeterFeaturesReply createStatisticsMeterFeaturesReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterFeaturesReply();
		default: return null;
		}
	}
	
	public static OFStatisticsMeterFeaturesReply asStatisticsMeterFeaturesReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsMeterFeaturesReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsGroupDescReply createStatisticsGroupDescReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupDescReply();
		default: return null;
		}
	}
	
	public static OFStatisticsGroupDescReply asStatisticsGroupDescReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsGroupDescReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsPortReply createStatisticsPortReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsPortReply();
		default: return null;
		}
	}
	
	public static OFStatisticsPortReply asStatisticsPortReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsPortReply) m;
		default: return null;
		}
	}
	
	public static OFActionPopPbb createActionPopPbb(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFActionPopPbb();
		default: return null;
		}
	}
	
	public static OFActionPopPbb asActionPopPbb(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFActionPopPbb) m;
		default: return null;
		}
	}
	
	public static OFStatisticsQueueReply createStatisticsQueueReply(byte version) {
		switch(version) {
		case 0x01: return new org.openflow.protocol.ver1_0.messages.OFStatisticsQueueReply();
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsQueueReply();
		default: return null;
		}
	}
	
	public static OFStatisticsQueueReply asStatisticsQueueReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x01: return (OFStatisticsQueueReply) m;
		case 0x04: return (OFStatisticsQueueReply) m;
		default: return null;
		}
	}
	
	public static OFStatisticsTableFeaturesRequest createStatisticsTableFeaturesRequest(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableFeaturesRequest();
		default: return null;
		}
	}
	
	public static OFStatisticsTableFeaturesRequest asStatisticsTableFeaturesRequest(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsTableFeaturesRequest) m;
		default: return null;
		}
	}
	
	public static OFStatisticsGroupFeaturesReply createStatisticsGroupFeaturesReply(byte version) {
		switch(version) {
		case 0x04: return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupFeaturesReply();
		default: return null;
		}
	}
	
	public static OFStatisticsGroupFeaturesReply asStatisticsGroupFeaturesReply(byte version, org.openflow.protocol.OFMessage m) {
		switch(version) {
		case 0x04: return (OFStatisticsGroupFeaturesReply) m;
		default: return null;
		}
	}
	
}
