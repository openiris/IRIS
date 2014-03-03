

irisApp.controller('CntlSwitches', 
		['$scope', '$http', '$timeout', '$iris', '$compile',
		 function($scope, $http, $timeout, $iris, $compile) {
			// initialize some data internal to this controller.
			$scope.switch_dpids = [];
			$scope.switches = [];
			
			$scope.showPortsPopup = function(id) {
				var e = angular.element('#hiddens>div.ports').clone();
				e.append('<ng-include src="\'tpl/ports.html\'"></ng-include>');
				var newScope = $scope.$new();
				$compile(e)( newScope );
				newScope.id = id;
				
				e.dialog({
					title: 'Ports of the switch ' + id,
					width: 800,
					close: function(event, ui) {
						newScope.goon = false;
					}
				});
			};
			
			$scope.showSwitchDescPopup = function(id) {
				var e = angular.element('#hiddens>div.switch_desc').clone();
				e.append('<ng-include src="\'tpl/switch_desc.html\'"></ng-include>');
				var newScope = $scope.$new();
				$compile(e)( newScope );
				newScope.id = id;
				
				angular.element('div.content').append(e);
				
				e.dialog({
					title: 'Switch Description for ' + id,
					width: 600
				});
			}
			
			$scope.showFlowsPopup = function(id) {
				var e = angular.element('#hiddens>div.flows').clone();
				e.append('<ng-include src="\'tpl/switch_flows.html\'"></ng-include>');
				var newScope = $scope.$new();
				$compile(e)( newScope );
				newScope.id = id;
				
				e.dialog({
					title: 'Flow Records of switch ' + id,
					width: 1000,
					maxHeight: 600,
					close: function(event, ui) {
						newScope.goon = false;
					}
				});
			}
			
			// define getData method.
			$scope.getData = function() {
				$http.get('/wm/core/controller/switches/json')
				.success(function(data) {
					// get all the dpids in the returned array.
					retrieved_dpids = _.map(data, function(obj) { return obj['dpid']; })
					// calculate diff from switch_dpids to find out identifiers which 
					// should be removed from switches. 
					to_remove_dpids = _.difference($scope.switch_dpids, retrieved_dpids);
					// calculate diff from switch dpids to find out identifiers which
					// should be newly added to switches.
					to_add_dpids = _.difference(retrieved_dpids, $scope.switch_dpids);
					
					// now, adjust the switch_dpids array.
					$scope.switch_dpids = _.difference($scope.switch_dpids, to_remove_dpids);
					$scope.switch_dpids = _.union($scope.switch_dpids, to_add_dpids);
					
					$scope.switches = _.filter($scope.switches, function(sw) { return ! _.contains(to_remove_dpids, sw.id); })
					
					_.each(data, function(sw) {
						if ( _.contains(to_add_dpids, sw['dpid']) ) {
							$scope.switches.push({
								id: sw['dpid'],
								inetAddress: sw.inetAddress,
								connectedSince: new Date(sw.connectedSince).toLocaleString(),
								vendor: '-',
								packets: '0',
								bytes: '0',
								flows: '0'
							});
						}
					});
					
					$iris.setSwitches( $scope.switches );
					
					$scope.getDescription();
					$scope.getAggregateStatistics();
				})
				.error(function() {
					$scope.switches = [];
					$scope.switch_dpids = [];
				});
			};
			
			// define getDescription method.
			$scope.getDescription = function() {
				_.each($scope.switches, function(sw) {
					if (sw.vendor == '-') {
						$http.get('/wm/core/switch/' + sw.id + '/desc/json')
						.success(function(data) {
							sw.vendor = data[sw.id][0]['manufacturerDescription'];
							sw.manufacturerDescription = sw.vendor;
							sw.hardwareDescription = data[sw.id][0]['hardwareDescription'];
							sw.datapathDescription = data[sw.id][0]['datapathDescription'];
							sw.serialNumber = data[sw.id][0]['serialNumber'];
							sw.softwareDescription = data[sw.id][0]['softwareDescription'];
						});
					}
				});
			};
			
			// define getAggregateStatistics method.
			$scope.getAggregateStatistics = function() {
				_.each($scope.switches, function(sw) {
					$http.get('/wm/core/switch/' + sw.id + '/aggregate/json')
					.success(function(data) {
						if ( data && data[sw.id] && data[sw.id][0] ) {
							sw.packets = data[sw.id][0]['packetCount'];
							sw.bytes = data[sw.id][0]['byteCount'];
							sw.flows = data[sw.id][0]['flowCount'];
						} else {
							sw.packets = '-';
							sw.bytes = '-';
							sw.flows = '-';
						}
					})
					.error(function() {
						sw.packets = '-';
						sw.bytes = '-';
						sw.flows = '-';
					});
				});
			};
			
			// define intervalFunction method.
			$scope.intervalFunction = function() {
				$timeout(function(){
					$scope.getData();
					$scope.intervalFunction();
				}, 5000);
			};
			
			// call two method to fill the initial data into the view.
			$scope.getData();
			$scope.intervalFunction();
		}]
);
