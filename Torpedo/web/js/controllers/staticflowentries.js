irisApp.controller(
  'CntlStaticFlowEntryManager',
  ['$scope', '$http', '$timeout',
    function($scope, $http, $timeout) {
      $scope.switch_dpids = [];
      $scope.rules = [];

      $scope.form = {};

      $scope.entryFields = [
        "table_id",
        "idle_timeout",
        "hard_timeout",
        "actions",
        "instructions",
        "in_port",
        "eth_dst",
        "eth_src",
        "vlan_vid",
        "vlan_pcp",
        "eth_type",
        "ip_proto",
        "ipv4_src",
        "ipv4_dst",
        "ip_dscp",
        "ip_ecn",
        "tcp_src",
        "tcp_dst",
        "udp_src",
        "udp_dst",
        "sctp_src",
        "sctp_dst",
        "icmpv4_type",
        "icmpv4_code",
        "arp_op",
        "arp_spa",
        "arp_tpa",
        "arp_sha",
        "arp_tha",
        "mpls_label",
        "mpls_tc",
      ];

      $scope.addEntry = function() {
        var form = $scope.form;
        form.active = true;
        $http.post('/wm/staticflowentry/json', form)
        .success(function(data) {
          console.log(data);
        });
        // TODO
      };

      $scope.deleteEntry = function() {
        var name = $scope.form.name;
        $http.delete('/wm/staticflowentry/delete/' + name + '/json');
        // TODO
      };

      $scope.clearEntry = function() {
        var switch_ = $scope.form['switch'];
        $http.get('/wm/staticflowentry/clear/' + switch_ + '/json');
      };

      $scope.clearAll = function() {
        $http.get('/wm/staticflowentry/clear/all/json')
        .success(function(data) { console.log(data); });
      };

      $scope.autofill = function(entry) {
        $scope.form = angular.copy(entry);
        $scope.form.priority = parseInt(entry.priority);
      };

      $scope.toggleActive = function(entry) {
        entry.active = entry.active.toLowerCase() == "true" ? "false" : "true";
        $http.post('/wm/staticflowentry/json', entry);
      }

      $scope.addField = function() {
        if (! $scope.entry.field || ! $scope.entry.value) {
          return;
        }
        $scope.form[$scope.entry.field] = $scope.entry.value;
      };

      $scope.removeField = function(field) {
        delete $scope.form[field];
      };

      $scope.additionalFields = function() {
        var result = {};

        angular.forEach($scope.form, function(value, key) {
          if (_.contains($scope.entryFields, key)) {
            result[key] = value;
          }
        });

        return result;
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
