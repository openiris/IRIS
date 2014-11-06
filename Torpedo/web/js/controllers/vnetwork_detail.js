

irisApp.controller('CntlVNetworkDetail', 
		['$scope', '$http', '$timeout', 
		 function($scope, $http, $timeout) {
			// initial data
			$scope.vNetworkDetail = {}
			
			// define getData method.
			$scope.getData = function() {
				// scope.id is set externally.
				$http.get('/wm/ml2/networks/' + $scope.id)
				.success(function(data) {
					$scope.vNetworkDetail = [];
					_.each(data, function(vNetworkDetail) {
						$scope.vNetworkDetail = vNetworkDetail;
					});
				})
				.error(function(){
					$scope.vNetworkDetail = {};
				});
			};
			
			// fire once.
			$scope.getData();
		}]
);
