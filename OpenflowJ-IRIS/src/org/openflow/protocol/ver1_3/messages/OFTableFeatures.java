package org.openflow.protocol.ver1_3.messages;

import java.nio.ByteBuffer;
import org.openflow.util.*;

import java.util.LinkedList;
import java.util.List;
import org.openflow.protocol.ver1_3.types.*;

public class OFTableFeatures   implements org.openflow.protocol.interfaces.OFTableFeatures {
    public static int MINIMUM_LENGTH = 64;
    public static int CORE_LENGTH = 64;

    short  length;
	byte  table_id;
	int pad_1th;
	byte pad_2th;
	byte[]  name;
	long  metadata_match;
	long  metadata_write;
	int  config;
	int  max_entries;
	List<org.openflow.protocol.interfaces.OFTableFeatureProperty>  properties;

    public OFTableFeatures() {
        name = new byte[32];
    }
    
    public OFTableFeatures(OFTableFeatures other) {
    	this.length = other.length;
		this.table_id = other.table_id;
		if (other.name != null) { this.name = java.util.Arrays.copyOf(other.name, other.name.length); }
		this.metadata_match = other.metadata_match;
		this.metadata_write = other.metadata_write;
		this.config = other.config;
		this.max_entries = other.max_entries;
		this.properties = (other.properties == null)? null: new LinkedList<org.openflow.protocol.interfaces.OFTableFeatureProperty>();
		for ( org.openflow.protocol.interfaces.OFTableFeatureProperty i : other.properties ) { this.properties.add( i.dup() ); }
    }

	public short getLength() {
		return this.length;
	}
	
	public OFTableFeatures setLength(short length) {
		this.length = length;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isLengthSupported() {
		return true;
	}
			
	public byte getTableId() {
		return this.table_id;
	}
	
	public OFTableFeatures setTableId(byte table_id) {
		this.table_id = table_id;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isTableIdSupported() {
		return true;
	}
			
	public byte[] getName() {
		return this.name;
	}
	
	public OFTableFeatures setName(byte[] name) {
		this.name = name;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isNameSupported() {
		return true;
	}
			
	public long getMetadataMatch() {
		return this.metadata_match;
	}
	
	public OFTableFeatures setMetadataMatch(long metadata_match) {
		this.metadata_match = metadata_match;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isMetadataMatchSupported() {
		return true;
	}
			
	public long getMetadataWrite() {
		return this.metadata_write;
	}
	
	public OFTableFeatures setMetadataWrite(long metadata_write) {
		this.metadata_write = metadata_write;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isMetadataWriteSupported() {
		return true;
	}
			
	public int getConfig() {
		return this.config;
	}
	
	public OFTableFeatures setConfig(int config) {
		this.config = config;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isConfigSupported() {
		return true;
	}
			
	public int getMaxEntries() {
		return this.max_entries;
	}
	
	public OFTableFeatures setMaxEntries(int max_entries) {
		this.max_entries = max_entries;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isMaxEntriesSupported() {
		return true;
	}
			
	public List<org.openflow.protocol.interfaces.OFTableFeatureProperty> getProperties() {
		return this.properties;
	}
	
	public OFTableFeatures setProperties(List<org.openflow.protocol.interfaces.OFTableFeatureProperty> properties) {
		this.properties = properties;
		return this;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	public boolean isPropertiesSupported() {
		return true;
	}
			
	
	
	
	public OFTableFeatures dup() {
		return new OFTableFeatures(this);
	}
	
    public void readFrom(ByteBuffer data) {
        int mark = data.position();
		this.length = data.getShort();
		this.table_id = data.get();
		this.pad_1th = data.getInt();
		this.pad_2th = data.get();
		if ( this.name == null ) this.name = new byte[32];
		data.get(this.name);
		this.metadata_match = data.getLong();
		this.metadata_write = data.getLong();
		this.config = data.getInt();
		this.max_entries = data.getInt();
		if (this.properties == null) this.properties = new LinkedList<org.openflow.protocol.interfaces.OFTableFeatureProperty>();
		int __cnt = ((int)getLength() - (data.position() - mark));
		while (__cnt > 0) {
		  data.mark();
		  short __t = data.getShort();
		  data.reset();
		  OFTableFeatureProperty t = OFTableFeaturePropertyType.valueOf(__t).newInstance();
		  t.readFrom(data); __cnt -= t.getLength();
		  this.properties.add(t);
		}
    }

    public void writeTo(ByteBuffer data) {
    	
        data.putShort(this.length);
		data.put(this.table_id);
		data.putInt(this.pad_1th);
		data.put(this.pad_2th);
		if ( this.name != null ) { data.put(this.name); }
		data.putLong(this.metadata_match);
		data.putLong(this.metadata_write);
		data.putInt(this.config);
		data.putInt(this.max_entries);
		if (this.properties != null ) for (org.openflow.protocol.interfaces.OFTableFeatureProperty t: this.properties) { t.writeTo(data); }
    }

    public String toString() {
        return  ":OFTableFeatures-"+":length=" + U16.f(length) + 
		":table_id=" + U8.f(table_id) + 
		":name=" + java.util.Arrays.toString(name) + 
		":metadata_match=" + U64.f(metadata_match) + 
		":metadata_write=" + U64.f(metadata_write) + 
		":config=" + U32.f(config) + 
		":max_entries=" + U32.f(max_entries) + 
		":properties=" + properties;
    }

	// compute length (without final alignment)    
    public short computeLength() {
    	short len = (short)MINIMUM_LENGTH;
		if ( this.properties != null ) for ( org.openflow.protocol.interfaces.OFTableFeatureProperty i : this.properties ) { len += i.computeLength(); }
    	return len;
    }
    
    // calculate the amount that will be increased by the alignment requirement.
    public short alignment(int total, int req) {
    	return (short)((total + (req-1))/req*req - total);
    }
    
    // compute the difference with MINIMUM_LENGTH (with alignment)
    public short lengthDiff() {
    	short total = computeLength();
    	return (short)(total - (short)MINIMUM_LENGTH + alignment(total, 0));
    }

    @Override
    public int hashCode() {
        		
		final int prime = 1667;
		int result = super.hashCode() * prime;
		result = prime * result + (int) length;
		result = prime * result + (int) table_id;
		result = prime * result + ((name == null)?0:java.util.Arrays.hashCode(name));
		result = prime * result + (int) metadata_match;
		result = prime * result + (int) metadata_write;
		result = prime * result + (int) config;
		result = prime * result + (int) max_entries;
		result = prime * result + ((properties == null)?0:properties.hashCode());
		return result;
    }

    @Override
    public boolean equals(Object obj) {
        
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OFTableFeatures)) {
            return false;
        }
        OFTableFeatures other = (OFTableFeatures) obj;
		if ( length != other.length ) return false;
		if ( table_id != other.table_id ) return false;
		if ( name == null && other.name != null ) { return false; }
		else if ( !java.util.Arrays.equals(name, other.name) ) { return false; }
		if ( metadata_match != other.metadata_match ) return false;
		if ( metadata_write != other.metadata_write ) return false;
		if ( config != other.config ) return false;
		if ( max_entries != other.max_entries ) return false;
		if ( properties == null && other.properties != null ) { return false; }
		else if ( !properties.equals(other.properties) ) { return false; }
        return true;
    }
}
