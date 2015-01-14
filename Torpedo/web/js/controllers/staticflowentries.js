irisApp.controller(
  'CntlStaticFlowEntryManager',
  ['$scope', '$http', '$timeout',
    function($scope, $http, $timeout) {
      $scope.switch_dpids = [];
      $scope.rules = [];

      $scope.form = {};
      $scope.entry = {};

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
        if (typeof(form.instructions) == 'string') {
          form.instructions = JSON.parse(form.instructions);
        }
        $http.post('/wm/staticflowentry/json', form)
        .success(function(data) {
          $scope.form = {};
        });
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

      $scope.editField = function(field) {
        $scope.entry.field = field;
        if (typeof($scope.form[field]) == 'object') {
          $scope.entry.value = JSON.stringify($scope.form[field]);
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
        return $scope.form.instructions[parseInt(index)];
      };

      $scope.addInstruction = function() {
        $scope.form.instructions.push({});
      };

      $scope.removeInstruction = function(index) {
        $scope.form.instructions.splice(index, 1);
      };

      $scope.getActions = function(index) {
        var instruction = $scope.getInstruction(index);
        $scope.actions = {};

        for (var table in instruction) {
          if (_.contains(['apply_actions', 'write_actions'], table)) {
            for (var index in instruction[table]) {
              var entry = instruction[table][index];
              for (var actionName in entry) {
                $scope.actions[table + "|" + index] = table + ' - ' + actionName + ': ' + entry[actionName];
              }
            }
          } else if (table == 'clear_actions') {
            $scope.actions['clear_actions'] = 'clear_actions';
          } else if (table == 'goto_table') {
            // TODO
          }
        }

        return $scope.actions;
      };

      $scope.removeAction = function(instIndex, actionKey) {
        var instuction = $scope.getInstruction(instIndex);
        var _params = actionKey.split('|');
        var table = _params[0];
        var index = _params[1];

        if (_.contains(['apply_actions', 'write_actions'], table)) {
          instuction[table].splice(index, 1);
        } else {
          delete instuction[table];
        }
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
