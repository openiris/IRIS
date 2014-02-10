

irisApp.controller('CntlDevices', 
		['$scope', '$http', '$timeout', '$iris',
		 function($scope, $http, $timeout, $iris) {
			// initialize initial data
			$scope.devices = [];
			$scope.device_ids = [];
			
			// define getData method
			$scope.getData = function() {
				$http.get('/wm/device/all/json')
				.success(function(data) {
					
					// get all the ids in the returned array.
					retrieved_ids = _.map(data, function(d) { return d['mac'][0]; })
					// calculate diff from device_ids to find out identifiers which 
					// should be removed from devices. 
					to_remove_ids = _.difference($scope.device_ids, retrieved_ids);
					// calculate diff from device ids to find out identifiers which
					// should be newly added to devices.
					to_add_ids = _.difference(retrieved_ids, $scope.device_ids);
					
					// now, adjust the device_ids array.
					$scope.device_ids = _.difference($scope.device_ids, to_remove_ids);
					$scope.device_ids = _.union($scope.device_ids, to_add_ids);
					
					$scope.devices = _.filter($scope.devices, function(d) { return ! _.contains(to_remove_ids, d.id); })
					
					_.each(data, function(host) {
						if ( host['attachmentPoint'].length > 0 ) {
							if ( _.contains(to_add_ids, host.mac[0])) {
								// a device which should be newly added.
								$scope.devices.push({
									id: host.mac[0],
									port: _.reduce(host['attachmentPoint'], function(memo, ap) { return memo + ap.switchDPID + "-" + ap.port + "\n"}, ""),
									lastSeen: new Date(host.lastSeen).toLocaleString(),
									inetAddress: host.ipv4[0],
									attachmentPoint: host['attachmentPoint'],
									mac: host['mac'],
									ipv4: host['ipv4'],
									vlan: host['vlan']
								});
							}
							else {
								// a device whose information should be updated.
								var device = _.find($scope.devices, function(d) { return d.id == host.mac[0]; } );
								if ( device ) {
									device.attachmentPoint = host['attachmentPoint'];
									device.mac = host['mac'];
									device.ipv4 = host['ipv4'];
									device.vlan = host['vlan'];
									device.port = _.reduce(host['attachmentPoint'], function(memo, ap) { return memo + ap.switchDPID + "-" + ap.port + "\n"}, "");
									device.lastSeen = new Date(host.lastSeen).toLocaleString();
								}
							}							
	                    }
					});
					
					$iris.setDevices( $scope.devices );
				})
				.error(function() {
					$scope.devices = [];
					$scope.device_ids = [];
				});
			};
			
			$scope.intervalFunction = function() {
				$timeout(function() {
					$scope.getData();
					$scope.intervalFunction();
				}, 5000)
			}
			
			// call to fill the initial data into the view.
			$scope.getData();
			$scope.intervalFunction();
		}]
);