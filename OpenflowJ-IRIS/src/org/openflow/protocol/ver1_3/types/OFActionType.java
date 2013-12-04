package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFActionType {
    OUTPUT	(0, OFActionOutput.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionOutput();
    }}),
	COPY_TTL_OUT	(0xb, OFActionCopyTtlOut.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionCopyTtlOut();
    }}),
	COPY_TTL_IN	(0xc, OFActionCopyTtlIn.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionCopyTtlIn();
    }}),
	SET_MPLS_TTL	(0xf, OFActionSetMplsTtl.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetMplsTtl();
    }}),
	DEC_MPLS_TTL	(0x10, OFActionDecMplsTtl.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionDecMplsTtl();
    }}),
	PUSH_VLAN	(0x11, OFActionPushVlan.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionPushVlan();
    }}),
	POP_VLAN	(0x12, OFActionPopVlan.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionPopVlan();
    }}),
	PUSH_MPLS	(0x13, OFActionPushMpls.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionPushMpls();
    }}),
	POP_MPLS	(0x14, OFActionPopMpls.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionPopMpls();
    }}),
	SET_QUEUE	(0x15, OFActionSetQueue.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetQueue();
    }}),
	GROUP	(0x16, OFActionGroup.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionGroup();
    }}),
	SET_NW_TTL	(0x17, OFActionSetNwTtl.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetNwTtl();
    }}),
	DEC_NW_TTL	(0x18, OFActionDecNwTtl.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionDecNwTtl();
    }}),
	SET_FIELD	(0x19, OFActionSetField.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionSetField();
    }}),
	PUSH_PBB	(0x1a, OFActionPushPbb.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionPushPbb();
    }}),
	POP_PBB	(0x1b, OFActionPopPbb.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionPopPbb();
    }}),
	EXPERIMENTER	(0xffff, OFActionExperimenter.class, new Instantiable<OFAction>() {
    public OFAction instantiate() {
      return new OFActionExperimenter();
    }});

    static OFActionType[] mapping;

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
        if (mapping == null)
            mapping = new OFActionType[17];
        if ( i < 0 ) i = (short)(17 + i);
        OFActionType.mapping[i] = t;
    }

    static public OFActionType valueOf(short i) {
        if ( i < 0 ) i = (short)(17 + i);
        return OFActionType.mapping[i];
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFActionType first() {
    	return OFActionType.mapping[0];
    }
    
    static public OFActionType last() {
    	return OFActionType.mapping[OFActionType.mapping.length - 1];
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