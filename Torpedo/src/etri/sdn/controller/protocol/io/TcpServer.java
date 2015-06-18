package etri.sdn.controller.protocol.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * TCP server that handles OpenFlow connection (accept).
 * @author bjlee
 *
 */
public final class TcpServer extends Thread implements IOFProtocolServer {
	private volatile boolean quit = false;
	
	private ClientChannelWatcher[] watchers;
	private int port_number;
	
	public TcpServer(int port_number, int watcher_num) throws IOException {
		this.port_number = port_number;
		this.watchers = new ClientChannelWatcher[watcher_num];
		for ( int i = 0; i < watchers.length; ++i ) {
			this.watchers[i] = new ClientChannelWatcher();
		}
	}

	@Override
	public int getPortNumber() {
		return this.port_number;
	}
	
	@Override
	public void registerController(IOFHandler controller) {
		controller.registerProtocolServer( this );
		ClientChannelWatcher.registerController( controller );
	}
	
	@Override
	public void deregisterConroller(IOFHandler controller) {
		ClientChannelWatcher.unregisterController(controller);
	}	

	@Override
	public void shutdown() { 
		quit = true;
		this.wakeup();
	}

	@Override
	public void wakeup() {
		for ( int i = 0; i < watchers.length; ++i ) {
			if ( watchers[i] != null ) {
				watchers[i].wakeup();
			}
		}
	}
	
	@Override
	public void run() {
		try {

			// 
			// start all watchers
			//
			for ( int i = 0; i < watchers.length; ++i ) {
				watchers[i].start();
			}

			Selector accept_selector = Selector.open();

			ServerSocketChannel tcp_server = ServerSocketChannel.open();
			tcp_server.socket().bind(new InetSocketAddress(this.port_number));
			tcp_server.configureBlocking(false);
			tcp_server.register( accept_selector, SelectionKey.OP_ACCEPT );

			//
			// start accept loop
			// 
			int accept_seq = 0;
			while ( !quit ) {
				int r = accept_selector.select();

				if ( r > 0 ) {
					// accept set is ready
					Set<SelectionKey> keys = accept_selector.selectedKeys();
					for ( Iterator<SelectionKey> i = keys.iterator(); i.hasNext(); ) {
						SelectionKey key = i.next();
						i.remove();

						if ( key.isAcceptable() ) {
							int seq = ++accept_seq;
							SocketChannel sw_channel = tcp_server.accept();
							sw_channel.configureBlocking(false);
							sw_channel.socket().setTcpNoDelay(true);
							sw_channel.socket().setSendBufferSize(65536);
							sw_channel.socket().setPerformancePreferences(0,2,3);

							watchers[ seq % watchers.length ].addClient( sw_channel );
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			// cannot do further processing.
			return;
		}
	}
}
