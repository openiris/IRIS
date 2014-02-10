
 
irisApp.controller('CntlControllerStatus', 
		['$scope', '$http', '$timeout',
		 function($scope, $http, $timeout) {
			$scope.getData = function() {
				$http.get('/wm/core/health/json')
				.success(function(data) {
					$scope.stat = data;
				})
				.error(function(){
					$scope.stat = {
							'host':'unknown',
							'ofport':'0',
							'healthy':'false',
							'uptime':'0',
							'free':'0',
							'total':'0',
							'moduleText':'Not available'
					};
				});
			};
			
			$scope.intervalFunction = function() {
				$timeout(function(){
					$scope.getData();
					$scope.intervalFunction();
				}, 3000);
			};
			
			$scope.getData();
			$scope.intervalFunction();
		}]
);