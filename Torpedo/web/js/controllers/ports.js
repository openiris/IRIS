

irisApp.controller('CntlPorts', 
		['$scope', '$http', '$timeout', 
		 function($scope, $http, $timeout) {
			// initialize some data internal to this controller.
			$scope.ports = [];
			$scope.portsMap = {};
			
			// define getData method.
			$scope.getData = function() {
				// scope.id is set externally.
				$http.get('/wm/core/switch/'+ $scope.id +'/port/json')
				.success(function(data) {
					
					_.each(data[$scope.id], function(port) {
						if ( ! $scope.portsMap[port.portNumber] ) {
							$scope.ports.push( port );
						
							// build map for later feature request operation.
							$scope.portsMap[port.portNumber] = port;
						} else { 
							var px = $scope.portsMap[port.portNumber];
							px.transmitBytes = port.transmitBytes;
							px.receiveBytes = port.receiveBytes;
							px.transmitPackets = port.transmitPackets;
							px.receiveDropped = port.receiveDropped;
							px.transmitDropped = port.transmitDropped;
							px.receiveErrors = port.receiveErrors;
							px.transmitErrors = port.transmitErrors;
						}
					});
					
					$http.get('/wm/core/switch/' + $scope.id + '/features/json')
					.success(function(data) {
						_.each(data[$scope.id].ports, function(p)  {
							var px = $scope.portsMap[p.portNumber];
							if ( px ) {
								px.status = (p.currentFeatures == 0 || p.state & 1) ? 'DOWN' : 'UP';
								px.name = p.name;
							}
						});
					});
				})
				.error(function(){
					$scope.ports = [];
					$scope.portsMap = {};
				});
				
				// go-on flag will be externally set.
				if ( $scope.goon != false ) {
					$timeout(function(){
						$scope.getData();
					}, 1000);
				} else {
					$scope.ports = [];
					$scope.portsMap = {}
				}
			};
			
			// fire once.
			$scope.getData();
		}]
);
