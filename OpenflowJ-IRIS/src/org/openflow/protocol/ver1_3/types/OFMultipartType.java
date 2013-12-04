package org.openflow.protocol.ver1_3.types;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openflow.protocol.ver1_3.messages.*;

public enum OFMultipartType {
    DESC	(0, OFMultipartDescRequest.class, OFMultipartDescReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartDescRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartDescReply();
	      }
    	}),
  	FLOW	(1, OFMultipartFlowRequest.class, OFMultipartFlowReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartFlowRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartFlowReply();
	      }
    	}),
  	AGGREGATE	(2, OFMultipartAggregateRequest.class, OFMultipartAggregateReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartAggregateRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartAggregateReply();
	      }
    	}),
  	TABLE	(3, OFMultipartTableRequest.class, OFMultipartTableReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartTableRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartTableReply();
	      }
    	}),
  	PORT_STATS	(4, OFMultipartPortStatsRequest.class, OFMultipartPortStatsReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartPortStatsRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartPortStatsReply();
	      }
    	}),
  	QUEUE	(5, OFMultipartQueueRequest.class, OFMultipartQueueReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartQueueRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartQueueReply();
	      }
    	}),
  	GROUP	(6, OFMultipartGroupRequest.class, OFMultipartGroupReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartGroupRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartGroupReply();
	      }
    	}),
  	GROUP_DESC	(7, OFMultipartGroupDescRequest.class, OFMultipartGroupDescReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartGroupDescRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartGroupDescReply();
	      }
    	}),
  	GROUP_FEATURES	(8, OFMultipartGroupFeaturesRequest.class, OFMultipartGroupFeaturesReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartGroupFeaturesRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartGroupFeaturesReply();
	      }
    	}),
  	METER	(9, OFMultipartMeterRequest.class, OFMultipartMeterReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartMeterRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartMeterReply();
	      }
    	}),
  	METER_CONFIG	(10, OFMultipartMeterConfigRequest.class, OFMultipartMeterConfigReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartMeterConfigRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartMeterConfigReply();
	      }
    	}),
  	METER_FEATURES	(11, OFMultipartMeterFeaturesRequest.class, OFMultipartMeterFeaturesReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartMeterFeaturesRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartMeterFeaturesReply();
	      }
    	}),
  	TABLE_FEATURES	(12, OFMultipartTableFeaturesRequest.class, OFMultipartTableFeaturesReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartTableFeaturesRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartTableFeaturesReply();
	      }
    	}),
  	PORT_DESC	(13, OFMultipartPortDescRequest.class, OFMultipartPortDescReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartPortDescRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartPortDescReply();
	      }
    	}),
  	EXPERIMENTER	(0xffff, OFMultipartExperimenterRequest.class, OFMultipartExperimenterReply.class,
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartExperimenterRequest();
	      }
	    },
	    new Instantiable<OFMultipart>() {
	      public OFMultipart instantiate() {
	        return new OFMultipartExperimenterReply();
	      }
    	});

	/*
    static OFMultipartType[] requestMapping;
    static OFMultipartType[] replyMapping;
    */
    static Map<Short, OFMultipartType> requestMapping;
    static Map<Short, OFMultipartType> replyMapping;

    protected Class<? extends OFMultipart> requestClass;
    protected Class<? extends OFMultipart> replyClass;
    protected Constructor<? extends OFMultipart> requestConstructor;
    protected Constructor<? extends OFMultipart> replyConstructor;
    protected Instantiable<OFMultipart> requestInstantiable;
    protected Instantiable<OFMultipart> replyInstantiable;
    protected short type;

    OFMultipartType(int type, Class<? extends OFMultipart> requestClass, Class<? extends OFMultipart> replyClass,
            Instantiable<OFMultipart> requestInstantiable, Instantiable<OFMultipart> replyInstantiable) {
        this.type = (short) type;
        this.requestClass = requestClass;
        this.requestInstantiable = requestInstantiable;
        try {
            this.requestConstructor = requestClass.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + this.requestClass, e);
        }
        OFMultipartType.addMapping(this.type, OFMessageType.MULTIPART_REQUEST, this);
        
        this.replyClass = replyClass;
        this.replyInstantiable = replyInstantiable;
        try {
            this.replyConstructor = replyClass.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + this.replyClass, e);
        }
        OFMultipartType.addMapping(this.type, OFMessageType.MULTIPART_REPLY, this);
    }

    static public void addMapping(short i, OFMessageType t, OFMultipartType st) {
        if ( i < 0 ) i = (short)(15 + i);
        if (t == OFMessageType.MULTIPART_REQUEST) {
        	/*
            if (requestMapping == null)
                requestMapping = new OFMultipartType[15];
            OFMultipartType.requestMapping[i] = st;
            */
            if ( requestMapping == null ) 
            	requestMapping = new ConcurrentHashMap<Short, OFMultipartType>();
            requestMapping.put( i, st );
        } else if (t == OFMessageType.MULTIPART_REPLY){
        	/*
            if (replyMapping == null)
                replyMapping = new OFMultipartType[15];
            OFMultipartType.replyMapping[i] = st;
            */
            if ( replyMapping == null )
            	replyMapping = new ConcurrentHashMap<Short, OFMultipartType>();
            replyMapping.put( i, st );
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }
    
    static public void addReplyMapping(short i, OFMultipartType t) {
    	/*
        if (replyMapping == null)
            replyMapping = new OFMultipartType[15];
        if ( i < 0 ) i = (short)(15 + i);
        OFMultipartType.replyMapping[i] = t;
        */
        if ( replyMapping == null )
            replyMapping = new ConcurrentHashMap<Short, OFMultipartType>();
        replyMapping.put(i, t);
    }

    static public OFMultipartType valueOf(short i, OFMessageType t) {
        if ( i < 0 ) i = (short)(15 + i);
        if ( t == OFMessageType.MULTIPART_REQUEST ) {
          // return requestMapping[i];
          return requestMapping.get( i );
        }
        else if ( t == OFMessageType.MULTIPART_REPLY ) {
          // return replyMapping[i];
          return replyMapping.get( i );
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

    public Class<? extends OFMultipart> toClass(OFMessageType t) {
        if (t == OFMessageType.MULTIPART_REQUEST) {
            return requestClass;
        } else if (t == OFMessageType.MULTIPART_REPLY){
            return replyClass;
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }

    public Constructor<? extends OFMultipart> getConstructor(OFMessageType t) {
        if (t == OFMessageType.MULTIPART_REQUEST) {
            return requestConstructor;
        } else if (t == OFMessageType.MULTIPART_REPLY) {
            return replyConstructor;
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }

    public OFMultipart newInstance(OFMessageType t) {
        if (t == OFMessageType.MULTIPART_REQUEST) {
            return requestInstantiable.instantiate();
        } else if (t == OFMessageType.MULTIPART_REPLY) {
            return replyInstantiable.instantiate();
        } else {
            throw new RuntimeException(t.toString() + " is an invalid OFMessageType");
        }
    }

    public Instantiable<OFMultipart> getRequestInstantiable() {
        return requestInstantiable;
    }

    public void setRequestInstantiable(Instantiable<OFMultipart> requestInstantiable) {
        this.requestInstantiable = requestInstantiable;
    }
    
    public Instantiable<OFMultipart> getReplyInstantiable() {
        return replyInstantiable;
    }

    public void setReplyInstantiable(Instantiable<OFMultipart> replyInstantiable) {
        this.replyInstantiable = replyInstantiable;
    }
}