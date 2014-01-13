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
    static Map<$orep, org.openflow.protocol.interfaces.$typename> compatMapping;
    static Map<org.openflow.protocol.interfaces.$typename, $typename> compatMappingReverse;
    static $rep start_key = 0;
    static $rep end_key = 0;

    protected Class<? extends $supertype> clazz;
    protected Constructor<? extends $supertype> constructor;
    protected Instantiable<$supertype> instantiable;
    protected $rep type;

    $typename(
    	int type, org.openflow.protocol.interfaces.$typename compatType,
    	Class<? extends $supertype> clazz, Instantiable<$supertype> instantiator) 
    {
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
        $typename.addMapping(this.type, compatType, this);
    }

    static public void addMapping($rep i, $typename t) {
        if ( mapping == null )
        	mapping = new ConcurrentHashMap<$orep, $typename>();
        	
        if ( mapping.isEmpty() ) {
        	start_key = i;
        }
        end_key = i;
        mapping.put(i, t);
    }
    
    static public void addMapping($rep i, org.openflow.protocol.interfaces.$typename c, $typename t) {
    	if ( compatMapping == null ) 
    		compatMapping = new ConcurrentHashMap<$orep, org.openflow.protocol.interfaces.$typename>();
    		
    	if ( compatMappingReverse == null )
    		compatMappingReverse = new ConcurrentHashMap<org.openflow.protocol.interfaces.$typename, $typename>();
    		
    	compatMapping.put( i, c );
    	compatMappingReverse.put( c, t );
    }

    static public $typename valueOf($rep i) {
        return mapping.get(i);
    }
    
    /**
     * Convert to compatibility-support type
     */
    static public org.openflow.protocol.interfaces.$typename to($typename i) {
    	return compatMapping.get(i.getTypeValue());
    }
    
    /**
     * Convert from compatibility-support type
     */
    static public $typename from(org.openflow.protocol.interfaces.$typename c) {
    	return compatMappingReverse.get(c);
    }
    
	static public $rep readFrom(ByteBuffer data) {
  		return data.get${bytegetname}();
	}
    
    static public $typename first() {
    	return mapping.get(start_key);
    }
    
    static public $typename last() {
    	return mapping.get(end_key);
    }
    
    static public void parse(List<$supertype> output, ByteBuffer data, int length) {
    	$supertype demux = new $supertype();
    	while ( length > 0 ) {
    		data.mark();
    		demux.readFrom(data);
    		data.reset();
    		
    		$supertype real = $typename.from(demux.getType()).newInstance();
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