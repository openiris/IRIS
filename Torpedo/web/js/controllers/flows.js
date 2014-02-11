

irisApp.controller('CntlFlows', 
		['$scope', '$http', '$timeout', 
		 function($scope, $http, $timeout) {
			// initialize some data internal to this controller.
			$scope.flows = [];
			
			// define getData method.
			$scope.getData = function() {
				// scope.id is set externally.
				$http.get('/wm/core/switch/' + $scope.id + '/flow/json')
				.success(function(data) {
					$scope.flows = [];
					_.each(data[$scope.id], function(f) {
						f.matchHTML = '';
						
						if(!(f.match.wildcards & (1<<0))) { // input port
							f.matchHTML += "port=" + f.match.inputPort + ", ";
						}
						if(!(f.match.wildcards & (1<<1))) { // VLAN ID
							f.matchHTML += "VLAN=" + f.match.dataLayerVirtualLan + ", ";
						}
						if(!(f.match.wildcards & (1<<20))) { // VLAN prio
							f.matchHTML += "prio=" + f.match.dataLayerVirtualLanPriorityCodePoint  + ", ";
						}
						if(!(f.match.wildcards & (1<<2))) { // src MAC
							f.matchHTML += "src=" + f.match.dataLayerSource + ", ";
						}
						if(!(f.match.wildcards & (1<<3))) { // dest MAC
							f.matchHTML += "dest=" + f.match.dataLayerDestination + ", ";
						}
						if(!(f.match.wildcards & (1<<4))) { // Ethertype
							// TODO print a human-readable name instead of hex
							f.matchHTML += "ethertype=" + f.match.dataLayerType + ", ";
						}
						if(!(f.match.wildcards & (1<<5))) { // IP protocol
							// TODO print a human-readable name
							f.matchHTML += "proto=" + f.match.networkProtocol + ", ";
						}
						if(!(f.match.wildcards & (1<<6))) { // TCP/UDP source port
							f.matchHTML += "IP src port=" + f.match.transportSource + ", ";
						}
						if(!(f.match.wildcards & (1<<7))) { // TCP/UDP dest port
							f.matchHTML += "IP dest port=" + f.match.transportDestination  + ", ";
						}
						if(!(f.match.wildcards & (32<<8))) { // src IP
							f.matchHTML += "src=" + f.match.networkSource  + ", ";
						}
						if(!(f.match.wildcards & (32<<14))) { // dest IP
							f.matchHTML += "dest=" + f.match.networkDestination  + ", ";
						}
						if(!(f.match.wildcards & (1<<21))) { // IP TOS
							f.matchHTML += "TOS=" + f.match.networkTypeOfService  + ", ";
						}
						// remove trailing ", "
						f.matchHTML = f.matchHTML.substr(0, f.matchHTML.length - 2);
						
						f.actionText = _.reduce(f.actions, function (memo, a) {
							switch (a.type) {
							case "OUTPUT":
								return memo + "output " + a.port + ', ';
							case "OPAQUE_ENQUEUE":
								return memo + "enqueue " + a.port + ':' + a.queueId +  ', ';
							case "STRIP_VLAN":
								return memo + "strip VLAN, ";
							case "SET_VLAN_ID":
								return memo + "VLAN=" + a.virtualLanIdentifier + ', ';
							case "SET_VLAN_PCP":
								return memo + "prio=" + a.virtualLanPriorityCodePoint + ', ';
							case "SET_DL_SRC":
								return memo + "src=" + a.dataLayerAddress + ', ';
							case "SET_DL_DST":
								return memo + "dest=" + a.dataLayerAddress + ', ';
							case "SET_NW_TOS":
								return memo + "TOS=" + a.networkTypeOfService + ', ';
							case "SET_NW_SRC":
								return memo + "src=" + a.networkAddress + ', ';
							case "SET_NW_DST":
								return memo + "dest=" + a.networkAddress + ', ';
							case "SET_TP_SRC":
								return memo + "src port=" + a.transportPort + ', ';
							case "SET_TP_DST":
								return memo + "dest port=" + a.transportPort + ', ';
							}
						}, "");
						// remove trailing ", "
						f.actionText = f.actionText.substr(0, f.actionText.length - 2);
						
						$scope.flows.push(f);
					});					
				})
				.error(function(){
					$scope.flows = [];
				});
				
				// go-on flag will be externally set.
				if ( $scope.goon != false )
					$timeout(function(){
						$scope.getData();
					}, 1000);
			};
			
			// fire once.
			$scope.getData();
		}]
);
