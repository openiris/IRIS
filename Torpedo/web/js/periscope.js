
var iris = {}

//iris.showPortsDialog = function(element) {
//	var id = $(element).text();
//	var dlg = $('#hiddens > div.ports').clone();
//	dlg.dialog({
//		title: 'Ports of the switch ' + id,
//		width: 800,
//		open: function(event, ui) {
//			dlg.setAttribute("ng-include", "tpl/ports.html");
//			var popupScope = $scope.$new();
//		}
//	});
//};

// this is for setting tabs and their initial selection status.
$(document).ready( function() {
	
	// assign handlers to menu buttons
	$('.nav a').click( function() {
		var cl = $(this).attr('class');
		// the class assigned to body represents the block (role)
		// of the current page.
		$('body').removeClass();
		$('body').addClass(cl);

		// show div's belongs to the current block
		$('div.content > div.' + cl).show( 0, function() {
			$(this).trigger('displayed');
		});
		// and hide others
		$('div.content > div:not(.'+cl+')').hide();
	});
	
	// emulate that the first button is pressed when a page is loaded.
	// therefore, after reload, always the 'Home' button is enabled.
	$('.nav a')[0].click();
});

// create angular module for IRIS UI.
var irisApp = angular.module('iris', []);

// create IRIS services that enables data-sharing between IRIS angular controllers.
irisApp.factory('$iris', function() {
	var irisServiceInstance = {
		//
		// switch-related service
		//
		switches: [],
		getSwitches: function() { return self.switches; },
		setSwitches: function(arr) { self.switches = arr; },
		//
		// device-related service
		//
		devices: [],
		getDevices: function() { return self.devices; },
		setDevices: function(arr) { self.devices = arr; }
	};
	return irisServiceInstance;
});