package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFInstructionType {
    GOTO_TABLE	(0x1, org.openflow.protocol.interfaces.OFInstructionType.GOTO_TABLE, 
	OFInstructionGotoTable.class, 
	new Instantiable<OFInstruction>() {
    	public OFInstruction instantiate() {
      		return new OFInstructionGotoTable();
    	}
    }),
	WRITE_METADATA	(0x2, org.openflow.protocol.interfaces.OFInstructionType.WRITE_METADATA, 
	OFInstructionWriteMetadata.class, 
	new Instantiable<OFInstruction>() {
    	public OFInstruction instantiate() {
      		return new OFInstructionWriteMetadata();
    	}
    }),
	WRITE_ACTIONS	(0x3, org.openflow.protocol.interfaces.OFInstructionType.WRITE_ACTIONS, 
	OFInstructionWriteActions.class, 
	new Instantiable<OFInstruction>() {
    	public OFInstruction instantiate() {
      		return new OFInstructionWriteActions();
    	}
    }),
	APPLY_ACTIONS	(0x4, org.openflow.protocol.interfaces.OFInstructionType.APPLY_ACTIONS, 
	OFInstructionApplyActions.class, 
	new Instantiable<OFInstruction>() {
    	public OFInstruction instantiate() {
      		return new OFInstructionApplyActions();
    	}
    }),
	CLEAR_ACTIONS	(0x5, org.openflow.protocol.interfaces.OFInstructionType.CLEAR_ACTIONS, 
	OFInstructionClearActions.class, 
	new Instantiable<OFInstruction>() {
    	public OFInstruction instantiate() {
      		return new OFInstructionClearActions();
    	}
    }),
	METER	(0x6, org.openflow.protocol.interfaces.OFInstructionType.METER, 
	OFInstructionMeter.class, 
	new Instantiable<OFInstruction>() {
    	public OFInstruction instantiate() {
      		return new OFInstructionMeter();
    	}
    }),
	EXPERIMENTER	(0xffff, org.openflow.protocol.interfaces.OFInstructionType.EXPERIMENTER, 
	OFInstructionExperimenter.class, 
	new Instantiable<OFInstruction>() {
    	public OFInstruction instantiate() {
      		return new OFInstructionExperimenter();
    	}
    });

    // static OFInstructionType[] mapping;
    static Map<Short, OFInstructionType> mapping;
    static Map<Short, org.openflow.protocol.interfaces.OFInstructionType> compatMapping;
    static Map<org.openflow.protocol.interfaces.OFInstructionType, OFInstructionType> compatMappingReverse;
    static short start_key = 0;
    static short end_key = 0;

    protected Class<? extends OFInstruction> clazz;
    protected Constructor<? extends OFInstruction> constructor;
    protected Instantiable<OFInstruction> instantiable;
    protected short type;

    OFInstructionType(
    	int type, org.openflow.protocol.interfaces.OFInstructionType compatType,
    	Class<? extends OFInstruction> clazz, Instantiable<OFInstruction> instantiator) 
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
        OFInstructionType.addMapping(this.type, this);
        OFInstructionType.addMapping(this.type, compatType, this);
    }

    static public void addMapping(short i, OFInstructionType t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<Short, OFInstructionType>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping(short i, org.openflow.protocol.interfaces.OFInstructionType c, OFInstructionType t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFInstructionType>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFInstructionType, OFInstructionType>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public OFInstructionType valueOf(short i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFInstructionType to(OFInstructionType i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public OFInstructionType from(org.openflow.protocol.interfaces.OFInstructionType c) {
    	return compatMappingReverse.get(c);
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFInstructionType first() {
    	return mapping.get(start_key);
    }
    
    static public OFInstructionType last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<OFInstruction> output, ByteBuffer data, int length) {
    	OFInstruction demux = new OFInstruction();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFInstruction real = OFInstructionType.from(demux.getType()).newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<OFInstruction> to_write, ByteBuffer data) {
    	for ( OFInstruction i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFInstruction> toClass() {
        return clazz;
    }

    public Constructor<? extends OFInstruction> getConstructor() {
        return constructor;
    }

    public OFInstruction newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<OFInstruction> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<OFInstruction> instantiable) {
        this.instantiable = instantiable;
    }
}