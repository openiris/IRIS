package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFTableFeaturePropertyType {
    INSTRUCTIONS	(0, OFTableFeaturePropertyInstructions.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyInstructions();
    }}),
	INSTRUCTIONS_MISS	(1, OFTableFeaturePropertyInstructionsMiss.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyInstructionsMiss();
    }}),
	NEXT_TABLES	(2, OFTableFeaturePropertyNextTables.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyNextTables();
    }}),
	NEXT_TABLES_MISS	(3, OFTableFeaturePropertyNextTablesMiss.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyNextTablesMiss();
    }}),
	WRITE_ACTIONS	(4, OFTableFeaturePropertyWriteActions.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyWriteActions();
    }}),
	WRITE_ACTIONS_MISS	(5, OFTableFeaturePropertyWriteActionsMiss.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyWriteActionsMiss();
    }}),
	APPLY_ACTIONS	(6, OFTableFeaturePropertyApplyActions.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyApplyActions();
    }}),
	APPLY_ACTIONS_MISS	(7, OFTableFeaturePropertyApplyActionsMiss.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyApplyActionsMiss();
    }}),
	MATCH	(8, OFTableFeaturePropertyMatch.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyMatch();
    }}),
	WILDCARDS	(0xa, OFTableFeaturePropertyWildcards.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyWildcards();
    }}),
	WRITE_SETFIELD	(0xc, OFTableFeaturePropertyWriteSetfield.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyWriteSetfield();
    }}),
	WRITE_SETFIELD_MISS	(0xd, OFTableFeaturePropertyWriteSetfieldMiss.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyWriteSetfieldMiss();
    }}),
	APPLY_SETFIELD	(0xe, OFTableFeaturePropertyApplySetfield.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyApplySetfield();
    }}),
	APPLY_SETFIELD_MISS	(0xf, OFTableFeaturePropertyApplySetfieldMiss.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyApplySetfieldMiss();
    }}),
	EXPERIMENTER	(0xfffe, OFTableFeaturePropertyExperimenter.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyExperimenter();
    }}),
	EXPERIMENTER_MISS	(0xffff, OFTableFeaturePropertyExperimenterMiss.class, new Instantiable<OFTableFeatureProperty>() {
    public OFTableFeatureProperty instantiate() {
      return new OFTableFeaturePropertyExperimenterMiss();
    }});

    // static OFTableFeaturePropertyType[] mapping;
    static Map<Short, OFTableFeaturePropertyType> mapping;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFTableFeatureProperty> clazz;
    protected Constructor<? extends OFTableFeatureProperty> constructor;
    protected Instantiable<OFTableFeatureProperty> instantiable;
    protected short type;

    OFTableFeaturePropertyType(int type, Class<? extends OFTableFeatureProperty> clazz, Instantiable<OFTableFeatureProperty> instantiator) {
        this.type = (short) type;
        this.clazz = clazz;
        this.instantiable = instantiator;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        OFTableFeaturePropertyType.addMapping(this.type, this);
    }

    static public void addMapping(short i, OFTableFeaturePropertyType t) {
    	/*
        if (mapping == null)
            mapping = new OFTableFeaturePropertyType[16];
        if ( i < 0 ) i = (short)(16 + i);
        OFTableFeaturePropertyType.mapping[i] = t;
        */
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFTableFeaturePropertyType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }

    static public OFTableFeaturePropertyType valueOf(short i) {
    	/*
        if ( i < 0 ) i = (short)(16 + i);
        return OFTableFeaturePropertyType.mapping[i];
        */
        return mapping.get(i);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFTableFeaturePropertyType first() {
    	// return OFTableFeaturePropertyType.mapping[0];
    	return mapping.get(start_key);
    }
    
    static public OFTableFeaturePropertyType last() {
    	// return OFTableFeaturePropertyType.mapping[OFTableFeaturePropertyType.mapping.length - 1];
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFTableFeatureProperty> output, ByteBuffer data, int length) {
    	OFTableFeatureProperty demux = new OFTableFeatureProperty();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFTableFeatureProperty real = demux.getType().newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFTableFeatureProperty> to_write, ByteBuffer data) {
    	for ( OFTableFeatureProperty i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFTableFeatureProperty> toClass() {
        return clazz;
    }

    public Constructor<? extends OFTableFeatureProperty> getConstructor() {
        return constructor;
    }

    public OFTableFeatureProperty newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFTableFeatureProperty> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFTableFeatureProperty> instantiable) {
        this.instantiable = instantiable;
    }
}