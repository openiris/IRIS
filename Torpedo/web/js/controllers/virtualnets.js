

irisApp.controller('CntlVnets', 
		['$scope', '$http', '$timeout', '$iris', '$compile',
		 function($scope, $http, $timeout, $iris, $compile) {
			// initialize some data internal to this controller.
			$scope.vnetworks = [];
			$scope.vnetworks_ids = [];

			// define getData method.
			$scope.getData = function() {
				$http.get('/wm/ml2/networks')
				.success(function(data) {
					
					// get all the ids in the returned array.
					retrieved_ids = _.map(data, function(vn) { return vn['id'][0]; })
					// calculate diff from device_ids to find out identifiers which 
					// should be removed from devices. 
					to_remove_ids = _.difference($scope.vnetworks_ids, retrieved_ids);
					// calculate diff from device ids to find out identifiers which
					// should be newly added to devices.
					to_add_ids = _.difference(retrieved_ids, $scope.vnetworks_ids);
					
					// now, adjust the device_ids array.
					$scope.vnetworks_ids = _.difference($scope.vnetworks_ids, to_remove_ids);
					$scope.vnetworks_ids = _.union($scope.vnetworks_ids, to_add_ids);
					
					$scope.vnetworks = _.filter($scope.vnetworks, function(vn) { 
						return ! _.contains(to_remove_ids, vn.id); 
					})
					
					_.each(data, function(nets) {
						if ( nets['networks'].length > 0 ) {
							if ( _.contains(to_add_ids, nets.id[0])) {
								// a device which should be newly added.
								$scope.vnetworks.push({
									id: nets['id'];
									name: nets['name'];
									tenant: nets['tenant_id'];
									
								});
							} else {
								var vnets = _.find($scope.vnetworks, function(vn) { return vn.id == nets.id[0]; } );
								
								if(vnets) {
									vnets.id = nets['id'];
									vnets.name = nets['name'];
									vnets.tenant = nets['tenant_id'];
								}
							}
						}
					});
					
					$iris.setVirtualnets( $scope.vnetworks );
				})
				.error(function() {
					$scope.vnetworks = [];
					$scope.vnetworks_ids = [];
				});
			};
			
			// define intervalFunction method.
			$scope.intervalFunction = function() {
				$timeout(function() {
					$scope.getData();
					$scope.intervalFunction();
				}, 5000)
			}
			
			// call two method to fill the initial data into the view.
			$scope.getData();
			$scope.intervalFunction();
		}]
);