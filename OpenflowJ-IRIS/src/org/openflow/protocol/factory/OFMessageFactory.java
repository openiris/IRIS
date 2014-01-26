package org.openflow.protocol.factory;

import org.openflow.protocol.interfaces.*;
import org.openflow.util.Builder;

public class OFMessageFactory {

	/**
	 * String such as '1.0' and '1.3' are allowed.
	 */
	String version;
	
	public OFMessageFactory(String version) {
		this.version = version;
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createErrorBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFError.");
		}
	}
	
	public OFStatisticsRequest createStatisticsRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsRequest.");
		}
	}
	
	public OFStatisticsRequest asStatisticsRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsRequest) m;
		case "1.3": return (OFStatisticsRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsRequest.");
		}
	}
	
	public OFStatisticsAggregateRequest createStatisticsAggregateRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsAggregateRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateRequest.");
		}
	}
	
	public OFStatisticsAggregateRequest asStatisticsAggregateRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsAggregateRequest) m;
		case "1.3": return (OFStatisticsAggregateRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsAggregateRequestBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetNwTtlBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionCopyTtlInBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyWildcardsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsVendorRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsVendorRequest.");
		}
	}
	
	public OFMatch createMatch() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFMatch();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMatchOxm();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatch.");
		}
	}
	
	public OFMatch asMatch(OFMessage m) {
		switch(version) {
		case "1.0": return (OFMatch) m;
		case "1.3": return (OFMatchOxm) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatch.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMatchBuilder() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFMatch.Builder();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFMatchOxm.Builder();
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionGroupBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionGroup.");
		}
	}
	
	public OFStatisticsMeterFeaturesRequest createStatisticsMeterFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterFeaturesRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterFeaturesRequest.");
		}
	}
	
	public OFStatisticsMeterFeaturesRequest asStatisticsMeterFeaturesRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsMeterFeaturesRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterFeaturesRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsMeterFeaturesRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterFeaturesRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyExperimenterMissBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowModifyStrictBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionWriteMetadataBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionWriteMetadata.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createSetConfigBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFSetConfig.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetDlSrcBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetDlSrc.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFeaturesReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFeaturesReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyNextTablesBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionOpaqueEnqueueBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionOpaqueEnqueue.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMatchOxmBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMatchOxm.");
		}
	}
	
	public OFStatisticsMeterReply createStatisticsMeterReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterReply.");
		}
	}
	
	public OFStatisticsMeterReply asStatisticsMeterReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsMeterReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsMeterReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionStripVlanBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionStripVlan.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createPacketOutBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketOut.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createOxmBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueuePropertyMaxRateBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionIdBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueueGetConfigRequestBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyInstructionsMissBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetNwDstBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetNwDst.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyWriteSetfieldMissBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteSetfieldMiss.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createPortStatusBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortStatus.");
		}
	}
	
	public OFStatisticsTableFeaturesReply createStatisticsTableFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableFeaturesReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableFeaturesReply.");
		}
	}
	
	public OFStatisticsTableFeaturesReply asStatisticsTableFeaturesReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsTableFeaturesReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableFeaturesReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsTableFeaturesReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableFeaturesReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyNextTablesMissBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyNextTablesMiss.");
		}
	}
	
	public OFStatisticsExperimenterReply createStatisticsExperimenterReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsExperimenterReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsExperimenterReply.");
		}
	}
	
	public OFStatisticsExperimenterReply asStatisticsExperimenterReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsExperimenterReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsExperimenterReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsExperimenterReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsExperimenterReply.");
		}
	}
	
	public OFStatisticsPortDescReply createStatisticsPortDescReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortDescReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortDescReply.");
		}
	}
	
	public OFStatisticsPortDescReply asStatisticsPortDescReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsPortDescReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortDescReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsPortDescReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortDescReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowAddBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowAdd.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createGetAsyncReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetAsyncReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetFieldBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterBandStatsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFeaturesRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFeaturesRequest.");
		}
	}
	
	public OFStatisticsPortStatsRequest createStatisticsPortStatsRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortStatsRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortStatsRequest.");
		}
	}
	
	public OFStatisticsPortStatsRequest asStatisticsPortStatsRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsPortStatsRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortStatsRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsPortStatsRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortStatsRequest.");
		}
	}
	
	public OFStatistics createStatistics() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatistics();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatistics();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatistics.");
		}
	}
	
	public OFStatistics asStatistics(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatistics) m;
		case "1.3": return (OFStatistics) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatistics.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionPushMplsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetVlanIdBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetVlanId.");
		}
	}
	
	public OFStatisticsTableReply createStatisticsTableReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsTableReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableReply.");
		}
	}
	
	public OFStatisticsTableReply asStatisticsTableReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsTableReply) m;
		case "1.3": return (OFStatisticsTableReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsTableReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsPortRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortRequest.");
		}
	}
	
	public OFStatisticsPortDescRequest createStatisticsPortDescRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortDescRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortDescRequest.");
		}
	}
	
	public OFStatisticsPortDescRequest asStatisticsPortDescRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsPortDescRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortDescRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsPortDescRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortDescRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createGetAsyncRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetAsyncRequest.");
		}
	}
	
	public OFStatisticsFlowReply createStatisticsFlowReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsFlowReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowReply.");
		}
	}
	
	public OFStatisticsFlowReply asStatisticsFlowReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsFlowReply) m;
		case "1.3": return (OFStatisticsFlowReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsFlowReplyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createBarrierRequestBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionPushVlanBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPushVlan.");
		}
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionVendorBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionVendor.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createGetConfigRequestBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionMeterBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createVendorBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMatchStandardBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createGroupDescStatsEntryBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGroupDescStatsEntry.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetDlDstBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetDlDst.");
		}
	}
	
	public OFStatisticsExperimenterRequest createStatisticsExperimenterRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsExperimenterRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsExperimenterRequest.");
		}
	}
	
	public OFStatisticsExperimenterRequest asStatisticsExperimenterRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsExperimenterRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsExperimenterRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsExperimenterRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsExperimenterRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetTpDstBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createRoleRequestBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetTpSrcBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowModBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createPortStatsEntryBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowStatsEntryBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowDeleteStrictBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowDeleteBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createHelloElemVersionbitmapBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createHelloBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFHello.");
		}
	}
	
	public OFStatisticsFlowRequest createStatisticsFlowRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsFlowRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsFlowRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowRequest.");
		}
	}
	
	public OFStatisticsFlowRequest asStatisticsFlowRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsFlowRequest) m;
		case "1.3": return (OFStatisticsFlowRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsFlowRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsFlowRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionExperimenterBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFInstructionExperimenter.");
		}
	}
	
	public OFStatisticsAggregateReply createStatisticsAggregateReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsAggregateReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsAggregateReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateReply.");
		}
	}
	
	public OFStatisticsAggregateReply asStatisticsAggregateReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsAggregateReply) m;
		case "1.3": return (OFStatisticsAggregateReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsAggregateReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsAggregateReplyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createSetAsyncBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowModifyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMessageBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturesBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeatures.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyApplyActionsBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplyActions.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueueGetConfigReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueGetConfigReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionGotoTableBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionPopVlanBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionPopMplsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterBandDscpRemarkBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBandDscpRemark.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyApplySetfieldMissBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyApplySetfieldMiss.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyWriteActionsMissBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetNwTosBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterBandDropBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionDecNwTtlBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionOutputBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueuePropertyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueProperty.");
		}
	}
	
	public OFStatisticsGroupFeaturesRequest createStatisticsGroupFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupFeaturesRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupFeaturesRequest.");
		}
	}
	
	public OFStatisticsGroupFeaturesRequest asStatisticsGroupFeaturesRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsGroupFeaturesRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupFeaturesRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsGroupFeaturesRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupFeaturesRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableStatsEntryBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableStatsEntry.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createEchoReplyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueueStatsEntryBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueueStatsEntry.");
		}
	}
	
	public OFStatisticsGroupRequest createStatisticsGroupRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupRequest.");
		}
	}
	
	public OFStatisticsGroupRequest asStatisticsGroupRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsGroupRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsGroupRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createExperimenterBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyApplySetfieldBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyWriteActionsBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyWriteActions.");
		}
	}
	
	public OFStatisticsDescRequest createStatisticsDescRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsDescRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsDescRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescRequest.");
		}
	}
	
	public OFStatisticsDescRequest asStatisticsDescRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsDescRequest) m;
		case "1.3": return (OFStatisticsDescRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsDescRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescRequest.");
		}
	}
	
	public OFStatisticsGroupDescRequest createStatisticsGroupDescRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupDescRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupDescRequest.");
		}
	}
	
	public OFStatisticsGroupDescRequest asStatisticsGroupDescRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsGroupDescRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupDescRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsGroupDescRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupDescRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterBandExperimenterBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueuePropertyMinRateBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyMinRate.");
		}
	}
	
	public OFStatisticsDescReply createStatisticsDescReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsDescReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsDescReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescReply.");
		}
	}
	
	public OFStatisticsDescReply asStatisticsDescReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsDescReply) m;
		case "1.3": return (OFStatisticsDescReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsDescReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsDescReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createBucketCounterBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionCopyTtlOutBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createGroupStatsEntryBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableModBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionWriteActionsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionDecMplsTtlBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionDecMplsTtl.");
		}
	}
	
	public OFStatisticsPortStatsReply createStatisticsPortStatsReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsPortStatsReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortStatsReply.");
		}
	}
	
	public OFStatisticsPortStatsReply asStatisticsPortStatsReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsPortStatsReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortStatsReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsPortStatsReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsPortStatsReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createRoleReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFRoleReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterFeaturesBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createEchoRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFEchoRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyWriteSetfieldBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionApplyActionsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createInstructionClearActionsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createPacketInBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createBarrierReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBarrierReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetQueueBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetQueue.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterModBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyExperimenterBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetMplsTtlBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsVendorReplyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueuePropertyExperimenterBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyExperimenter.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterBandBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFMeterBand.");
		}
	}
	
	public OFStatisticsMeterRequest createStatisticsMeterRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterRequest.");
		}
	}
	
	public OFStatisticsMeterRequest asStatisticsMeterRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsMeterRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsMeterRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyInstructionsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createHelloElemBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetVlanPcpBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionSetVlanPcp.");
		}
	}
	
	public OFStatisticsMeterConfigReply createStatisticsMeterConfigReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterConfigReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterConfigReply.");
		}
	}
	
	public OFStatisticsMeterConfigReply asStatisticsMeterConfigReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsMeterConfigReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterConfigReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsMeterConfigReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterConfigReply.");
		}
	}
	
	public OFStatisticsGroupReply createStatisticsGroupReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupReply.");
		}
	}
	
	public OFStatisticsGroupReply asStatisticsGroupReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsGroupReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsGroupReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupReply.");
		}
	}
	
	public OFStatisticsMeterConfigRequest createStatisticsMeterConfigRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterConfigRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterConfigRequest.");
		}
	}
	
	public OFStatisticsMeterConfigRequest asStatisticsMeterConfigRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsMeterConfigRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterConfigRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsMeterConfigRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterConfigRequest.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionSetNwSrcBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createPortModBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionExperimenterBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createMeterStatsBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionPushPbbBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createFlowRemovedBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFFlowRemoved.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createPortDescBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPortDesc.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createBucketBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFBucket.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createQueuePropertyNoneBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFQueuePropertyNone.");
		}
	}
	
	public OFStatisticsTableRequest createStatisticsTableRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsTableRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableRequest.");
		}
	}
	
	public OFStatisticsTableRequest asStatisticsTableRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsTableRequest) m;
		case "1.3": return (OFStatisticsTableRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsTableRequestBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyMatchBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFTableFeaturePropertyMatch.");
		}
	}
	
	public OFStatisticsQueueRequest createStatisticsQueueRequest() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsQueueRequest();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsQueueRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueRequest.");
		}
	}
	
	public OFStatisticsQueueRequest asStatisticsQueueRequest(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsQueueRequest) m;
		case "1.3": return (OFStatisticsQueueRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsQueueRequestBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createGetConfigReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFGetConfigReply.");
		}
	}
	
	public OFStatisticsReply createStatisticsReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsReply.");
		}
	}
	
	public OFStatisticsReply asStatisticsReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsReply) m;
		case "1.3": return (OFStatisticsReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsReplyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createGroupModBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createTableFeaturePropertyApplyActionsMissBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createPacketQueueBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFPacketQueue.");
		}
	}
	
	public OFStatisticsMeterFeaturesReply createStatisticsMeterFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsMeterFeaturesReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterFeaturesReply.");
		}
	}
	
	public OFStatisticsMeterFeaturesReply asStatisticsMeterFeaturesReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsMeterFeaturesReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterFeaturesReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsMeterFeaturesReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsMeterFeaturesReply.");
		}
	}
	
	public OFStatisticsGroupDescReply createStatisticsGroupDescReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupDescReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupDescReply.");
		}
	}
	
	public OFStatisticsGroupDescReply asStatisticsGroupDescReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsGroupDescReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupDescReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsGroupDescReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupDescReply.");
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsPortReplyBuilder() {
		switch(version) {
		
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
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createActionPopPbbBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFActionPopPbb.");
		}
	}
	
	public OFStatisticsQueueReply createStatisticsQueueReply() {
		switch(version) {
		case "1.0": return new org.openflow.protocol.ver1_0.messages.OFStatisticsQueueReply();
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsQueueReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueReply.");
		}
	}
	
	public OFStatisticsQueueReply asStatisticsQueueReply(OFMessage m) {
		switch(version) {
		case "1.0": return (OFStatisticsQueueReply) m;
		case "1.3": return (OFStatisticsQueueReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsQueueReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsQueueReply.");
		}
	}
	
	public OFStatisticsTableFeaturesRequest createStatisticsTableFeaturesRequest() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsTableFeaturesRequest();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableFeaturesRequest.");
		}
	}
	
	public OFStatisticsTableFeaturesRequest asStatisticsTableFeaturesRequest(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsTableFeaturesRequest) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableFeaturesRequest.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsTableFeaturesRequestBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsTableFeaturesRequest.");
		}
	}
	
	public OFStatisticsGroupFeaturesReply createStatisticsGroupFeaturesReply() {
		switch(version) {
		case "1.3": return new org.openflow.protocol.ver1_3.messages.OFStatisticsGroupFeaturesReply();
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupFeaturesReply.");
		}
	}
	
	public OFStatisticsGroupFeaturesReply asStatisticsGroupFeaturesReply(OFMessage m) {
		switch(version) {
		case "1.3": return (OFStatisticsGroupFeaturesReply) m;
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupFeaturesReply.");
		}
	}
	
	public org.openflow.protocol.interfaces.OFMatch.Builder createStatisticsGroupFeaturesReplyBuilder() {
		switch(version) {
		
		default:
			throw new IllegalStateException("Version " + this.version + " does not support OFStatisticsGroupFeaturesReply.");
		}
	}
	
}
