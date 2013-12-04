package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.List;

import org.openflow.protocol.ver1_0.messages.*;

public enum OFQueuePropertyType {
    NONE	(0, OFQueuePropertyNone.class, new Instantiable<OFQueueProperty>() {
    public OFQueueProperty instantiate() {
      return new OFQueuePropertyNone();
    }}),
	MIN_RATE	(1, OFQueuePropertyMinRate.class, new Instantiable<OFQueueProperty>() {
    public OFQueueProperty instantiate() {
      return new OFQueuePropertyMinRate();
    }});

    static OFQueuePropertyType[] mapping;

    protected Class<? extends OFQueueProperty> clazz;
    protected Constructor<? extends OFQueueProperty> constructor;
    protected Instantiable<OFQueueProperty> instantiable;
    protected int type;

    OFQueuePropertyType(int type, Class<? extends OFQueueProperty> clazz, Instantiable<OFQueueProperty> instantiator) {
        this.type = (int) type;
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

    static public void addMapping(int i, OFQueuePropertyType t) {
        if (mapping == null)
            mapping = new OFQueuePropertyType[2];
        if ( i < 0 ) i = (int)(2 + i);
        OFQueuePropertyType.mapping[i] = t;
    }

    static public OFQueuePropertyType valueOf(int i) {
        if ( i < 0 ) i = (int)(2 + i);
        return OFQueuePropertyType.mapping[i];
    }
    
	static public int readFrom(ByteBuffer data) {
  		return data.getInt();
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

    public int getTypeValue() {
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