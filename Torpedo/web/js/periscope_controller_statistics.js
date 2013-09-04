
iris.controller_statistics = function() {
	var statDefault = {
		host: 'localhost',
	    ofport: 6633,
	    uptime: 'unknown',
	    free: 0,
	    total: 0,
	    healthy: 'unknown',
	    modules: [],
	    moduleText: ''
	}
	iris.ajaxTo( '/wm/core/health/json', $('table[template="controller_statistics"]'), statDefault );
	
	iris.doTimers(['home'], 'controller_statistics', 5000, function() {
		// every period, we re-collect switch statistics
		iris.ajaxTo( '/wm/core/health/json', $('table[template="controller_statistics"]'), statDefault );
	});
};
