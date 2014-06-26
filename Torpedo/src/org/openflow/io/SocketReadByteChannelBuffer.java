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

/**
 * This is a ChannelBuffer that is implemented on top of a ByteBuffer.
 * This buffer is only for two purpose:
 * 
 * 1. feed socket data to buffer
 * 2. do some read on the buffer. 
 * 
 * @author Byungjoon Lee (bjlee@etri.re.kr)
 *
 */
public final class SocketReadByteChannelBuffer implements ChannelBuffer {
	
	private int capacity;
	private ByteBuffer inBuf;
	
	public SocketReadByteChannelBuffer(int bufferSize) {
		this.capacity = bufferSize;
		this.inBuf = ByteBuffer.allocateDirect(this.capacity);
	}
	
	/**
	 * read from socket, and pack the data into self. 
	 * 
	 * @param socket		socket to read
	 * @return				how many bytes are read?
	 * @throws IOException	is thrown if read is impossible
	 */
	public int read(SocketChannel socket) throws IOException {
		int ret = socket.read(this.inBuf);
		if ( ret >= 0 ) {
			// ready for the subsequent read operations from this buffer.
			this.inBuf.flip();
		}
		return ret;
	}
	
	@Override
	public byte[] array() {
		return this.inBuf.array();
	}

	@Override
	public int arrayOffset() {
		return this.inBuf.arrayOffset();
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
		return this.inBuf.capacity();
	}

	@Override
	public void clear() {
		this.inBuf.clear();
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
		this.inBuf.compact();
	}

	@Override
	public ChannelBuffer duplicate() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void ensureWritableBytes(int writableBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChannelBufferFactory factory() {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int index) {
		if ( index < 0 || index + 1 > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.inBuf.get(index);
	}

	@Override
	public void getBytes(int index, ChannelBuffer dst) {
		if ( index < 0 || index + dst.writableBytes() > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		while ( dst.writable() && index < this.inBuf.limit() ) {
			dst.writeByte( this.inBuf.get(index++) );
		}
	}

	@Override
	public void getBytes(int index, byte[] dst) {
		getBytes(index, dst, 0, dst.length);
	}

	@Override
	public void getBytes(int index, ByteBuffer dst) {
		if ( index < 0 || index + dst.remaining() > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		while ( dst.position() < dst.limit() && index < this.inBuf.limit() ) {
			dst.put( this.inBuf.get(index++) );
		}
	}

	@Override
	public void getBytes(int index, ChannelBuffer dst, int length) {
		if ( index < 0 || index + length > this.inBuf.capacity() || length > dst.writableBytes() ) {
			throw new IndexOutOfBoundsException();
		}

		while ( dst.writable() && index < this.inBuf.position() + length && index < this.inBuf.limit() ) {
			dst.writeByte( this.inBuf.get(index++) );
		}
	}

	@Override
	public void getBytes(int index, OutputStream dst, int length) throws IOException {
		if ( index < 0 || index + length > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		while ( index < this.inBuf.position() + length && index < this.inBuf.limit() ) {
			dst.write( this.inBuf.get(index++) );
		}
	}

	@Override
	public int getBytes(int index, GatheringByteChannel dst, int length) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void getBytes(int index, ChannelBuffer dst, int dstIndex, int length) {
		int backup = dst.writerIndex(); 
		dst.writerIndex( dstIndex );
		
		getBytes(index, dst, length);

		dst.writerIndex( backup );
	}

	@Override
	public void getBytes(int index, byte[] dst, int dstIndex, int length) {
		if ( index < 0 || dstIndex < 0 || index + length > this.inBuf.capacity() || dstIndex + length > dst.length ) {
			throw new IndexOutOfBoundsException();
		}
		
		int current = 0;
		while ( current < length && current < dst.length && index < this.inBuf.limit() ) {
			dst[dstIndex + current++] = this.inBuf.get(index);
		}
	}

	@Override
	public char getChar(int index) {
		if ( index < 0 || index + 2 > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.inBuf.getChar(index);
	}

	@Override
	public double getDouble(int index) {
		if ( index < 0 || index + 8 > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.inBuf.getDouble(index);
	}

	@Override
	public float getFloat(int index) {
		if ( index < 0 || index + 4 > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.inBuf.getFloat(index);
	}

	@Override
	public int getInt(int index) {
		if ( index < 0 || index + 4 > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.inBuf.getInt(index);
	}

	@Override
	public long getLong(int index) {
		if ( index < 0 || index + 8 > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.inBuf.getLong(index);
	}

	@Override
	public int getMedium(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int index) {
		if ( index < 0 || index + 2 > this.inBuf.capacity() ) {
			throw new IndexOutOfBoundsException();
		}
		
		return this.inBuf.getShort(index);
	}

	@Override
	public short getUnsignedByte(int index) {
		return (short) (0x00ff & getByte(index));
	}

	@Override
	public long getUnsignedInt(int index) {
		return 0x00000000ffffffff & getInt(index);
	}

	@Override
	public int getUnsignedMedium(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getUnsignedShort(int index) {
		return 0x0000ffff & getShort(index);
	}

	@Override
	public boolean hasArray() {
		return true;
	}

	@Override
	public int indexOf(int fromIndex, int toIndex, byte value) {
		int inc = toIndex >= fromIndex ? 1 : -1;
		
		for ( int i = fromIndex ; i != toIndex; i = i + inc) {
			if ( i >= this.inBuf.position() && i < this.inBuf.limit() ) {
				if ( this.inBuf.get(i) == value ) {
					return i;
				}
			}
		}
		
		return -1;
	}

	@Override
	public int indexOf(int fromIndex, int toIndex, ChannelBufferIndexFinder indexFinder) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDirect() {
		return true;
	}

	@Override
	public void markReaderIndex() {
		this.inBuf.mark();
	}

	@Override
	public void markWriterIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ByteOrder order() {
		return ByteOrder.BIG_ENDIAN;
	}

	@Override
	public byte readByte() {
		return this.inBuf.get();
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
	public void readBytes(ChannelBuffer dst) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(byte[] dst) {
		this.inBuf.get(dst);
	}

	@Override
	public void readBytes(ByteBuffer dst) {
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
	public int readBytes(GatheringByteChannel arg0, int arg1) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(ChannelBuffer arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readBytes(byte[] dst, int dstIndex, int length) {
		this.inBuf.get(dst, dstIndex, length);
	}

	@Override
	public char readChar() {
		return this.inBuf.getChar();
	}

	@Override
	public double readDouble() {
		return this.inBuf.getDouble();
	}

	@Override
	public float readFloat() {
		return this.inBuf.getFloat();
	}

	@Override
	public int readInt() {
		return this.inBuf.getInt();
	}

	@Override
	public long readLong() {
		return this.inBuf.getLong();
	}

	@Override
	public int readMedium() {
		throw new UnsupportedOperationException();
	}

	@Override
	public short readShort() {
		return this.inBuf.getShort();
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
		return (short) (0x00ff & readByte());
	}

	@Override
	public long readUnsignedInt() {
		return 0x00000000ffffffff & readInt();
	}

	@Override
	public int readUnsignedMedium() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUnsignedShort() {
		return 0x0000ffff & readShort();
	}

	@Override
	public boolean readable() {
		return this.inBuf.hasRemaining();
	}

	@Override
	public int readableBytes() {
		return this.inBuf.remaining();
	}

	@Override
	public int readerIndex() {
		return this.inBuf.position();
	}

	@Override
	public void readerIndex(int index) {
		this.inBuf.position(index);
	}

	@Override
	public void resetReaderIndex() {
		this.inBuf.reset();
	}

	@Override
	public void resetWriterIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setByte(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int arg0, ChannelBuffer arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int arg0, byte[] arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int arg0, ByteBuffer arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int arg0, ChannelBuffer arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int setBytes(int arg0, InputStream arg1, int arg2)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int setBytes(int arg0, ScatteringByteChannel arg1, int arg2)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int arg0, ChannelBuffer arg1, int arg2, int arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBytes(int arg0, byte[] arg1, int arg2, int arg3) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setChar(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDouble(int arg0, double arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFloat(int arg0, float arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setIndex(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setInt(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLong(int arg0, long arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMedium(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setShort(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setZero(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void skipBytes(int length) {
		this.inBuf.position( this.inBuf.position() + length );
	}

	@Override
	@Deprecated
	public int skipBytes(ChannelBufferIndexFinder arg0) {
		return 0;
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
		return this.inBuf;
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
		return this.inBuf.toString();
	}

	@Override
	@Deprecated
	public String toString(String arg0) {
		return null;
	}

	@Override
	@Deprecated
	public String toString(String arg0, ChannelBufferIndexFinder arg1) {
		return null;
	}

	@Override
	public String toString(int index, int length, Charset charset) {
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
		return false;
	}

	@Override
	public int writableBytes() {
		return 0;
	}

	@Override
	public void writeByte(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(ChannelBuffer arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(byte[] arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(ByteBuffer arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(ChannelBuffer arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int writeBytes(InputStream arg0, int arg1) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int writeBytes(ScatteringByteChannel arg0, int arg1)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(ChannelBuffer arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(byte[] arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeChar(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeDouble(double arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeFloat(float arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeInt(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeLong(long arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeMedium(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeShort(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeZero(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int writerIndex() {
		return 0;
	}

	@Override
	public void writerIndex(int arg0) {
		throw new UnsupportedOperationException();
	}

}
