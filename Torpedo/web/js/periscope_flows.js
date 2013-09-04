
var pageNumberClickHandler = function(event) {
	var num = $(event.target).text();
	var id = $(event.target).attr('ref');

	console.log(num);
	console.log(id);

	var t = $('tbody[ref="switch_flows_' + id + '"]');
	t.find('tr:not([page="' + num + '"])').css('display', 'none');
	t.find('tr[page="' + num + '"]').css('display', 'table-row');
	
	event.preventDefault();
	return false;
};
	
iris.flows = function(id) {
	if ( ! id ) {
		// if id is undefined, we just return.
		return;
	}
	
	var aSwitch = iris.switchCollection.get(id);
	
	aSwitch.flows.on( 'add', function() {
		
		var pagenate = 10;
		var target = $('tbody[ref="switch_flows_' + id + '"]');
		var pageCount = Math.ceil(aSwitch.flows.length / pagenate);
		var links = target.parent().find('caption span[type="links"]');
		
		links.empty();
		for ( var i = 0; pageCount > 1 && i < pageCount; ++i ) {
			var link = $('<a ref="'+id+'">' + i + '</a>');
			link.click( pageNumberClickHandler );
			links.append(link);
			links.append(' ');
		}

		iris.collectionTo(aSwitch.flows, target, pagenate);

		links.find('a').first().click();
	});
	
	aSwitch.loadFlows();
};