package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFMatchType {
    STANDARD	(0, OFMatchStandard.class, new Instantiable<OFMatch>() {
    public OFMatch instantiate() {
      return new OFMatchStandard();
    }}),
	OXM	(1, OFMatchOxm.class, new Instantiable<OFMatch>() {
    public OFMatch instantiate() {
      return new OFMatchOxm();
    }});

    static OFMatchType[] mapping;

    protected Class<? extends OFMatch> clazz;
    protected Constructor<? extends OFMatch> constructor;
    protected Instantiable<OFMatch> instantiable;
    protected short type;

    OFMatchType(int type, Class<? extends OFMatch> clazz, Instantiable<OFMatch> instantiator) {
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
    }

    static public void addMapping(short i, OFMatchType t) {
        if (mapping == null)
            mapping = new OFMatchType[2];
        if ( i < 0 ) i = (short)(2 + i);
        OFMatchType.mapping[i] = t;
    }

    static public OFMatchType valueOf(short i) {
        if ( i < 0 ) i = (short)(2 + i);
        return OFMatchType.mapping[i];
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFMatchType first() {
    	return OFMatchType.mapping[0];
    }
    
    static public OFMatchType last() {
    	return OFMatchType.mapping[OFMatchType.mapping.length - 1];
    }
    
    static public void parse(List<OFMatch> output, ByteBuffer data, int length) {
    	OFMatch demux = new OFMatch();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFMatch real = demux.getType().newInstance();
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