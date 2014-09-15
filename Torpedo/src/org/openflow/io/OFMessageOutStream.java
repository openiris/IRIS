/**
 *
 */
package org.openflow.io;

import org.projectfloodlight.openflow.protocol.OFMessage;

/**
 * Interface for writing OFMessages to a buffered stream
 *
 * @author Rob Sherwood (rob.sherwood@stanford.edu)
 *
 */
public interface OFMessageOutStream {
    /**
     * Write an OpenFlow message to the stream
     * @param m An OF Message
     */
    public void write(OFMessage m) throws java.io.IOException;

    /**
     * Pushes buffered data out the Stream; this is NOT guranteed to flush all
     * data, multiple flush() calls may be required, until needFlush() returns
     * false.
     */
    public void flush() throws java.io.IOException;
}
