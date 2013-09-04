iris.hostCollection = new HostCollection();

iris.devices = function() {
	iris.cancelAllTimersNotInBlock( $('body').attr('class') );

	var updating = false;

	// this timer belongs to 'home' and 'devices' page.
	// and the name of the timer is 'devices'.
	iris.doTimers(['home', 'devices'], 'devices', 5000, function() {
		// every period, we re-collect switch statistics
		iris.hostCollection.fetch();
	});

	iris.hostCollection.on("add remove change", function() {
		if ( !updating ) {
			updating = true;
			$('#dnum').html( iris.hostCollection.length );
			iris.collectionTo(iris.hostCollection, $('tbody[template="devices"]'));
			updating = false;
		}
	});

	iris.hostCollection.fetch();
};