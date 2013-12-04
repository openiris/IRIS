package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFHelloElemType {
    VERSIONBITMAP	(1, OFHelloElemVersionbitmap.class, new Instantiable<OFHelloElem>() {
    public OFHelloElem instantiate() {
      return new OFHelloElemVersionbitmap();
    }});

    static OFHelloElemType[] mapping;

    protected Class<? extends OFHelloElem> clazz;
    protected Constructor<? extends OFHelloElem> constructor;
    protected Instantiable<OFHelloElem> instantiable;
    protected short type;

    OFHelloElemType(int type, Class<? extends OFHelloElem> clazz, Instantiable<OFHelloElem> instantiator) {
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
    }

    static public void addMapping(short i, OFHelloElemType t) {
        if (mapping == null)
            mapping = new OFHelloElemType[1];
        if ( i < 0 ) i = (short)(1 + i);
        OFHelloElemType.mapping[i] = t;
    }

    static public OFHelloElemType valueOf(short i) {
        if ( i < 0 ) i = (short)(1 + i);
        return OFHelloElemType.mapping[i];
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFHelloElemType first() {
    	return OFHelloElemType.mapping[0];
    }
    
    static public OFHelloElemType last() {
    	return OFHelloElemType.mapping[OFHelloElemType.mapping.length - 1];
    }
    
    static public void parse(List<OFHelloElem> output, ByteBuffer data, int length) {
    	OFHelloElem demux = new OFHelloElem();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFHelloElem real = demux.getType().newInstance();
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