

irisApp.controller('CntlVPorts', 
		['$scope', '$http', '$timeout', '$iris', '$compile',
		 function($scope, $http, $timeout, $iris, $compile) {
			// initialize initial data
			$scope.vPorts = [];
			$scope.vPort_ids = [];
			
			$scope.showVPortDetailPopup = function(id) {
				var e = angular.element('#hiddens>div.vPort_detail').clone();
				e.append('<ng-include src="\'tpl/vport_detail.html\'"></ng-include>');
				var newScope = $scope.$new();
				$compile(e)( newScope );
				newScope.id = id;
				
				e.dialog({
					title: 'vPort Detail Infomation for ' + id,
					width: 800,
					close: function(event, ui) {
						newScope.goon = false;
					}
				});
			};
			
			// define getData method
			$scope.getData = function() {
				$http.get('/wm/ml2/ports')
				.success(function(data) {
					
					// get all the ids in the returned array.
					retrieved_ids = _.map(data.ports, function(obj) { return obj['id']; })
					// calculate diff from vPort_ids to find out identifiers which 
					// should be removed from vPorts. 
					to_remove_ids = _.difference($scope.vPort_ids, retrieved_ids);
					// calculate diff from vPort ids to find out identifiers which
					// should be newly added to vPort.
					to_add_ids = _.difference(retrieved_ids, $scope.vPort_ids);
					
					// now, adjust the vPort_ids array.
					$scope.vPort_ids = _.difference($scope.vPort_ids, to_remove_ids);
					$scope.vPort_ids = _.union($scope.vPort_ids, to_add_ids);
					
					$scope.vPorts = _.filter($scope.vPorts, function(d) { return ! _.contains(to_remove_ids, d.id); })
					
					_.each(data.ports, function(d) {
						if ( _.contains(to_add_ids, d['id']) ) {
							$scope.vPorts.push({
								id: d['id'],
								name: d['name'],
								tenant_id: d['tenant_id'],
								network_id: d['network_id'],
								mac_address: d['mac_address']
							});
						}
					});
					
					$iris.setVPorts( $scope.vPorts );
				})
				.error(function() {
					$scope.vPorts = [];
					$scope.vPort_ids = [];
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