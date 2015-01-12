irisApp.controller(
  'CntlStaticFlowEntryManager',
  ['$scope', '$http', '$timeout',
    function($scope, $http, $timeout) {
      $scope.switch_dpids = [];
      $scope.rules = [];

      $scope.addEntry = function() {
        var form = $scope.form;
        form.active = true;
        $http.post('/wm/staticflowentry/json', form);
        // TODO
      };

      $scope.deleteEntry = function() {
        var name = $scope.form.name;
        $http.delete('/wm/staticflowentry/delete/' + name + '/json');
        // TODO
      };

      $scope.autofill = function(entry) {
        $scope.form = angular.copy(entry);
      };

      $scope.getData = function() {
        // Get static flow entries.
        $http.get('/wm/staticflowentry/list/all/json')
        .success(function(data){
          $scope.rules = [];
          _.each(data, function(entry) {
            $scope.rules.push(entry);
          });
        })
        .error(function(){
          $scope.rules = [];
        });

        // Get switches.
        $http.get('/wm/core/controller/switches/json')
        .success(function(data) {
          $scope.switch_dpids = [];
          _.each(data, function(sw) {
            $scope.switch_dpids.push(sw.dpid);
          });
        })
        .error(function(){
          $scope.switch_dpids = [];
        });

        // Loop.
        if ($scope.goon != false) {
          $timeout($scope.getData, 1000);
        }
      };

      $scope.getData();
    }]
);
