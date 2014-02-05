package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFActionType {
    OUTPUT	(0, org.openflow.protocol.interfaces.OFActionType.OUTPUT, 
	OFActionOutput.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionOutput();
    	}
    }),
	COPY_TTL_OUT	(0xb, org.openflow.protocol.interfaces.OFActionType.COPY_TTL_OUT, 
	OFActionCopyTtlOut.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionCopyTtlOut();
    	}
    }),
	COPY_TTL_IN	(0xc, org.openflow.protocol.interfaces.OFActionType.COPY_TTL_IN, 
	OFActionCopyTtlIn.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionCopyTtlIn();
    	}
    }),
	SET_MPLS_TTL	(0xf, org.openflow.protocol.interfaces.OFActionType.SET_MPLS_TTL, 
	OFActionSetMplsTtl.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetMplsTtl();
    	}
    }),
	DEC_MPLS_TTL	(0x10, org.openflow.protocol.interfaces.OFActionType.DEC_MPLS_TTL, 
	OFActionDecMplsTtl.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionDecMplsTtl();
    	}
    }),
	PUSH_VLAN	(0x11, org.openflow.protocol.interfaces.OFActionType.PUSH_VLAN, 
	OFActionPushVlan.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionPushVlan();
    	}
    }),
	POP_VLAN	(0x12, org.openflow.protocol.interfaces.OFActionType.POP_VLAN, 
	OFActionPopVlan.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionPopVlan();
    	}
    }),
	PUSH_MPLS	(0x13, org.openflow.protocol.interfaces.OFActionType.PUSH_MPLS, 
	OFActionPushMpls.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionPushMpls();
    	}
    }),
	POP_MPLS	(0x14, org.openflow.protocol.interfaces.OFActionType.POP_MPLS, 
	OFActionPopMpls.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionPopMpls();
    	}
    }),
	SET_QUEUE	(0x15, org.openflow.protocol.interfaces.OFActionType.SET_QUEUE, 
	OFActionSetQueue.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetQueue();
    	}
    }),
	GROUP	(0x16, org.openflow.protocol.interfaces.OFActionType.GROUP, 
	OFActionGroup.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionGroup();
    	}
    }),
	SET_NW_TTL	(0x17, org.openflow.protocol.interfaces.OFActionType.SET_NW_TTL, 
	OFActionSetNwTtl.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetNwTtl();
    	}
    }),
	DEC_NW_TTL	(0x18, org.openflow.protocol.interfaces.OFActionType.DEC_NW_TTL, 
	OFActionDecNwTtl.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionDecNwTtl();
    	}
    }),
	SET_FIELD	(0x19, org.openflow.protocol.interfaces.OFActionType.SET_FIELD, 
	OFActionSetField.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetField();
    	}
    }),
	PUSH_PBB	(0x1a, org.openflow.protocol.interfaces.OFActionType.PUSH_PBB, 
	OFActionPushPbb.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionPushPbb();
    	}
    }),
	POP_PBB	(0x1b, org.openflow.protocol.interfaces.OFActionType.POP_PBB, 
	OFActionPopPbb.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionPopPbb();
    	}
    }),
	EXPERIMENTER	(0xffff, org.openflow.protocol.interfaces.OFActionType.EXPERIMENTER, 
	OFActionExperimenter.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionExperimenter();
    	}
    });

    // static OFActionType[] mapping;
    static Map<Short, OFActionType> mapping;
    static Map<Short, org.openflow.protocol.interfaces.OFActionType> compatMapping;
    static Map<org.openflow.protocol.interfaces.OFActionType, OFActionType> compatMappingReverse;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFAction> clazz;
    protected Constructor<? extends OFAction> constructor;
    protected Instantiable<OFAction> instantiable;
    protected short type;

    OFActionType(
    	int type, org.openflow.protocol.interfaces.OFActionType compatType,
    	Class<? extends OFAction> clazz, Instantiable<OFAction> instantiator) 
    {
        this.type = (short) type;
        this.clazz = clazz;
        this.instantiable = instantiator;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        OFActionType.addMapping(this.type, this);
        OFActionType.addMapping(this.type, compatType, this);
    }

    static public void addMapping(short i, OFActionType t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFActionType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping(short i, org.openflow.protocol.interfaces.OFActionType c, OFActionType t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFActionType>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFActionType, OFActionType>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public OFActionType valueOf(short i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFActionType to(OFActionType i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFActionType from(org.openflow.protocol.interfaces.OFActionType c) {
    	return compatMappingReverse.get(c);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFActionType first() {
    	return mapping.get(start_key);
    }
    
    static public OFActionType last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFAction> output, ByteBuffer data, int length) {
    	OFAction demux = new OFAction();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFAction real = OFActionType.from(demux.getType()).newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFAction> to_write, ByteBuffer data) {
    	for ( OFAction i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFAction> toClass() {
        return clazz;
    }

    public Constructor<? extends OFAction> getConstructor() {
        return constructor;
    }

    public OFAction newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFAction> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFAction> instantiable) {
        this.instantiable = instantiable;
    }
}