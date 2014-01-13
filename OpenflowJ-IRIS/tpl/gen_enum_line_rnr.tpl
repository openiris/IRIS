  	$n	($v, org.openflow.protocol.interfaces.${enumtype}.$n,
  		${typename}Request.class, ${typename}Reply.class,
	    new Instantiable<$supertype>() {
	      public $supertype instantiate() {
	        return new ${typename}Request();
	      }
	    },
	    new Instantiable<$supertype>() {
	      public $supertype instantiate() {
	        return new ${typename}Reply();
	      }
    	}),