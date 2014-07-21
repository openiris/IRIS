

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
						
						for ( var k in f.match ) {
							var field_name = k.replace("_", "");
							if ( field_name == 'ipproto' ) {
								// we need some conversion to display it decimal format.
								f.match[k] = parseInt(f.match[k], 16);
							}
							f.matchHTML += field_name + "=" + f.match[k] + ", "
						}
												
						// remove trailing ", "
						f.matchHTML = f.matchHTML.substr(0, f.matchHTML.length - 2);
						
						if ( f.priority == 0 && f.matchHTML == '' ) {
							f.matchHTML = 'table miss record';
						}
						
						var actions;
						if ( f.actions ) {
							actions = f.actions;
						} else {
							// this is for version 1.3 >= 
							actions = _.reduce(f.instructions, function(arr, a) {
								return arr.concat(a.actions);
							}, []);
						}
						
						f.actionText = _.reduce(actions, function (memo, a) {
							switch (a.type) {
							case "OUTPUT":
								var maps = {"-8":"IN_PORT","-7":"TABLE","-6":"NORMAL","-5":"FLOOD","-4":"ALL","-3":"CONTROLLER","-2":"LOCAL"};
								var tok = maps[a.port];
								
								if ( tok != undefined ) {
									return memo + "output " + tok + ", ";
								} else {
									return memo + "output " + a.port + ", ";
								}
							case "ENQUEUE":
							case "SET_QUEUE":
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
							case "SET_FIELD":
								var ret = memo;
								for ( key in a.field ) {
									ret += key + ":" + a.field[key] + ', ';
								}
								//remove trailing ", "
								ret = ret.substr(0, ret.length - 2);
								return (ret += ", ");
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
