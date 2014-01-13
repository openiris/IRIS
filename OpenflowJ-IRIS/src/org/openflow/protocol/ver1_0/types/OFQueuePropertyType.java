package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_0.messages.*;

public enum OFQueuePropertyType {
    NONE	(0, org.openflow.protocol.interfaces.OFQueuePropertyType.NONE, 
	OFQueuePropertyNone.class, 
	new Instantiable<OFQueueProperty>() {
    	public OFQueueProperty instantiate() {
      		return new OFQueuePropertyNone();
    	}
    }),
	MIN_RATE	(1, org.openflow.protocol.interfaces.OFQueuePropertyType.MIN_RATE, 
	OFQueuePropertyMinRate.class, 
	new Instantiable<OFQueueProperty>() {
    	public OFQueueProperty instantiate() {
      		return new OFQueuePropertyMinRate();
    	}
    });

    // static OFQueuePropertyType[] mapping;
    static Map<Integer, OFQueuePropertyType> mapping;
    static Map<Integer, org.openflow.protocol.interfaces.OFQueuePropertyType> compatMapping;
    static Map<org.openflow.protocol.interfaces.OFQueuePropertyType, OFQueuePropertyType> compatMappingReverse;
    static int start_key = 0;
    static int end_key = 0;

    protected Class<? extends OFQueueProperty> clazz;
    protected Constructor<? extends OFQueueProperty> constructor;
    protected Instantiable<OFQueueProperty> instantiable;
    protected int type;

    OFQueuePropertyType(
    	int type, org.openflow.protocol.interfaces.OFQueuePropertyType compatType,
    	Class<? extends OFQueueProperty> clazz, Instantiable<OFQueueProperty> instantiator) 
    {
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
        OFQueuePropertyType.addMapping(this.type, compatType, this);
    }

    static public void addMapping(int i, OFQueuePropertyType t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Integer, OFQueuePropertyType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping(int i, org.openflow.protocol.interfaces.OFQueuePropertyType c, OFQueuePropertyType t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<Integer, org.openflow.protocol.interfaces.OFQueuePropertyType>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFQueuePropertyType, OFQueuePropertyType>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public OFQueuePropertyType valueOf(int i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFQueuePropertyType to(OFQueuePropertyType i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFQueuePropertyType from(org.openflow.protocol.interfaces.OFQueuePropertyType c) {
    	return compatMappingReverse.get(c);
    }
    
	static public int readFrom(ByteBuffer data) {
  		return data.getInt();
	}
    
    static public OFQueuePropertyType first() {
    	return mapping.get(start_key);
    }
    
    static public OFQueuePropertyType last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFQueueProperty> output, ByteBuffer data, int length) {
    	OFQueueProperty demux = new OFQueueProperty();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFQueueProperty real = OFQueuePropertyType.from(demux.getType()).newInstance();
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