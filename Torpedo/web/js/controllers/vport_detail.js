

irisApp.controller('CntlVPortDetail', 
		['$scope', '$http', '$timeout', 
		 function($scope, $http, $timeout) {
			// initial data
			$scope.vPortDetail = {}
			
			// define getData method.
			$scope.getData = function() {
				// scope.id is set externally.
				$http.get('/wm/ml2/ports/' + $scope.id)
				.success(function(data) {
					$scope.vPortDetail = [];
					_.each(data, function(vPortDetail) {
						$scope.vPortDetail = vPortDetail;
					});
				})
				.error(function(){
					$scope.vPortDetail = {};
				});
			};
			
			// fire once.
			$scope.getData();
		}]
);
