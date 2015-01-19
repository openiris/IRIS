irisApp.controller(
  'CntlStaticFlowEntryManager',
  ['$scope', '$http', '$timeout',
    function($scope, $http, $timeout) {
      $scope.switch_dpids = [];
      $scope.rules = [];

      $scope.form = {};
      $scope.entry = {};

      $scope.entryFields = [
        "eth_type",
        "actions",
        "in_port",
        "eth_dst",
        "eth_src",
        "vlan_vid",
        "vlan_pcp",
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

      $scope.instructionActions = [
        "output",
        "set_vlan_vid",
        "set_vlan_pcp",
        "strip_vlan",
        "set_dl_src",
        "set_dl_dst",
        "set_nw_src",
        "set_nw_dst",
        "set_nw_tos",
        "set_tp_src",
        "set_tp_dst",
        "enqueue",
        "copy_ttl_out",
        "copy_ttl_in",
        "set_mpls_ttl",
        "dec_mpls_ttl",
        "push_vlan",
        "pop_vlan",
        "push_mpls",
        "pop_mpls",
        "set_queue",
        "group",
        "set_nw_ttl",
        "dec_nw_ttl",
        "set_field",
        "push_pbb",
        "pop_pbb",
      ];

      $scope.addEntry = function() {
        var form = $scope.form;

        if (form.active === undefined) {
          form.active = true;
        }
        if (form.priority === undefined) {
          form.priority = 100;
        }

        $http.post('/wm/staticflowentry/json', form)
        .success(function(data) {
          $scope.result = data.result;
        });
      };

      $scope.deleteEntry = function() {
        var name = $scope.form.name;
        $http.delete('/wm/staticflowentry/delete/' + name + '/json')
        .success(function(data) {
          $scope.result = data.result;
          $scope.form = {};
        });
      };

      $scope.clearEntry = function() {
        var switch_ = $scope.form['switch'];
        $http.get('/wm/staticflowentry/clear/' + switch_ + '/json');
      };

      $scope.clearAll = function() {
        $http.get('/wm/staticflowentry/clear/all/json')
        .success(function(data) {
          $scope.result = data.result;
        });
      };

      $scope.autofill = function(entry) {
        $scope.form = angular.copy(entry);
        if (entry.priority) {
          $scope.form.priority = parseInt(entry.priority);
        }

        if (entry.active !== undefined) {
          $scope.form.active = JSON.parse(entry.active);
        }
      };

      $scope.toggleActive = function(entry) {
        entry.active = entry.active.toLowerCase() == "true" ? "false" : "true";
        $http.post('/wm/staticflowentry/json', entry);
      };

      $scope.addField = function() {
        if (! $scope.entry.field || ! $scope.entry.value) {
          return;
        }
        $scope.form[$scope.entry.field] = $scope.entry.value;
        $scope.entry.value = "";
      };

      $scope.removeField = function(field) {
        delete $scope.form[field];
      };

      $scope.editField = function(field) {
        $scope.entry.field = field;
        if (typeof($scope.form[field]) == 'object') {
          $scope.entry.value = angular.toJson($scope.form[field]);
        } else {
          $scope.entry.value = $scope.form[field];
        }
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

      $scope.getInstruction = function(index) {
        try {
          return $scope.form.instructions[parseInt(index)];
        } catch(error) {
          return;
        }
      };

      $scope.addInstruction = function() {
        if ($scope.form.instructions === undefined) {
          $scope.form.instructions = [];
        }
        $scope.form.instructions.push({});
      };

      $scope.removeInstruction = function(index) {
        $scope.form.instructions.splice(index, 1);
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
        if ($scope.goon !== false) {
          $timeout($scope.getData, 1000);
        }
      };

      $scope.getMatches = function(rule) {
        var matches = [];
        _.each($scope.entryFields, function(key) {
          if (rule[key] !== undefined) {
            matches.push(key + ": " + rule[key]);
          }
        });

        return matches.join(', ')
      };

      $scope.getActions = function(rule) {
        // TODO: Implement.
        return rule.instructions;
      };

      $scope.getData();
    }]
);

irisApp.filter('prettyJson', function() {
  function syntaxHighlight(json) {
    return angular.toJson(json, 2);
  }

  return syntaxHighlight;
});
