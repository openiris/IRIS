package $packagename;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;

import $importname;

public enum $typename {
    $enumdefs;

    static $typename[] requestMapping;
    static $typename[] replyMapping;

    protected Class<? extends $supertype> requestClass;
    protected Class<? extends $supertype> replyClass;
    protected Constructor<? extends $supertype> requestConstructor;
    protected Constructor<? extends $supertype> replyConstructor;
    protected Instantiable<$supertype> requestInstantiable;
    protected Instantiable<$supertype> replyInstantiable;
    protected $rep type;

    $typename(int type, Class<? extends $supertype> requestClass, Class<? extends $supertype> replyClass,
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
        
        this.replyClass = replyClass;
        this.replyInstantiable = replyInstantiable;
        try {
            this.replyConstructor = replyClass.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + this.replyClass, e);
        }
        $typename.addMapping(this.type, $ctype.$repv, this);
    }

    static public void addMapping($rep i, $ctype t, $typename st) {
        if ( i < 0 ) i = ($rep)($length + i);
        if (t == $ctype.$reqv) {
            if (requestMapping == null)
                requestMapping = new $typename[$length];
            $typename.requestMapping[i] = st;
        } else if (t == $ctype.$repv){
            if (replyMapping == null)
                replyMapping = new $typename[$length];
            $typename.replyMapping[i] = st;
        } else {
            throw new RuntimeException(t.toString() + " is an invalid $ctype");
        }
    }
    
    static public void addReplyMapping($rep i, $typename t) {
        if (replyMapping == null)
            replyMapping = new $typename[$length];
        if ( i < 0 ) i = ($rep)($length + i);
        $typename.replyMapping[i] = t;
    }

    static public $typename valueOf($rep i, $ctype t) {
        if ( i < 0 ) i = ($rep)($length + i);
        if ( t == ${ctype}.$reqv ) {
          return requestMapping[i];
        }
        else if ( t == ${ctype}.$repv ) {
          return replyMapping[i];
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