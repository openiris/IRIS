package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_0.messages.*;

public enum OFMessageType {
    HELLO	(0, org.openflow.protocol.interfaces.OFMessageType.HELLO, 
	OFHello.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFHello();
    	}
    }),
	ERROR	(1, org.openflow.protocol.interfaces.OFMessageType.ERROR, 
	OFError.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFError();
    	}
    }),
	ECHO_REQUEST	(2, org.openflow.protocol.interfaces.OFMessageType.ECHO_REQUEST, 
	OFEchoRequest.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFEchoRequest();
    	}
    }),
	ECHO_REPLY	(3, org.openflow.protocol.interfaces.OFMessageType.ECHO_REPLY, 
	OFEchoReply.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFEchoReply();
    	}
    }),
	VENDOR	(4, org.openflow.protocol.interfaces.OFMessageType.VENDOR, 
	OFVendor.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFVendor();
    	}
    }),
	FEATURES_REQUEST	(5, org.openflow.protocol.interfaces.OFMessageType.FEATURES_REQUEST, 
	OFFeaturesRequest.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFFeaturesRequest();
    	}
    }),
	FEATURES_REPLY	(6, org.openflow.protocol.interfaces.OFMessageType.FEATURES_REPLY, 
	OFFeaturesReply.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFFeaturesReply();
    	}
    }),
	GET_CONFIG_REQUEST	(7, org.openflow.protocol.interfaces.OFMessageType.GET_CONFIG_REQUEST, 
	OFGetConfigRequest.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFGetConfigRequest();
    	}
    }),
	GET_CONFIG_REPLY	(8, org.openflow.protocol.interfaces.OFMessageType.GET_CONFIG_REPLY, 
	OFGetConfigReply.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFGetConfigReply();
    	}
    }),
	SET_CONFIG	(9, org.openflow.protocol.interfaces.OFMessageType.SET_CONFIG, 
	OFSetConfig.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFSetConfig();
    	}
    }),
	PACKET_IN	(10, org.openflow.protocol.interfaces.OFMessageType.PACKET_IN, 
	OFPacketIn.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFPacketIn();
    	}
    }),
	FLOW_REMOVED	(11, org.openflow.protocol.interfaces.OFMessageType.FLOW_REMOVED, 
	OFFlowRemoved.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFFlowRemoved();
    	}
    }),
	PORT_STATUS	(12, org.openflow.protocol.interfaces.OFMessageType.PORT_STATUS, 
	OFPortStatus.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFPortStatus();
    	}
    }),
	PACKET_OUT	(13, org.openflow.protocol.interfaces.OFMessageType.PACKET_OUT, 
	OFPacketOut.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFPacketOut();
    	}
    }),
	FLOW_MOD	(14, org.openflow.protocol.interfaces.OFMessageType.FLOW_MOD, 
	OFFlowMod.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFFlowMod();
    	}
    }),
	PORT_MOD	(15, org.openflow.protocol.interfaces.OFMessageType.PORT_MOD, 
	OFPortMod.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFPortMod();
    	}
    }),
	STATISTICS_REQUEST	(16, org.openflow.protocol.interfaces.OFMessageType.STATISTICS_REQUEST, 
	OFStatisticsRequest.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFStatisticsRequest();
    	}
    }),
	STATISTICS_REPLY	(17, org.openflow.protocol.interfaces.OFMessageType.STATISTICS_REPLY, 
	OFStatisticsReply.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFStatisticsReply();
    	}
    }),
	BARRIER_REQUEST	(18, org.openflow.protocol.interfaces.OFMessageType.BARRIER_REQUEST, 
	OFBarrierRequest.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFBarrierRequest();
    	}
    }),
	BARRIER_REPLY	(19, org.openflow.protocol.interfaces.OFMessageType.BARRIER_REPLY, 
	OFBarrierReply.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFBarrierReply();
    	}
    }),
	QUEUE_GET_CONFIG_REQUEST	(20, org.openflow.protocol.interfaces.OFMessageType.QUEUE_GET_CONFIG_REQUEST, 
	OFQueueGetConfigRequest.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFQueueGetConfigRequest();
    	}
    }),
	QUEUE_GET_CONFIG_REPLY	(21, org.openflow.protocol.interfaces.OFMessageType.QUEUE_GET_CONFIG_REPLY, 
	OFQueueGetConfigReply.class, 
	new Instantiable<OFMessage>() {
    	public OFMessage instantiate() {
      		return new OFQueueGetConfigReply();
    	}
    });

    // static OFMessageType[] mapping;
    static Map<Byte, OFMessageType> mapping;
    static Map<Byte, org.openflow.protocol.interfaces.OFMessageType> compatMapping;
    static Map<org.openflow.protocol.interfaces.OFMessageType, OFMessageType> compatMappingReverse;
    static byte start_key = 0;
    static byte end_key = 0;

    protected Class<? extends OFMessage> clazz;
    protected Constructor<? extends OFMessage> constructor;
    protected Instantiable<OFMessage> instantiable;
    protected byte type;

    OFMessageType(
    	int type, org.openflow.protocol.interfaces.OFMessageType compatType,
    	Class<? extends OFMessage> clazz, Instantiable<OFMessage> instantiator) 
    {
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
        OFMessageType.addMapping(this.type, compatType, this);
    }

    static public void addMapping(byte i, OFMessageType t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Byte, OFMessageType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping(byte i, org.openflow.protocol.interfaces.OFMessageType c, OFMessageType t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<Byte, org.openflow.protocol.interfaces.OFMessageType>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMessageType, OFMessageType>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public OFMessageType valueOf(byte i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFMessageType to(OFMessageType i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFMessageType from(org.openflow.protocol.interfaces.OFMessageType c) {
    	return compatMappingReverse.get(c);
    }
    
	static public byte readFrom(ByteBuffer data) {
  		return data.get();
	}
    
    static public OFMessageType first() {
    	return mapping.get(start_key);
    }
    
    static public OFMessageType last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFMessage> output, ByteBuffer data, int length) {
    	OFMessage demux = new OFMessage();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFMessage real = OFMessageType.from(demux.getType()).newInstance();
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