package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.List;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFInstructionType {
    GOTO_TABLE	(0x1, OFInstructionGotoTable.class, new Instantiable<OFInstruction>() {
    public OFInstruction instantiate() {
      return new OFInstructionGotoTable();
    }}),
	WRITE_METADATA	(0x2, OFInstructionWriteMetadata.class, new Instantiable<OFInstruction>() {
    public OFInstruction instantiate() {
      return new OFInstructionWriteMetadata();
    }}),
	WRITE_ACTIONS	(0x3, OFInstructionWriteActions.class, new Instantiable<OFInstruction>() {
    public OFInstruction instantiate() {
      return new OFInstructionWriteActions();
    }}),
	APPLY_ACTIONS	(0x4, OFInstructionApplyActions.class, new Instantiable<OFInstruction>() {
    public OFInstruction instantiate() {
      return new OFInstructionApplyActions();
    }}),
	CLEAR_ACTIONS	(0x5, OFInstructionClearActions.class, new Instantiable<OFInstruction>() {
    public OFInstruction instantiate() {
      return new OFInstructionClearActions();
    }}),
	METER	(0x6, OFInstructionMeter.class, new Instantiable<OFInstruction>() {
    public OFInstruction instantiate() {
      return new OFInstructionMeter();
    }}),
	EXPERIMENTER	(0xffff, OFInstructionExperimenter.class, new Instantiable<OFInstruction>() {
    public OFInstruction instantiate() {
      return new OFInstructionExperimenter();
    }});

    static OFInstructionType[] mapping;

    protected Class<? extends OFInstruction> clazz;
    protected Constructor<? extends OFInstruction> constructor;
    protected Instantiable<OFInstruction> instantiable;
    protected short type;

    OFInstructionType(int type, Class<? extends OFInstruction> clazz, Instantiable<OFInstruction> instantiator) {
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
    }

    static public void addMapping(short i, OFInstructionType t) {
        if (mapping == null)
            mapping = new OFInstructionType[7];
        if ( i < 0 ) i = (short)(7 + i);
        OFInstructionType.mapping[i] = t;
    }

    static public OFInstructionType valueOf(short i) {
        if ( i < 0 ) i = (short)(7 + i);
        return OFInstructionType.mapping[i];
    }
    
	static public short readFrom(ByteBuffer data) {
  		return data.getShort();
	}
    
    static public OFInstructionType first() {
    	return OFInstructionType.mapping[0];
    }
    
    static public OFInstructionType last() {
    	return OFInstructionType.mapping[OFInstructionType.mapping.length - 1];
    }
    
    static public void parse(List<OFInstruction> output, ByteBuffer data, int length) {
    	OFInstruction demux = new OFInstruction();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		OFInstruction real = demux.getType().newInstance();
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