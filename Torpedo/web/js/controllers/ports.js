

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
					// array response.
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
							px.receivePackets = port.receivePackets;
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
								px.status = 'UP'
								if ( p.config.indexOf("DOWN") >= 0 || p.state.indexOf("DOWN") >=0 )
									px.status = 'DOWN';
								px.status += ' ';
								px.status += p.currentFeatures.replace(/(PF_|\s+)/gi, "")
								                              .replace(/_/," ")
								                              .replace(/\[\]/,"");
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
