package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFHelloElemType {
    VERSIONBITMAP	(1, org.openflow.protocol.interfaces.OFHelloElemType.VERSIONBITMAP, 
	OFHelloElemVersionbitmap.class, 
	new Instantiable<OFHelloElem>() {
    	public OFHelloElem instantiate() {
      		return new OFHelloElemVersionbitmap();
    	}
    });

    // static OFHelloElemType[] mapping;
    static Map<Short, OFHelloElemType> mapping;
    static Map<Short, org.openflow.protocol.interfaces.OFHelloElemType> compatMapping;
    static Map<org.openflow.protocol.interfaces.OFHelloElemType, OFHelloElemType> compatMappingReverse;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFHelloElem> clazz;
    protected Constructor<? extends OFHelloElem> constructor;
    protected Instantiable<OFHelloElem> instantiable;
    protected short type;

    OFHelloElemType(
    	int type, org.openflow.protocol.interfaces.OFHelloElemType compatType,
    	Class<? extends OFHelloElem> clazz, Instantiable<OFHelloElem> instantiator) 
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
        OFHelloElemType.addMapping(this.type, this);
        OFHelloElemType.addMapping(this.type, compatType, this);
    }

    static public void addMapping(short i, OFHelloElemType t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFHelloElemType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping(short i, org.openflow.protocol.interfaces.OFHelloElemType c, OFHelloElemType t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFHelloElemType>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFHelloElemType, OFHelloElemType>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public OFHelloElemType valueOf(short i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFHelloElemType to(OFHelloElemType i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFHelloElemType from(org.openflow.protocol.interfaces.OFHelloElemType c) {
    	return compatMappingReverse.get(c);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFHelloElemType first() {
    	return mapping.get(start_key);
    }
    
    static public OFHelloElemType last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFHelloElem> output, ByteBuffer data, int length) {
    	OFHelloElem demux = new OFHelloElem();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFHelloElem real = OFHelloElemType.from(demux.getType()).newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFHelloElem> to_write, ByteBuffer data) {
    	for ( OFHelloElem i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFHelloElem> toClass() {
        return clazz;
    }

    public Constructor<? extends OFHelloElem> getConstructor() {
        return constructor;
    }

    public OFHelloElem newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFHelloElem> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFHelloElem> instantiable) {
        this.instantiable = instantiable;
    }
}