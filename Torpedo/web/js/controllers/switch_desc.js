

irisApp.controller('CntlSwitchDesc', 
		['$scope', '$http', '$timeout', 
		 function($scope, $http, $timeout) {
			// initial data
			$scope.desc = {}
			
			// define getData method.
			$scope.getData = function() {
				// scope.id is set externally.
				$http.get('/wm/core/switch/'+ $scope.id +'/desc/json')
				.success(function(data) {
					$scope.desc = [];
					_.each(data[$scope.id], function(desc) {
						$scope.desc = desc;
					});					
				})
				.error(function(){
					$scope.desc = {};
				});
			};
			
			// fire once.
			$scope.getData();
		}]
);
