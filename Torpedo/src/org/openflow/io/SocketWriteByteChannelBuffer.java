package org.openflow.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferFactory;
import org.jboss.netty.buffer.ChannelBufferIndexFinder;

public class SocketWriteByteChannelBuffer implements ChannelBuffer {
	
	private int capacity;
	private ByteBuffer outBuf;
	
	public SocketWriteByteChannelBuffer(int capacity) {
		this.capacity = capacity;
		this.outBuf = ByteBuffer.allocateDirect(this.capacity);
	}
	
	public int write(SocketChannel sock) throws IOException {
		this.outBuf.flip();					// swap pointers; lim = pos; pos = 0;
		int ret = this.outBuf.remaining();
		do {
			sock.write(this.outBuf);
		} while ( this.outBuf.remaining() > 0 );
		return ret;
	}

	@Override
	public byte[] array() {
		return this.outBuf.array();
	}

	@Override
	public int arrayOffset() {
		return this.outBuf.arrayOffset();
	}

	@Override
	public int bytesBefore(byte v) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int bytesBefore(ChannelBufferIndexFinder indexFinder) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int bytesBefore(int arg0, byte arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int bytesBefore(int arg0, ChannelBufferIndexFinder arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int bytesBefore(int arg0, int arg1, byte arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int bytesBefore(int arg0, int arg1, ChannelBufferIndexFinder arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int capacity() {
		return this.outBuf.capacity();
	}

	@Override
	public void clear() {
		this.outBuf.clear();
	}

	@Override
	public int compareTo(ChannelBuffer arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBuffer copy() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBuffer copy(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void discardReadBytes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBuffer duplicate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void ensureWritableBytes(int writableBytes) {
		if (this.outBuf.remaining() < writableBytes) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public ChannelBufferFactory factory() {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getBytes(int arg0, ChannelBuffer arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getBytes(int arg0, byte[] arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getBytes(int arg0, ByteBuffer arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getBytes(int arg0, ChannelBuffer arg1, int arg2) {
		throw new UnsupportedOperationException();	}

	@Override
	public void getBytes(int arg0, OutputStream arg1, int arg2)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getBytes(int arg0, GatheringByteChannel arg1, int arg2)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getBytes(int arg0, ChannelBuffer arg1, int arg2, int arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getBytes(int arg0, byte[] arg1, int arg2, int arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public char getChar(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMedium(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getUnsignedByte(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getUnsignedInt(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getUnsignedMedium(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getUnsignedShort(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasArray() {
		return true;
	}

	@Override
	public int indexOf(int fromIndex, int toIndex, byte value) {
		int inc = toIndex >= fromIndex ? 1 : -1;
		
		for ( int i = fromIndex ; i != toIndex; i = i + inc) {
			if ( i >= this.outBuf.position() && i < this.outBuf.limit() ) {
				if ( this.outBuf.get(i) == value ) {
					return i;
				}
			}
		}
		
		return -1;
	}

	@Override
	public int indexOf(int arg0, int arg1, ChannelBufferIndexFinder arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDirect() {
		return true;
	}

	@Override
	public void markReaderIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void markWriterIndex() {
		this.outBuf.mark();
	}

	@Override
	public ByteOrder order() {
		return ByteOrder.BIG_ENDIAN;
	}

	@Override
	public byte readByte() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBuffer readBytes(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public ChannelBuffer readBytes(ChannelBufferIndexFinder arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(ChannelBuffer arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(byte[] arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(ByteBuffer arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(ChannelBuffer arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(OutputStream arg0, int arg1) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readBytes(GatheringByteChannel arg0, int arg1)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(ChannelBuffer arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(byte[] arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public char readChar() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double readDouble() {
		throw new UnsupportedOperationException();
	}

	@Override
	public float readFloat() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readInt() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long readLong() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readMedium() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short readShort() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBuffer readSlice(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public ChannelBuffer readSlice(ChannelBufferIndexFinder arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short readUnsignedByte() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long readUnsignedInt() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUnsignedMedium() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUnsignedShort() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean readable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readableBytes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readerIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readerIndex(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetReaderIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetWriterIndex() {
		this.outBuf.reset();
	}

	@Override
	public void setByte(int index, int value) {
		if ( index < 0 || index >= this.outBuf.limit() || index + 1 > this.outBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.put(index, (byte)value);
	}

	@Override
	public void setBytes(int index, ChannelBuffer src) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int index, byte[] src) {
		if ( index < 0 || index + src.length > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		for ( int i = index; i < src.length; ++i ) {
			this.outBuf.put(index, src[i-index]);
		}
	}

	@Override
	public void setBytes(int index, ByteBuffer src) {
		if ( index < 0 || index + src.remaining() > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		while ( src.remaining() > 0 ) {
			this.outBuf.put(index++, src.get());
		}
	}

	@Override
	public void setBytes(int index, ChannelBuffer src, int length) {
		if ( index < 0 || index + src.readableBytes() > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		for ( int i = 0; i < length; ++i ) {
			this.outBuf.put(index+i, src.readByte());
		}
	}

	@Override
	public int setBytes(int index, InputStream src, int length) throws IOException {
		if ( index < 0 || index + src.available() > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		for ( int i = 0; i < length; ++i ) {
			int r = src.read();
			if ( r < 0 ) {
				return i;
			}
			this.outBuf.put(index+i, (byte)r);
		}
		
		return length;
	}

	@Override
	public int setBytes(int index, ScatteringByteChannel src, int length)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int index, ChannelBuffer src, int srcIndex, int length) {
		if ( index < 0 || index + length > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		for ( int i = 0; i < length; ++i ) {
			this.outBuf.put(index+i, (byte)src.getByte(srcIndex+i));
		}
	}

	@Override
	public void setBytes(int index, byte[] src, int srcIndex, int length) {
		if ( index < 0 || index + length > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		for ( int i = 0; i < length; ++i ) {
			this.outBuf.put(index+i, src[srcIndex+i]);
		}
	}

	@Override
	public void setChar(int index, int value) {
		if ( index < 0 || index >= this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putChar(index, (char) value);
	}

	@Override
	public void setDouble(int index, double value) {
		if ( index < 0 || index >= this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putDouble(index, value);
	}

	@Override
	public void setFloat(int index, float value) {
		if ( index < 0 || index >= this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putFloat(index, value);
	}

	@Override
	public void setIndex(int readerIndex, int writerIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setInt(int index, int value) {
		if ( index < 0 || index >= this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putInt(index, value);
	}

	@Override
	public void setLong(int index, long value) {
		if ( index < 0 || index >= this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putLong(index, value);
	}

	@Override
	public void setMedium(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setShort(int index, int value) {
		if ( index < 0 || index >= this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putShort(index, (short) value);
	}

	@Override
	public void setZero(int index, int length) {
		if ( index < 0 || index + length > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}
		
		this.outBuf.put(index, (byte)0x00);
	}

	@Override
	public void skipBytes(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public int skipBytes(ChannelBufferIndexFinder arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBuffer slice() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBuffer slice(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteBuffer toByteBuffer() {
		return this.outBuf;
	}

	@Override
	public ByteBuffer toByteBuffer(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteBuffer[] toByteBuffers() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteBuffer[] toByteBuffers(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString(Charset arg0) {
		return this.outBuf.toString();
	}

	@Override
	@Deprecated
	public String toString(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public String toString(String arg0, ChannelBufferIndexFinder arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString(int arg0, int arg1, Charset arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public String toString(int arg0, int arg1, String arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public String toString(int arg0, int arg1, String arg2,
			ChannelBufferIndexFinder arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean writable() {
		return true;
	}

	@Override
	public int writableBytes() {
		return this.outBuf.remaining();
	}

	@Override
	public void writeByte(int value) {
		if ( this.writableBytes() < 1 ) {
			throw new IndexOutOfBoundsException();
		}
		
		this.outBuf.put((byte)value);
	}

	@Override
	public void writeBytes(ChannelBuffer src) {
		if ( this.writableBytes() < src.readableBytes() ) {
			throw new IndexOutOfBoundsException();
		}

		while ( src.readableBytes() > 0 ) {
			this.outBuf.put( src.readByte() );
		}
	}

	@Override
	public void writeBytes(byte[] src) {
		if ( this.writableBytes() < src.length ) {
			throw new IndexOutOfBoundsException();
		}
		
		this.outBuf.put( src );
	}

	@Override
	public void writeBytes(ByteBuffer src) {
		if ( this.writableBytes() < src.remaining() ) {
			throw new IndexOutOfBoundsException();
		}
		
		this.outBuf.put( src );
	}

	@Override
	public void writeBytes(ChannelBuffer src, int length) {
		if ( this.writableBytes() < length || this.writableBytes() < src.readableBytes() ) {
			throw new IndexOutOfBoundsException();
		}
		
		for ( int i = 0; i < length; ++i ) {
			this.outBuf.put( src.readByte() );
		}
	}

	@Override
	public int writeBytes(InputStream src, int length) throws IOException {
		if ( this.writableBytes() < length ) {
			throw new IndexOutOfBoundsException();
		}
		
		for ( int i = 0; i < length; ++i ) {
			int r = src.read();
			if ( r < 0 ) {
				return i;
			}
			this.outBuf.put( (byte) r );
		}
		return length;
	}

	@Override
	public int writeBytes(ScatteringByteChannel src, int length) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(ChannelBuffer src, int srcIndex, int length) {
		if ( srcIndex < 0 || length > this.writableBytes() || srcIndex + length > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		for ( int i = 0; i < length; ++i ) {
			this.outBuf.put( src.getByte(srcIndex + i) );
		}
	}

	@Override
	public void writeBytes(byte[] src, int srcIndex, int length) {
		if ( srcIndex < 0 || length > this.writableBytes() || srcIndex + length > this.outBuf.limit() ) {
			throw new IndexOutOfBoundsException();
		}

		for ( int i = 0; i < length; ++i ) {
			this.outBuf.put( src[srcIndex + i] );
		}
	}

	@Override
	public void writeChar(int value) {
		if ( this.writableBytes() < 2 ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putChar( (char) value );
	}

	@Override
	public void writeDouble(double value) {
		if ( this.writableBytes() < 8 ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putDouble( value );

	}

	@Override
	public void writeFloat(float value) {
		if ( this.writableBytes() < 4 ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putFloat( value );

	}

	@Override
	public void writeInt(int value) {
		if ( this.writableBytes() < 4 ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putInt( value );
	}

	@Override
	public void writeLong(long value) {
		if ( this.writableBytes() < 8 ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putLong( value );
	}

	@Override
	public void writeMedium(int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeShort(int value) {
		if ( this.writableBytes() < 2 ) {
			throw new IndexOutOfBoundsException();
		}

		this.outBuf.putShort( (short) value );
	}

	@Override
	public void writeZero(int length) {
		for ( int i = 0; i < length; ++i ) {
			this.writeByte((byte)0x00);
		}
	}

	@Override
	public int writerIndex() {
		return this.outBuf.position();
	}

	@Override
	public void writerIndex(int writerIndex) {
		this.outBuf.position(writerIndex);
	}

}
