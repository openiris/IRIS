package org.openflow.protocol;

import java.nio.ByteBuffer;

import org.openflow.protocol.interfaces.OFMatch;
import org.openflow.protocol.interfaces.OFOxm;
import org.openflow.protocol.interfaces.OFOxmMatchFields;

public abstract class OFMatchUtil {

	public static Integer getInt(OFMatch match, OFOxmMatchFields field) {
		
		byte[] data = getData(match, field, 4);
		
		if (data == null) return null;
		
		return ByteBuffer.wrap(data).getInt();
	}
	
	public static Short getShort(OFMatch match, OFOxmMatchFields field) {
		byte[] data = getData(match, field, 2);
		
		if (data == null) return null;
		
		return ByteBuffer.wrap(data).getShort();
	}
	
	public static Byte getByte(OFMatch match, OFOxmMatchFields field) {
		byte[] data = getData(match, field, 1);
		
		if (data == null) return null;
		
		return data[0];
	}
	
	public static byte[] getByteArray(OFMatch match, OFOxmMatchFields field) {
		byte[] data = getData(match, field, 1);
		return data;
	}
	
	public static Long getEthAsLong(OFMatch match, OFOxmMatchFields field) {
		byte[] data = getData(match, field, 6);
		
		if (data == null) return null;
		
		byte[] tmpArray = new byte[8];
		tmpArray[0] = tmpArray[1] = 0;
		System.arraycopy(data, 0, tmpArray, 2, 6);
		
		return ByteBuffer.wrap(tmpArray).getLong();
	}

	private static byte[] getData(OFMatch match, OFOxmMatchFields field, int len) {
		if ( match == null ) {
			return null;
		}
		if ( !match.isOxmFieldsSupported() ) {
			return null;
		}
		OFOxm oxm = match.getOxmFromIndex(field);
		if ( oxm == null ) return null;
		
		byte[] data = oxm.getData();
		if ( oxm.getBitmask() != (byte)0 ) {
			if (data.length != len * 2) return null;
		} else {
			if (data.length != len) return null;
		}
		return data;
	}
}
