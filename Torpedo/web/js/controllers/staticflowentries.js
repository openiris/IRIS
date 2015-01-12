irisApp.controller(
  'CntlStaticFlowEntries',
  ['$scope', '$http', '$timeout',
    function($scope, $http, $timeout) {
      $scope.staticflowentries = [];
      $scope.getData = function() {
        $http.get('/wm/staticflowentry/list/all/json')
        .success(function(data){
          _.each(data, function(entry){
            $scope.staticflowentries = [];
            $scope.staticflowentries.push(entry);
          });
        })
        .error(function(){
          $scope.staticflowentries = [];
        });

        if ($scope.goon != false) {
          $timeout($scope.getData, 1000);
        }
      };

      $scope.getData();
    }]
);
