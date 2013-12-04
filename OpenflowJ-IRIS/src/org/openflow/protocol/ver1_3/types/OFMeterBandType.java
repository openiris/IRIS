package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFMeterBandType {
    DROP	(0x1, OFMeterBandDrop.class, new Instantiable<OFMeterBand>() {
    public OFMeterBand instantiate() {
      return new OFMeterBandDrop();
    }}),
	DSCP_REMARK	(0x2, OFMeterBandDscpRemark.class, new Instantiable<OFMeterBand>() {
    public OFMeterBand instantiate() {
      return new OFMeterBandDscpRemark();
    }}),
	EXPERIMENTER	(0xffff, OFMeterBandExperimenter.class, new Instantiable<OFMeterBand>() {
    public OFMeterBand instantiate() {
      return new OFMeterBandExperimenter();
    }});

    // static OFMeterBandType[] mapping;
    static Map<Short, OFMeterBandType> mapping;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFMeterBand> clazz;
    protected Constructor<? extends OFMeterBand> constructor;
    protected Instantiable<OFMeterBand> instantiable;
    protected short type;

    OFMeterBandType(int type, Class<? extends OFMeterBand> clazz, Instantiable<OFMeterBand> instantiator) {
        this.type = (short) type;
        this.clazz = clazz;
        this.instantiable = instantiator;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        OFMeterBandType.addMapping(this.type, this);
    }

    static public void addMapping(short i, OFMeterBandType t) {
    	/*
        if (mapping == null)
            mapping = new OFMeterBandType[3];
        if ( i < 0 ) i = (short)(3 + i);
        OFMeterBandType.mapping[i] = t;
        */
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFMeterBandType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }

    static public OFMeterBandType valueOf(short i) {
    	/*
        if ( i < 0 ) i = (short)(3 + i);
        return OFMeterBandType.mapping[i];
        */
        return mapping.get(i);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFMeterBandType first() {
    	// return OFMeterBandType.mapping[0];
    	return mapping.get(start_key);
    }
    
    static public OFMeterBandType last() {
    	// return OFMeterBandType.mapping[OFMeterBandType.mapping.length - 1];
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFMeterBand> output, ByteBuffer data, int length) {
    	OFMeterBand demux = new OFMeterBand();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFMeterBand real = demux.getType().newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFMeterBand> to_write, ByteBuffer data) {
    	for ( OFMeterBand i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFMeterBand> toClass() {
        return clazz;
    }

    public Constructor<? extends OFMeterBand> getConstructor() {
        return constructor;
    }

    public OFMeterBand newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFMeterBand> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFMeterBand> instantiable) {
        this.instantiable = instantiable;
    }
}