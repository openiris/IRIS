package $packagename;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import $importname;

public enum $typename {
    $enumdefs;

    static Map<$orep, $typename> requestMapping;
    static Map<$orep, $typename> replyMapping;
    static Map<$orep, org.openflow.protocol.interfaces.$typename> requestCompatMapping;
    static Map<org.openflow.protocol.interfaces.$typename, $typename> requestCompatReverseMapping;
    static Map<$orep, org.openflow.protocol.interfaces.$typename> replyCompatMapping;
    static Map<org.openflow.protocol.interfaces.$typename, $typename> replyCompatReverseMapping;

    protected Class<? extends $supertype> requestClass;
    protected Class<? extends $supertype> replyClass;
    protected Constructor<? extends $supertype> requestConstructor;
    protected Constructor<? extends $supertype> replyConstructor;
    protected Instantiable<$supertype> requestInstantiable;
    protected Instantiable<$supertype> replyInstantiable;
    protected $rep type;

    $typename(int type, org.openflow.protocol.interfaces.$typename compatType,
    		Class<? extends $supertype> requestClass, Class<? extends $supertype> replyClass,
            Instantiable<$supertype> requestInstantiable, Instantiable<$supertype> replyInstantiable) {
        this.type = ($rep) type;
        this.requestClass = requestClass;
        this.requestInstantiable = requestInstantiable;
        try {
            this.requestConstructor = requestClass.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + this.requestClass, e);
        }
        $typename.addMapping(this.type, $ctype.$reqv, this);
        $typename.addMapping(this.type, compatType, $ctype.$reqv, this);
        
        this.replyClass = replyClass;
        this.replyInstantiable = replyInstantiable;
        try {
            this.replyConstructor = replyClass.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + this.replyClass, e);
        }
        $typename.addMapping(this.type, $ctype.$repv, this);
        $typename.addMapping(this.type, compatType, $ctype.$repv, this);
    }

    static public void addMapping($rep i, $ctype t, $typename st) {
        if (t == $ctype.$reqv) {
            if ( requestMapping == null ) 
            	requestMapping = new ConcurrentHashMap<$orep, $typename>();
            requestMapping.put( i, st );
        } else if (t == $ctype.$repv){
            if ( replyMapping == null )
            	replyMapping = new ConcurrentHashMap<$orep, $typename>();
            replyMapping.put( i, st );
        } else {
            throw new RuntimeException(t.toString() + " is an invalid $ctype");
        }
    }
    
    static public void addMapping($rep i, org.openflow.protocol.interfaces.$typename c, $ctype t, $typename st) {
    	if ( t == ${ctype}.$reqv ) {
    		if ( requestCompatMapping == null ) {
    			requestCompatMapping = new ConcurrentHashMap<$orep, org.openflow.protocol.interfaces.$typename>();
    		}
    		if ( requestCompatReverseMapping == null ) {
    			requestCompatReverseMapping = new ConcurrentHashMap<org.openflow.protocol.interfaces.$typename, $typename>();
    		}
    		
    		requestCompatMapping.put( i, c );
    		requestCompatReverseMapping.put( c, st );
    	}
    	else if ( t == ${ctype}.$repv ) {
    		if ( replyCompatMapping == null ) {
    			replyCompatMapping = new ConcurrentHashMap<$orep, org.openflow.protocol.interfaces.$typename>();
    		}
    		if ( replyCompatReverseMapping == null ) {
    			replyCompatReverseMapping = new ConcurrentHashMap<org.openflow.protocol.interfaces.$typename, $typename>();
    		}
    		
    		replyCompatMapping.put( i, c );
    		replyCompatReverseMapping.put( c, st );
    	}
    	else {
    		throw new RuntimeException(t.toString() + " is an invalid ${ctype}");
        }
    }
    
    static public void addReplyMapping($rep i, $typename t) {
        if ( replyMapping == null )
            replyMapping = new ConcurrentHashMap<$orep, $typename>();
        replyMapping.put(i, t);
    }

    static public $typename valueOf($rep i, $ctype t) {
        if ( t == ${ctype}.$reqv ) {
          return requestMapping.get( i );
        }
        else if ( t == ${ctype}.$repv ) {
          return replyMapping.get( i );
        }
        else {
          throw new RuntimeException(t.toString() + " is an invalid ${ctype}");
        }
    }
    
    /**
     * convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.$typename to($rep i, $ctype t) {
    	if ( t == ${ctype}.$reqv ) {
    		return requestCompatMapping.get(i);
    	}
    	else if ( t == ${ctype}.$repv ) {
    		return replyCompatMapping.get(i);
    	}
    	else {
    		throw new RuntimeException(t.toString() + " is an invalid ${ctype}");
    	}
    }
    
    /**
     * convert from compatibility-support type
     */
    static public $typename from(org.openflow.protocol.interfaces.$typename c, $ctype t) {
    	if ( t == ${ctype}.$reqv ) {
    		return requestCompatReverseMapping.get(c);
    	}
    	else if ( t == ${ctype}.$repv ) {
    		return replyCompatReverseMapping.get(c);
    	}
    	else {
    		throw new RuntimeException(t.toString() + " is an invalid ${ctype}");
    	}
    }
    
    public static $rep readFrom(ByteBuffer data) {
  		return data.get${bytegetname}();
	}

    public $rep getTypeValue() {
        return this.type;
    }

    public Class<? extends $supertype> toClass($ctype t) {
        if (t == $ctype.$reqv) {
            return requestClass;
        } else if (t == $ctype.$repv){
            return replyClass;
        } else {
            throw new RuntimeException(t.toString() + " is an invalid $ctype");
        }
    }

    public Constructor<? extends $supertype> getConstructor($ctype t) {
        if (t == $ctype.$reqv) {
            return requestConstructor;
        } else if (t == $ctype.$repv) {
            return replyConstructor;
        } else {
            throw new RuntimeException(t.toString() + " is an invalid $ctype");
        }
    }

    public $supertype newInstance($ctype t) {
        if (t == $ctype.$reqv) {
            return requestInstantiable.instantiate();
        } else if (t == $ctype.$repv) {
            return replyInstantiable.instantiate();
        } else {
            throw new RuntimeException(t.toString() + " is an invalid $ctype");
        }
    }

    public Instantiable<$supertype> getRequestInstantiable() {
        return requestInstantiable;
    }

    public void setRequestInstantiable(Instantiable<$supertype> requestInstantiable) {
        this.requestInstantiable = requestInstantiable;
    }
    
    public Instantiable<$supertype> getReplyInstantiable() {
        return replyInstantiable;
    }

    public void setReplyInstantiable(Instantiable<$supertype> replyInstantiable) {
        this.replyInstantiable = replyInstantiable;
    }
}