package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFMatchType {
    STANDARD	(0, org.openflow.protocol.interfaces.OFMatchType.STANDARD, 
	OFMatchStandard.class, 
	new Instantiable<OFMatch>() {
    	public OFMatch instantiate() {
      		return new OFMatchStandard();
    	}
    }),
	OXM	(1, org.openflow.protocol.interfaces.OFMatchType.OXM, 
	OFMatchOxm.class, 
	new Instantiable<OFMatch>() {
    	public OFMatch instantiate() {
      		return new OFMatchOxm();
    	}
    });

    // static OFMatchType[] mapping;
    static Map<Short, OFMatchType> mapping;
    static Map<Short, org.openflow.protocol.interfaces.OFMatchType> compatMapping;
    static Map<org.openflow.protocol.interfaces.OFMatchType, OFMatchType> compatMappingReverse;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFMatch> clazz;
    protected Constructor<? extends OFMatch> constructor;
    protected Instantiable<OFMatch> instantiable;
    protected short type;

    OFMatchType(
    	int type, org.openflow.protocol.interfaces.OFMatchType compatType,
    	Class<? extends OFMatch> clazz, Instantiable<OFMatch> instantiator) 
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
        OFMatchType.addMapping(this.type, this);
        OFMatchType.addMapping(this.type, compatType, this);
    }

    static public void addMapping(short i, OFMatchType t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFMatchType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping(short i, org.openflow.protocol.interfaces.OFMatchType c, OFMatchType t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFMatchType>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFMatchType, OFMatchType>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public OFMatchType valueOf(short i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFMatchType to(OFMatchType i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFMatchType from(org.openflow.protocol.interfaces.OFMatchType c) {
    	return compatMappingReverse.get(c);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFMatchType first() {
    	return mapping.get(start_key);
    }
    
    static public OFMatchType last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFMatch> output, ByteBuffer data, int length) {
    	OFMatch demux = new OFMatch();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFMatch real = OFMatchType.from(demux.getType()).newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFMatch> to_write, ByteBuffer data) {
    	for ( OFMatch i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFMatch> toClass() {
        return clazz;
    }

    public Constructor<? extends OFMatch> getConstructor() {
        return constructor;
    }

    public OFMatch newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFMatch> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFMatch> instantiable) {
        this.instantiable = instantiable;
    }
}