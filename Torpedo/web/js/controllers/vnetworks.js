

irisApp.controller('CntlVNetworks', 
		['$scope', '$http', '$timeout', '$iris', '$compile',
		 function($scope, $http, $timeout, $iris, $compile) {
			// initialize initial data
			$scope.vNetworks = [];
			$scope.vNetwork_ids = [];
			
			$scope.showVNetworkDetailPopup = function(id) {
				var e = angular.element('#hiddens>div.vNetwork_detail').clone();
				e.append('<ng-include src="\'tpl/vnetwork_detail.html\'"></ng-include>');
				var newScope = $scope.$new();
				$compile(e)( newScope );
				newScope.id = id;
				
				e.dialog({
					title: 'vNetwork Detail Infomation for ' + id,
					width: 800,
					close: function(event, ui) {
						newScope.goon = false;
					}
				});
			};
			
			// define getData method
			$scope.getData = function() {
				$http.get('/wm/ml2/networks')
				.success(function(data) {
					
					// get all the ids in the returned array.
					retrieved_ids = _.map(data.networks, function(obj) { return obj['id']; })
					// calculate diff from vNetwork_ids to find out identifiers which 
					// should be removed from vNetworks. 
					to_remove_ids = _.difference($scope.vNetwork_ids, retrieved_ids);
					// calculate diff from vNetwork ids to find out identifiers which
					// should be newly added to vNetwork.
					to_add_ids = _.difference(retrieved_ids, $scope.vNetwork_ids);
					
					// now, adjust the vNetwork_ids array.
					$scope.vNetwork_ids = _.difference($scope.vNetwork_ids, to_remove_ids);
					$scope.vNetwork_ids = _.union($scope.vNetwork_ids, to_add_ids);
					
					$scope.vNetworks = _.filter($scope.vNetworks, function(d) { return ! _.contains(to_remove_ids, d.id); })
					
					_.each(data.networks, function(d) {
						if ( _.contains(to_add_ids, d['id']) ) {
							$scope.vNetworks.push({
								id: d['id'],
								name: d['name'],
								tenant_id: d['tenant_id'],
								network_type: d['provider:network_type'],
								segmentation_id: d['provider:segmentation_id']
							});
						}
					});
					
					$iris.setVNetworks( $scope.vNetworks );
				})
				.error(function() {
					$scope.vNetworks = [];
					$scope.vNetwork_ids = [];
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