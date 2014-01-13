package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_0.messages.*;

public enum OFActionType {
    OUTPUT	(0, org.openflow.protocol.interfaces.OFActionType.OUTPUT, 
	OFActionOutput.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionOutput();
    	}
    }),
	SET_VLAN_ID	(1, org.openflow.protocol.interfaces.OFActionType.SET_VLAN_ID, 
	OFActionSetVlanId.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetVlanId();
    	}
    }),
	SET_VLAN_PCP	(2, org.openflow.protocol.interfaces.OFActionType.SET_VLAN_PCP, 
	OFActionSetVlanPcp.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetVlanPcp();
    	}
    }),
	STRIP_VLAN	(3, org.openflow.protocol.interfaces.OFActionType.STRIP_VLAN, 
	OFActionStripVlan.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionStripVlan();
    	}
    }),
	SET_DL_SRC	(4, org.openflow.protocol.interfaces.OFActionType.SET_DL_SRC, 
	OFActionSetDlSrc.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetDlSrc();
    	}
    }),
	SET_DL_DST	(5, org.openflow.protocol.interfaces.OFActionType.SET_DL_DST, 
	OFActionSetDlDst.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetDlDst();
    	}
    }),
	SET_NW_SRC	(6, org.openflow.protocol.interfaces.OFActionType.SET_NW_SRC, 
	OFActionSetNwSrc.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetNwSrc();
    	}
    }),
	SET_NW_DST	(7, org.openflow.protocol.interfaces.OFActionType.SET_NW_DST, 
	OFActionSetNwDst.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetNwDst();
    	}
    }),
	SET_NW_TOS	(8, org.openflow.protocol.interfaces.OFActionType.SET_NW_TOS, 
	OFActionSetNwTos.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetNwTos();
    	}
    }),
	SET_TP_SRC	(9, org.openflow.protocol.interfaces.OFActionType.SET_TP_SRC, 
	OFActionSetTpSrc.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetTpSrc();
    	}
    }),
	SET_TP_DST	(10, org.openflow.protocol.interfaces.OFActionType.SET_TP_DST, 
	OFActionSetTpDst.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionSetTpDst();
    	}
    }),
	OPAQUE_ENQUEUE	(11, org.openflow.protocol.interfaces.OFActionType.OPAQUE_ENQUEUE, 
	OFActionOpaqueEnqueue.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionOpaqueEnqueue();
    	}
    }),
	VENDOR	(0xffff, org.openflow.protocol.interfaces.OFActionType.VENDOR, 
	OFActionVendor.class, 
	new Instantiable<OFAction>() {
    	public OFAction instantiate() {
      		return new OFActionVendor();
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