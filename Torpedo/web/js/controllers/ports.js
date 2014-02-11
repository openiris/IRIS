

irisApp.controller('CntlPorts', 
		['$scope', '$http', '$timeout', 
		 function($scope, $http, $timeout) {
			// initialize some data internal to this controller.
			$scope.ports = [];
			
			// define getData method.
			$scope.getData = function() {
				// scope.id is set externally.
				$http.get('/wm/core/switch/'+ $scope.id +'/port/json')
				.success(function(data) {
					$scope.ports = [];
					_.each(data[$scope.id], function(port) {
						port['status'] = 'UP';						
						$scope.ports.push( port );
					});					
				})
				.error(function(){
					$scope.ports = [];
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
