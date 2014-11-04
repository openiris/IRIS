

irisApp.controller('CntlVSubnetDetail', 
		['$scope', '$http', '$timeout', 
		 function($scope, $http, $timeout) {
			// initial data
			$scope.vSubnetDetail = {}
			
			// define getData method.
			$scope.getData = function() {
				// scope.id is set externally.
				$http.get('/wm/ml2/subnets/' + $scope.id)
				.success(function(data) {
					$scope.vSubnetDetail = [];
					_.each(data, function(vSubnetDetail) {
						$scope.vSubnetDetail = vSubnetDetail;
					});
				})
				.error(function(){
					$scope.vSubnetDetail = {};
				});
			};
			
			// fire once.
			$scope.getData();
		}]
);
