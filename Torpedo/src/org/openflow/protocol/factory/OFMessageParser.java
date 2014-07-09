package org.openflow.protocol.factory;

import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.projectfloodlight.openflow.protocol.OFMessage;


/**
 * The interface to factories used for retrieving OFMessage instances. All
 * methods are expected to be thread-safe.
 * @author David Erickson (daviderickson@cs.stanford.edu)
 */
public interface OFMessageParser {

    /**
     * Attempts to parse and return all OFMessages contained in the given
     * ByteBuffer, beginning at the ByteBuffer's position, and ending at the
     * ByteBuffer's limit.
     * @param data the ByteBuffer to parse for an OpenFlow message
     * @return a list of OFMessage instances
     */
    public List<OFMessage> parseMessages(ChannelBuffer data);

    /**
     * Attempts to parse and return all OFMessages contained in the given
     * ByteBuffer, beginning at the ByteBuffer's position, and ending at the
     * ByteBuffer's limit.
     * @param data the ByteBuffer to parse for an OpenFlow message
     * @param limit the maximum number of messages to return, 0 means no limit
     * @return a list of OFMessage instances
     */
    public List<OFMessage> parseMessages(ChannelBuffer data, int limit);
}
