

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
						feature_ports = data[$scope.id].ports;
						if ( !feature_ports ) {
							// for 1.3 response
							feature_ports = data[$scope.id].entries;
						}
						_.each(feature_ports, function(p)  {
							var px = $scope.portsMap[p.portNumber];
							if ( px ) {
								px.status = (p.currentFeatures == 0 || p.state & 1) ? 'DOWN' : 'UP';
								switch(p.currentFeatures & 0x7f) {
								case 1:
									px.status += ' 10 Mbps';
									break;
								case 2:
									px.status += ' 10 Mbps FDX';
									break;
								case 4:
									px.status += ' 100 Mbps';
									break;
								case 8:
									px.status += ' 100 Mbps FDX';
									break;
								case 16:
									px.status += ' 1 Gbps'; // RLY?
									break;
								case 32:
									px.status += ' 1 Gbps FDX';
									break;
								case 64:
									px.status += ' 10 Gbps FDX';
									break;
								}
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
