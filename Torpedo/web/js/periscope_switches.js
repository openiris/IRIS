
iris.switchCollection = new SwitchCollection();

var stopTimer = 0;

iris.switches = function() {
	iris.cancelAllTimersNotInBlock( $('body').attr('class') );

	stopTimer = 0;
	var updating = false;
	
	var showSwitchDescInformation = function(id) {
		
		if ( updating ) {
			return;
		}
		
		// find a space to insert the switch flow information.
		var target = $('tr[ref$="' + id + '"] td');
		if ( target.find('div').length > 0 ) {
			return;
		}
		
		++stopTimer;
		
		var source = $('#hiddens div.switch_desc').clone();
		source.find('tbody')
		.attr('ref', 'switch_desc_' + id);
	
		target.addClass('chosen');
		$('tr[ref$="' + id + '"]').prev().addClass('chosen');
		
		source.appendTo(target).css('display', 'block');
		
		$('tr.chosen a[type="desc"]').click( function(event) {
			var link = $(event.target);
			var p = link.parents('tr');
			
			p.removeClass('chosen');
			p.next().find('td.chosen').empty().removeClass('chosen');
			
			link.click( function(e) {showSwitchDescInformation($(e.target).attr('ref'));} );
			
			--stopTimer;
		});
		
		iris.switch_desc(id, source);
	};
	
	var showSwitchPortInformation = function(id) {
		
		if ( updating ) {
			return;
		}
		
		// find a space to insert the switch flow information.
		var target = $('tr[ref$="' + id + '"] td');
		if ( target.find('div').length > 0 ) {
			return;
		}
		
		stopTimer++;
		
		var source = $('#hiddens div.ports').clone();
		source.find('tbody')
		.attr('ref', 'switch_ports_' + id)
		.html('<tr style="border-top: none;"><td colspan="8">no ports</td></tr>');
		
		target.addClass('chosen');
		$('tr[ref$="' + id + '"]').prev().addClass('chosen');
		
		source.appendTo(target).css('display', 'block');

		$('tr.chosen a[type="id"]').click( function(event) {
			var link = $(event.target);
			var p = link.parents('tr');
			
			p.removeClass('chosen');
			p.next().find('td.chosen').empty().removeClass('chosen');
			
			$(event.target).click( function(e) {showSwitchPortInformation($(e.target).text());} );
			
			--stopTimer;
		});
		
		// call to render the flows information.
		iris.ports( id );
	};
	
	// event handler for switch id click.
	var showSwitchFlowInformation = function(id) {
		
		if ( updating ) {
			return;
		}

		// find a space to insert the switch flow information.
		var target = $('tr[ref$="' + id + '"] td');
		if ( target.find('div').length > 0 ) {
			return;
		}
		
		stopTimer++;
		
		var source = $('#hiddens div.flows').clone();
		source.find('tbody')
		.attr('ref', 'switch_flows_' + id)
		.html('<tr style="border-top: none;"><td colspan="8">no flow records</td></tr>');
		
		target.addClass('chosen');
		$('tr[ref$="' + id + '"]').prev().addClass('chosen');
		
		source.appendTo(target).css('display', 'block');
		
		$('tr.chosen a[type="flows"]').click( function(event) {
			var link = $(event.target);
			var p = link.parents('tr');
			
			p.removeClass('chosen');
			p.next().find('td.chosen').empty().removeClass('chosen');
			
			link.click( function(e) {showSwitchFlowInformation($(e.target).attr('ref'));} );
			
			--stopTimer;
		});
		
		// call to render the flows information.
		iris.flows( id );
	};


	// this timer belongs to 'home' and 'switches' page.
	// and the name of the timer is 'switches'.
	iris.doTimers(['home', 'switches'], 'switches', 5000, function() {
		if ( stopTimer > 0 ) {
			return;
		}
		if ( updating ) {
			return;
		}
		// every period, we re-collect switch statistics
		iris.switchCollection.fetch();
	});

	iris.switchCollection.on('add remove change', function() {
		if ( !updating ) {
			updating = true;
			$('#snum').html( iris.switchCollection.length );
			var target = $('tbody[template="switches"]');
			iris.collectionTo(iris.switchCollection, target);
			
			target.find('td a[type="id"]').each( function (index, x) {
				$(x).click( function(event) {showSwitchPortInformation($(x).text());} );
			});

			target.find('td a[type="flows"]').each( function(index, x) {
				$(x).click( function(event) {showSwitchFlowInformation($(x).attr('ref'));} );
			});
			
			target.find('td a[type="desc"]').each( function(index, x) {
				$(x).click( function(event) {showSwitchDescInformation($(x).attr('ref'));} );
			});
			updating = false;
		}
	});

	iris.switchCollection.fetch();
};