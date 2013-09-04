
iris.ports = function(id) {

	if ( ! id ){
		// if id is undefined, just return
		return;
	}
	
	var aSwitch = iris.switchCollection.get(id);
	
	aSwitch.ports.on('add', function() {
		
		var pagenate = 10;
		
		var target=$('tbody[ref="switch_ports_' + id + '"]');
		var pageCount = Math.ceil(aSwitch.ports.length / pagenate);
		var links = target.parent().find('caption span[type="links"]');
		
		links.empty();
		for ( var i = 0; pageCount > 1 && i < pageCount; ++i ) {
			var link = $('<a ref="'+id+'">' + i + '</a>');
			link.click( pageNumberClickHandler );
			links.append(link);
			links.append(' ');
		}
		
		iris.collectionTo(aSwitch.ports, target, pagenate);	
		
		links.find('a').first().click();
	});
	
	aSwitch.loadPorts();
};