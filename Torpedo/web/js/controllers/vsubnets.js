

irisApp.controller('CntlVSubnets', 
		['$scope', '$http', '$timeout', '$iris', '$compile',
		 function($scope, $http, $timeout, $iris, $compile) {
			// initialize initial data
			$scope.vSubnets = [];
			$scope.vSubnet_ids = [];
			
			$scope.showVSubnetDetailPopup = function(id) {
				var e = angular.element('#hiddens>div.vSubnet_detail').clone();
				e.append('<ng-include src="\'tpl/vsubnet_detail.html\'"></ng-include>');
				var newScope = $scope.$new();
				$compile(e)( newScope );
				newScope.id = id;
				
				e.dialog({
					title: 'vSubnet Detail Infomation for ' + id,
					width: 800,
					close: function(event, ui) {
						newScope.goon = false;
					}
				});
			};
			
			// define getData method
			$scope.getData = function() {
				$http.get('/wm/ml2/subnets')
				.success(function(data) {
					
					// get all the ids in the returned array.
					retrieved_ids = _.map(data.subnets, function(obj) { return obj['id']; })
					// calculate diff from vSubnet_ids to find out identifiers which 
					// should be removed from vSubnets. 
					to_remove_ids = _.difference($scope.vSubnet_ids, retrieved_ids);
					// calculate diff from vSubnet ids to find out identifiers which
					// should be newly added to vSubnet.
					to_add_ids = _.difference(retrieved_ids, $scope.vSubnet_ids);
					
					// now, adjust the vSubnet_ids array.
					$scope.vSubnet_ids = _.difference($scope.vSubnet_ids, to_remove_ids);
					$scope.vSubnet_ids = _.union($scope.vSubnet_ids, to_add_ids);
					
					$scope.vSubnets = _.filter($scope.vSubnets, function(d) { return ! _.contains(to_remove_ids, d.id); })
					
					_.each(data.subnets, function(d) {
						if ( _.contains(to_add_ids, d['id']) ) {
							$scope.vSubnets.push({
								id: d['id'],
								name: d['name'],
								tenant_id: d['tenant_id'],
								network_id: d['network_id'],
								gateway_ip: d['gateway_ip']
							});
						}
					});
					
					$iris.setVSubnets( $scope.vSubnets );
				})
				.error(function() {
					$scope.vSubnets = [];
					$scope.vSubnet_ids = [];
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