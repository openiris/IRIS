  	$n	($v, ${typename}Request.class, ${typename}Reply.class,
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