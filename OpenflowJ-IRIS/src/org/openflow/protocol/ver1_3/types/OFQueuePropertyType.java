package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFQueuePropertyType {
    MIN_RATE	(0x1, OFQueuePropertyMinRate.class, new Instantiable<OFQueueProperty>() {
    public OFQueueProperty instantiate() {
      return new OFQueuePropertyMinRate();
    }}),
	MAX_RATE	(0x2, OFQueuePropertyMaxRate.class, new Instantiable<OFQueueProperty>() {
    public OFQueueProperty instantiate() {
      return new OFQueuePropertyMaxRate();
    }}),
	EXPERIMENTER	(0xffff, OFQueuePropertyExperimenter.class, new Instantiable<OFQueueProperty>() {
    public OFQueueProperty instantiate() {
      return new OFQueuePropertyExperimenter();
    }});

    static OFQueuePropertyType[] mapping;

    protected Class<? extends OFQueueProperty> clazz;
    protected Constructor<? extends OFQueueProperty> constructor;
    protected Instantiable<OFQueueProperty> instantiable;
    protected short type;

    OFQueuePropertyType(int type, Class<? extends OFQueueProperty> clazz, Instantiable<OFQueueProperty> instantiator) {
        this.type = (short) type;
        this.clazz = clazz;
        this.instantiable = instantiator;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        OFQueuePropertyType.addMapping(this.type, this);
    }

    static public void addMapping(short i, OFQueuePropertyType t) {
        if (mapping == null)
            mapping = new OFQueuePropertyType[3];
        if ( i < 0 ) i = (short)(3 + i);
        OFQueuePropertyType.mapping[i] = t;
    }

    static public OFQueuePropertyType valueOf(short i) {
        if ( i < 0 ) i = (short)(3 + i);
        return OFQueuePropertyType.mapping[i];
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFQueuePropertyType first() {
    	return OFQueuePropertyType.mapping[0];
    }
    
    static public OFQueuePropertyType last() {
    	return OFQueuePropertyType.mapping[OFQueuePropertyType.mapping.length - 1];
    }
    
    static public void parse(List<OFQueueProperty> output, ByteBuffer data, int length) {
    	OFQueueProperty demux = new OFQueueProperty();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFQueueProperty real = demux.getType().newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFQueueProperty> to_write, ByteBuffer data) {
    	for ( OFQueueProperty i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFQueueProperty> toClass() {
        return clazz;
    }

    public Constructor<? extends OFQueueProperty> getConstructor() {
        return constructor;
    }

    public OFQueueProperty newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFQueueProperty> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFQueueProperty> instantiable) {
        this.instantiable = instantiable;
    }
}