package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_0.messages.*;

public enum OFActionType {
    OUTPUT	(0, OFActionOutput.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionOutput();
    }}),
	SET_VLAN_ID	(1, OFActionSetVlanId.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetVlanId();
    }}),
	SET_VLAN_PCP	(2, OFActionSetVlanPcp.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetVlanPcp();
    }}),
	STRIP_VLAN	(3, OFActionStripVlan.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionStripVlan();
    }}),
	SET_DL_SRC	(4, OFActionSetDlSrc.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetDlSrc();
    }}),
	SET_DL_DST	(5, OFActionSetDlDst.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetDlDst();
    }}),
	SET_NW_SRC	(6, OFActionSetNwSrc.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetNwSrc();
    }}),
	SET_NW_DST	(7, OFActionSetNwDst.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetNwDst();
    }}),
	SET_NW_TOS	(8, OFActionSetNwTos.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetNwTos();
    }}),
	SET_TP_SRC	(9, OFActionSetTpSrc.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetTpSrc();
    }}),
	SET_TP_DST	(10, OFActionSetTpDst.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetTpDst();
    }}),
	OPAQUE_ENQUEUE	(11, OFActionOpaqueEnqueue.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionOpaqueEnqueue();
    }}),
	VENDOR	(0xffff, OFActionVendor.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionVendor();
    }});

    // static OFActionType[] mapping;
    static Map<Short, OFActionType> mapping;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFAction> clazz;
    protected Constructor<? extends OFAction> constructor;
    protected Instantiable<OFAction> instantiable;
    protected short type;

    OFActionType(int type, Class<? extends OFAction> clazz, Instantiable<OFAction> instantiator) {
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
    }

    static public void addMapping(short i, OFActionType t) {
    	/*
        if (mapping == null)
            mapping = new OFActionType[13];
        if ( i < 0 ) i = (short)(13 + i);
        OFActionType.mapping[i] = t;
        */
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFActionType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }

    static public OFActionType valueOf(short i) {
    	/*
        if ( i < 0 ) i = (short)(13 + i);
        return OFActionType.mapping[i];
        */
        return mapping.get(i);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFActionType first() {
    	// return OFActionType.mapping[0];
    	return mapping.get(start_key);
    }
    
    static public OFActionType last() {
    	// return OFActionType.mapping[OFActionType.mapping.length - 1];
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFAction> output, ByteBuffer data, int length) {
    	OFAction demux = new OFAction();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFAction real = demux.getType().newInstance();
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