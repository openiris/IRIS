/**
 *
 */
package org.openflow.io;

import java.util.List;

import org.openflow.protocol.factory.OFMessageParser;
import org.projectfloodlight.openflow.protocol.OFMessage;

/**
 * Interface for reading OFMessages from a buffered stream
 * 
 * @author Rob Sherwood (rob.sherwood@stanford.edu)
 * 
 */
public interface OFMessageInStream {
    /**
     * Read OF messages from the stream
     * 
     * @return a list of OF Messages, empty if no complete messages are
     *         available, null if the stream has closed
     */
    public List<OFMessage> read() throws java.io.IOException;

    /**
     * Read OF messages from the stream
     * 
     * @param limit
     *            The maximum number of messages to read: 0 means all that are
     *            buffered
     * @return a list of OF Messages, empty if no complete messages are
     *         available, null if the stream has closed
     * 
     */
    public List<OFMessage> read(int limit) throws java.io.IOException;
}
