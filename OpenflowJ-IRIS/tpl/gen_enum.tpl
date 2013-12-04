package $packagename;

import java.nio.ByteBuffer;

import java.lang.reflect.Constructor;
import org.openflow.util.Instantiable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import $importname;

public enum $typename {
    $enumdefs;

    // static $typename[] mapping;
    static Map<$orep, $typename> mapping;
    static $rep start_key = 0;
    static $rep end_key = 0;

    protected Class<? extends $supertype> clazz;
    protected Constructor<? extends $supertype> constructor;
    protected Instantiable<$supertype> instantiable;
    protected $rep type;

    $typename(int type, Class<? extends $supertype> clazz, Instantiable<$supertype> instantiator) {
        this.type = ($rep) type;
        this.clazz = clazz;
        this.instantiable = instantiator;
        try {
            this.constructor = clazz.getConstructor(new Class[]{});
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failure getting constructor for class: " + clazz, e);
        }
        $typename.addMapping(this.type, this);
    }

    static public void addMapping($rep i, $typename t) {
    	/*
        if (mapping == null)
            mapping = new $typename[$length];
        if ( i < 0 ) i = ($rep)($length + i);
        $typename.mapping[i] = t;
        */
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<$orep, $typename>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }

    static public $typename valueOf($rep i) {
    	/*
        if ( i < 0 ) i = ($rep)($length + i);
        return $typename.mapping[i];
        */
        return mapping.get(i);
    }
    
	static public $rep readFrom(ByteBuffer data) {
  		return data.get${bytegetname}();
	}
    
    static public $typename first() {
    	// return $typename.mapping[0];
    	return mapping.get(start_key);
    }
    
    static public $typename last() {
    	// return $typename.mapping[$typename.mapping.length - 1];
    	return mapping.get(end_key);
    }
    
    static public void parse(List<$supertype> output, ByteBuffer data, int length) {
    	$supertype demux = new $supertype();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		$supertype real = demux.getType().newInstance();
    		real.readFrom(data);
    		output.add(real);
    		length -= real.getLength();
    	}	
    }
    
    static public void write(List<$supertype> to_write, ByteBuffer data) {
    	for ( $supertype i : to_write ) {
    		i.writeTo(data);
    	}
    }

    public $rep getTypeValue() {
        return this.type;
    }

    public Class<? extends $supertype> toClass() {
        return clazz;
    }

    public Constructor<? extends $supertype> getConstructor() {
        return constructor;
    }

    public $supertype newInstance() {
        return instantiable.instantiate();
    }

    public Instantiable<$supertype> getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(Instantiable<$supertype> instantiable) {
        this.instantiable = instantiable;
    }
}