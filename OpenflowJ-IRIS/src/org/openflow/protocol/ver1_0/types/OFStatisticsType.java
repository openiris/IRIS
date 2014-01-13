package org.openflow.protocol.ver1_0.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.ver1_0.messages.*;

public enum OFStatisticsType {
    DESC	(0, org.openflow.protocol.interfaces.OFStatisticsType.DESC,
  		OFStatisticsDescRequest.class, OFStatisticsDescReply.class,
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsDescRequest();
	      }
	    },
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsDescReply();
	      }
    	}),
  	FLOW	(1, org.openflow.protocol.interfaces.OFStatisticsType.FLOW,
  		OFStatisticsFlowRequest.class, OFStatisticsFlowReply.class,
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsFlowRequest();
	      }
	    },
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsFlowReply();
	      }
    	}),
  	AGGREGATE	(2, org.openflow.protocol.interfaces.OFStatisticsType.AGGREGATE,
  		OFStatisticsAggregateRequest.class, OFStatisticsAggregateReply.class,
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsAggregateRequest();
	      }
	    },
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsAggregateReply();
	      }
    	}),
  	TABLE	(3, org.openflow.protocol.interfaces.OFStatisticsType.TABLE,
  		OFStatisticsTableRequest.class, OFStatisticsTableReply.class,
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsTableRequest();
	      }
	    },
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsTableReply();
	      }
    	}),
  	PORT	(4, org.openflow.protocol.interfaces.OFStatisticsType.PORT,
  		OFStatisticsPortRequest.class, OFStatisticsPortReply.class,
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsPortRequest();
	      }
	    },
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsPortReply();
	      }
    	}),
  	QUEUE	(5, org.openflow.protocol.interfaces.OFStatisticsType.QUEUE,
  		OFStatisticsQueueRequest.class, OFStatisticsQueueReply.class,
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsQueueRequest();
	      }
	    },
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsQueueReply();
	      }
    	}),
  	VENDOR	(0xffff, org.openflow.protocol.interfaces.OFStatisticsType.VENDOR,
  		OFStatisticsVendorRequest.class, OFStatisticsVendorReply.class,
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsVendorRequest();
	      }
	    },
	    new Instantiable<OFStatistics>() {
	      public OFStatistics instantiate() {
	        return new OFStatisticsVendorReply();
	      }
    	});

    static Map<Short, OFStatisticsType> requestMapping;
    static Map<Short, OFStatisticsType> replyMapping;
    static Map<Short, org.openflow.protocol.interfaces.OFStatisticsType> requestCompatMapping;
    static Map<org.openflow.protocol.interfaces.OFStatisticsType, OFStatisticsType> requestCompatReverseMapping;
    static Map<Short, org.openflow.protocol.interfaces.OFStatisticsType> replyCompatMapping;
    static Map<org.openflow.protocol.interfaces.OFStatisticsType, OFStatisticsType> replyCompatReverseMapping;

    protected Class<? extends OFStatistics> requestClass;
    protected Class<? extends OFStatistics> replyClass;
    protected Constructor<? extends OFStatistics> requestConstructor;
    protected Constructor<? extends OFStatistics> replyConstructor;
    protected Instantiable<OFStatistics> requestInstantiable;
    protected Instantiable<OFStatistics> replyInstantiable;
    protected short type;

    OFStatisticsType(int type, org.openflow.protocol.interfaces.OFStatisticsType compatType,
    		Class<? extends OFStatistics> requestClass, Class<? extends OFStatistics> replyClass,
            Instantiable<OFStatistics> requestInstantiable, Instantiable<OFStatistics> replyInstantiable) {
        this.type = (short) type;
        this.requestClass = requestClass;
        this.requestInstantiable = requestInstantiable;
        try {
            this.requestConstructor = requestClass.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + this.requestClass, e);
        }
        OFStatisticsType.addMapping(this.type, OFMessageType.STATISTICS_REQUEST, this);
        OFStatisticsType.addMapping(this.type, compatType, OFMessageType.STATISTICS_REQUEST, this);
        
        this.replyClass = replyClass;
        this.replyInstantiable = replyInstantiable;
        try {
            this.replyConstructor = replyClass.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + this.replyClass, e);
        }
        OFStatisticsType.addMapping(this.type, OFMessageType.STATISTICS_REPLY, this);
        OFStatisticsType.addMapping(this.type, compatType, OFMessageType.STATISTICS_REPLY, this);
    }

    static public void addMapping(short i, OFMessageType t, OFStatisticsType st) {
        if (t == OFMessageType.STATISTICS_REQUEST) {
            if ( requestMapping == null ) 
            	requestMapping = new ConcurrentHashMap<Short, OFStatisticsType>();
            requestMapping.put( i, st );
        } else if (t == OFMessageType.STATISTICS_REPLY){
            if ( replyMapping == null )
            	replyMapping = new ConcurrentHashMap<Short, OFStatisticsType>();
            replyMapping.put( i, st );
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }
    
    static public void addMapping(short i, org.openflow.protocol.interfaces.OFStatisticsType c, OFMessageType t, OFStatisticsType st) {
    	if ( t == OFMessageType.STATISTICS_REQUEST ) {
    		if ( requestCompatMapping == null ) {
    			requestCompatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFStatisticsType>();
    		}
    		if ( requestCompatReverseMapping == null ) {
    			requestCompatReverseMapping = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFStatisticsType, OFStatisticsType>();
    		}
    		
    		requestCompatMapping.put( i, c );
    		requestCompatReverseMapping.put( c, st );
    	}
    	else if ( t == OFMessageType.STATISTICS_REPLY ) {
    		if ( replyCompatMapping == null ) {
    			replyCompatMapping = new ConcurrentHashMap<Short, org.openflow.protocol.interfaces.OFStatisticsType>();
    		}
    		if ( replyCompatReverseMapping == null ) {
    			replyCompatReverseMapping = new ConcurrentHashMap<org.openflow.protocol.interfaces.OFStatisticsType, OFStatisticsType>();
    		}
    		
    		replyCompatMapping.put( i, c );
    		replyCompatReverseMapping.put( c, st );
    	}
    	else {
    		throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }
    
    static public void addReplyMapping(short i, OFStatisticsType t) {
        if ( replyMapping == null )
            replyMapping = new ConcurrentHashMap<Short, OFStatisticsType>();
        replyMapping.put(i, t);
    }

    static public OFStatisticsType valueOf(short i, OFMessageType t) {
        if ( t == OFMessageType.STATISTICS_REQUEST ) {
          return requestMapping.get( i );
        }
        else if ( t == OFMessageType.STATISTICS_REPLY ) {
          return replyMapping.get( i );
        }
        else {
          throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }
    
    /**
     * convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.OFStatisticsType to(short i, OFMessageType t) {
    	if ( t == OFMessageType.STATISTICS_REQUEST ) {
    		return requestCompatMapping.get(i);
    	}
    	else if ( t == OFMessageType.STATISTICS_REPLY ) {
    		return replyCompatMapping.get(i);
    	}
    	else {
    		throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
    	}
    }
    
    /**
     * convert from compatibility-support type
     */
    static public OFStatisticsType from(org.openflow.protocol.interfaces.OFStatisticsType c, OFMessageType t) {
    	if ( t == OFMessageType.STATISTICS_REQUEST ) {
    		return requestCompatReverseMapping.get(c);
    	}
    	else if ( t == OFMessageType.STATISTICS_REPLY ) {
    		return replyCompatReverseMapping.get(c);
    	}
    	else {
    		throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
    	}
    }
    
    public static short readFrom(ByteBuffer data) {
  		return data.getShort();
	}

    public short getTypeValue() {
        return this.type;
    }

    public Class<? extends OFStatistics> toClass(OFMessageType t) {
        if (t == OFMessageType.STATISTICS_REQUEST) {
            return requestClass;
        } else if (t == OFMessageType.STATISTICS_REPLY){
            return replyClass;
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }

    public Constructor<? extends OFStatistics> getConstructor(OFMessageType t) {
        if (t == OFMessageType.STATISTICS_REQUEST) {
            return requestConstructor;
        } else if (t == OFMessageType.STATISTICS_REPLY) {
            return replyConstructor;
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }

    public OFStatistics newInstance(OFMessageType t) {
        if (t == OFMessageType.STATISTICS_REQUEST) {
            return requestInstantiable.instantiate();
        } else if (t == OFMessageType.STATISTICS_REPLY) {
            return replyInstantiable.instantiate();
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }

    public Instantiable<OFStatistics> getRequestInstantiable() {
        return requestInstantiable;
    }

    public void setRequestInstantiable(Instantiable<OFStatistics> requestInstantiable) {
        this.requestInstantiable = requestInstantiable;
    }
    
    public Instantiable<OFStatistics> getReplyInstantiable() {
        return replyInstantiable;
    }

    public void setReplyInstantiable(Instantiable<OFStatistics> replyInstantiable) {
        this.replyInstantiable = replyInstantiable;
    }
}