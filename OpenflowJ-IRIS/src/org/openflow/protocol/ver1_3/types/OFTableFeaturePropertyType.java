package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFTableFeaturePropertyType {
    INSTRUCTIONS	(0, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.INSTRUCTIONS, 
	OFTableFeaturePropertyInstructions.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyInstructions();
    	}
    }),
	INSTRUCTIONS_MISS	(1, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.INSTRUCTIONS_MISS, 
	OFTableFeaturePropertyInstructionsMiss.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyInstructionsMiss();
    	}
    }),
	NEXT_TABLES	(2, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.NEXT_TABLES, 
	OFTableFeaturePropertyNextTables.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyNextTables();
    	}
    }),
	NEXT_TABLES_MISS	(3, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.NEXT_TABLES_MISS, 
	OFTableFeaturePropertyNextTablesMiss.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyNextTablesMiss();
    	}
    }),
	WRITE_ACTIONS	(4, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.WRITE_ACTIONS, 
	OFTableFeaturePropertyWriteActions.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyWriteActions();
    	}
    }),
	WRITE_ACTIONS_MISS	(5, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.WRITE_ACTIONS_MISS, 
	OFTableFeaturePropertyWriteActionsMiss.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyWriteActionsMiss();
    	}
    }),
	APPLY_ACTIONS	(6, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.APPLY_ACTIONS, 
	OFTableFeaturePropertyApplyActions.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyApplyActions();
    	}
    }),
	APPLY_ACTIONS_MISS	(7, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.APPLY_ACTIONS_MISS, 
	OFTableFeaturePropertyApplyActionsMiss.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyApplyActionsMiss();
    	}
    }),
	MATCH	(8, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.MATCH, 
	OFTableFeaturePropertyMatch.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyMatch();
    	}
    }),
	WILDCARDS	(0xa, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.WILDCARDS, 
	OFTableFeaturePropertyWildcards.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyWildcards();
    	}
    }),
	WRITE_SETFIELD	(0xc, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.WRITE_SETFIELD, 
	OFTableFeaturePropertyWriteSetfield.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyWriteSetfield();
    	}
    }),
	WRITE_SETFIELD_MISS	(0xd, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.WRITE_SETFIELD_MISS, 
	OFTableFeaturePropertyWriteSetfieldMiss.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyWriteSetfieldMiss();
    	}
    }),
	APPLY_SETFIELD	(0xe, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.APPLY_SETFIELD, 
	OFTableFeaturePropertyApplySetfield.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyApplySetfield();
    	}
    }),
	APPLY_SETFIELD_MISS	(0xf, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.APPLY_SETFIELD_MISS, 
	OFTableFeaturePropertyApplySetfieldMiss.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyApplySetfieldMiss();
    	}
    }),
	EXPERIMENTER	(0xfffe, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.EXPERIMENTER, 
	OFTableFeaturePropertyExperimenter.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyExperimenter();
    	}
    }),
	EXPERIMENTER_MISS	(0xffff, org.openflow.protocol.interfaces.OFTableFeaturePropertyType.EXPERIMENTER_MISS, 
	OFTableFeaturePropertyExperimenterMiss.class, 
	new Instantiable<OFTableFeatureProperty>() {
    	public OFTableFeatureProperty instantiate() {
      		return new OFTableFeaturePropertyExperimenterMiss();
    	}
    });

    // static OFTableFeaturePropertyType[] mapping;
    static Map<Short, OFTableFeaturePropertyType> mapping;
    static Map<Short, org.openflow.protocol.interfaces.OFTableFeaturePropertyType> compatMapping;
    static Map<org.openflow.protocol.interfaces.OFTableFeaturePropertyType, OFTableFeaturePropertyType> compatMappingReverse;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFTableFeatureProperty> clazz;
    protected Constructor<? extends OFTableFeatureProperty> constructor;
    protected Instantiable<OFTableFeatureProperty> instantiable;
    protected short type;

    OFTableFeaturePropertyType(
    	int type, org.openflow.protocol.interfaces.OFTableFeaturePropertyType compatType,
    	Class<? extends OFTableFeatureProperty> clazz, Instantiable<OFTableFeatureProperty> instantiator) 
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
        OFTableFeaturePropertyType.addMapping(this.type, this);
        OFTableFeaturePropertyType.addMapping(this.type, compatType, this);
    }

    static public void addMapping(short i, OFTableFeaturePropertyType t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFTableFeaturePropertyType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping(short i, org.openflow.protocol.interfaces.OFTableFeaturePropertyType c, OFTableFeaturePropertyType t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFTableFeaturePropertyType>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFTableFeaturePropertyType, OFTableFeaturePropertyType>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public OFTableFeaturePropertyType valueOf(short i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFTableFeaturePropertyType to(OFTableFeaturePropertyType i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFTableFeaturePropertyType from(org.openflow.protocol.interfaces.OFTableFeaturePropertyType c) {
    	return compatMappingReverse.get(c);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFTableFeaturePropertyType first() {
    	return mapping.get(start_key);
    }
    
    static public OFTableFeaturePropertyType last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFTableFeatureProperty> output, ByteBuffer data, int length) {
    	OFTableFeatureProperty demux = new OFTableFeatureProperty();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFTableFeatureProperty real = OFTableFeaturePropertyType.from(demux.getType()).newInstance();
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