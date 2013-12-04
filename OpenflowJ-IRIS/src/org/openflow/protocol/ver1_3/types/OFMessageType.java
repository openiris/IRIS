package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFMessageType {
    HELLO	(0, OFHello.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFHello();
    }}),
	ERROR	(1, OFError.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFError();
    }}),
	ECHO_REQUEST	(2, OFEchoRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFEchoRequest();
    }}),
	ECHO_REPLY	(3, OFEchoReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFEchoReply();
    }}),
	EXPERIMENTER	(4, OFExperimenter.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFExperimenter();
    }}),
	FEATURES_REQUEST	(5, OFFeaturesRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFFeaturesRequest();
    }}),
	FEATURES_REPLY	(6, OFFeaturesReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFFeaturesReply();
    }}),
	GET_CONFIG_REQUEST	(7, OFGetConfigRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFGetConfigRequest();
    }}),
	GET_CONFIG_REPLY	(8, OFGetConfigReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFGetConfigReply();
    }}),
	SET_CONFIG	(9, OFSetConfig.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFSetConfig();
    }}),
	PACKET_IN	(10, OFPacketIn.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFPacketIn();
    }}),
	FLOW_REMOVED	(11, OFFlowRemoved.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFFlowRemoved();
    }}),
	PORT_STATUS	(12, OFPortStatus.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFPortStatus();
    }}),
	PACKET_OUT	(13, OFPacketOut.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFPacketOut();
    }}),
	FLOW_MOD	(14, OFFlowMod.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFFlowMod();
    }}),
	GROUP_MOD	(15, OFGroupMod.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFGroupMod();
    }}),
	PORT_MOD	(16, OFPortMod.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFPortMod();
    }}),
	TABLE_MOD	(17, OFTableMod.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFTableMod();
    }}),
	MULTIPART_REQUEST	(18, OFMultipartRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFMultipartRequest();
    }}),
	MULTIPART_REPLY	(19, OFMultipartReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFMultipartReply();
    }}),
	BARRIER_REQUEST	(20, OFBarrierRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFBarrierRequest();
    }}),
	BARRIER_REPLY	(21, OFBarrierReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFBarrierReply();
    }}),
	QUEUE_GET_CONFIG_REQUEST	(22, OFQueueGetConfigRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFQueueGetConfigRequest();
    }}),
	QUEUE_GET_CONFIG_REPLY	(23, OFQueueGetConfigReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFQueueGetConfigReply();
    }}),
	ROLE_REQUEST	(24, OFRoleRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFRoleRequest();
    }}),
	ROLE_REPLY	(25, OFRoleReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFRoleReply();
    }}),
	GET_ASYNC_REQUEST	(26, OFGetAsyncRequest.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFGetAsyncRequest();
    }}),
	GET_ASYNC_REPLY	(27, OFGetAsyncReply.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFGetAsyncReply();
    }}),
	SET_ASYNC	(28, OFSetAsync.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFSetAsync();
    }}),
	METER_MOD	(29, OFMeterMod.class, new Instantiable<OFMessage>() {
    public OFMessage instantiate() {
      return new OFMeterMod();
    }});

    static OFMessageType[] mapping;

    protected Class<? extends OFMessage> clazz;
    protected Constructor<? extends OFMessage> constructor;
    protected Instantiable<OFMessage> instantiable;
    protected byte type;

    OFMessageType(int type, Class<? extends OFMessage> clazz, Instantiable<OFMessage> instantiator) {
        this.type = (byte) type;
        this.clazz = clazz;
        this.instantiable = instantiator;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        OFMessageType.addMapping(this.type, this);
    }

    static public void addMapping(byte i, OFMessageType t) {
        if (mapping == null)
            mapping = new OFMessageType[30];
        if ( i < 0 ) i = (byte)(30 + i);
        OFMessageType.mapping[i] = t;
    }

    static public OFMessageType valueOf(byte i) {
        if ( i < 0 ) i = (byte)(30 + i);
        return OFMessageType.mapping[i];
    }
    
	static public byte readFrom(ByteBuffer data) {
  		return data.get();
	}
    
    static public OFMessageType first() {
    	return OFMessageType.mapping[0];
    }
    
    static public OFMessageType last() {
    	return OFMessageType.mapping[OFMessageType.mapping.length - 1];
    }
    
    static public void parse(List<OFMessage> output, ByteBuffer data, int length) {
    	OFMessage demux = new OFMessage();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFMessage real = demux.getType().newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFMessage> to_write, ByteBuffer data) {
    	for ( OFMessage i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public byte getTypeValue() {
        return this.type;
    }

    public Class<? extends OFMessage> toClass() {
        return clazz;
    }

    public Constructor<? extends OFMessage> getConstructor() {
        return constructor;
    }

    public OFMessage newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFMessage> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFMessage> instantiable) {
        this.instantiable = instantiable;
    }
}