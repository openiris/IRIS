

iris.topologyWidth = 1160;
iris.topologyHeight = 700;

var switchStats = {};

iris.getSwitches = function() {
	$.get("/wm/core/controller/switches/json")
	.success(function(data) {
		_.each(data, function(obj) {
			var dpid = obj.dpid;
			switchStats[dpid] = {};
			iris.getStatistics(dpid);
		});

		iris.getLinks();
		iris.getDevices();
	});
}

iris.getLinks = function() {
	$.get("/wm/topology/links/json")
	.success(function(data) {
		_.each(data, function(link) {
			var srcDpid = link['src-switch'];
			var port = link['src-port'];
			var dstDpid = link['dst-switch'];

			var sw = switchStats[srcDpid];

			if (sw[port] == undefined) {
				sw[port] = {};
			}
			sw[port].id = dstDpid;
		});
	});
}

iris.getDevices = function() {
	$.get("/wm/device/all/json")
	.success(function(data) {
		_.each(data, function(host) {
			var id = host.mac[0];
			_.each(host.attachmentPoint, function(point) {
				var dpid = point.switchDPID;
				var port = point.port;

				var sw = switchStats[dpid];

				if (sw[port] == undefined) {
					sw[port] = {};
				}
				sw[port].id = id;
			});
		});
	});
}

iris.getStatistics = function(dpid) {
	$.get("/wm/core/switch/" + dpid + "/port/json")
	.success(function(data) {
		var stats = data[dpid];
		var sw = switchStats[dpid];

		_.each(stats, function(stat) {
			var port = stat.portNumber;
			var tx = stat.transmitBytes;
			var rx = stat.receiveBytes;

			try {
			  if (sw[port] == undefined) {
			    sw[port] = {};
			  }

			  sw[port].lastLoad = sw[port].load;
			  sw[port].load = rx + tx;
			} catch(e) {}
		});
	});
}

iris.refreshStatistics = function() {
	_.each(switches, function(sw) {
		var dpid = sw.id;
		iris.getStatistics(dpid);
	});
};

iris.getLinkLoad = function(src, dst) {
	var sw;
	var target;

	if (src.id in switchStats) {
		sw = switchStats[src.id];
		target = dst;
	} else {
		sw = switchStats[dst.id];
		target = src;
	}

	for (var port in sw) {
		if (sw[port].id == target.id) {
			if (sw[port].lastLoad != undefined) {
				return sw[port].load - sw[port].lastLoad;
			} else {
				return 0;
			}
		}
	}

	return 0;
}


iris.minimumDistances = function(nodes, links) {
	var arr = [];

	for ( var i in nodes ) {
		arr[nodes[i].id] = [];
		arr[nodes[i].id][nodes[i].id] = 0;
	}

	for ( var i in links ) {
		arr[ links[i].source.id ][ links[i].target.id ] = 1;
		arr[ links[i].target.id ][ links[i].source.id ] = 1;
	}

	var sum = function(a, b) {
		if ( a == undefined || b == undefined ) {
			return undefined;
		}
		return a + b;
	}

	var min = function(a, b) {
		if ( a == undefined ) {
			return b;
		}
		if ( b == undefined ) {
			return a;
		}
		return (a <= b)? a : b;
	}

	// we now run the Floyd-Warshall algorithm.
	for ( var k = 0; k < nodes.length; ++k ) {
		for ( var i = 0; i < nodes.length; ++i ) {
			for ( var j = 0; j < nodes.length; ++j ) {
				arr[nodes[i].id][nodes[j].id] = 
					min( 
							arr[nodes[i].id][nodes[j].id], 
							sum(
									arr[nodes[i].id][nodes[k].id], 
									arr[nodes[k].id][nodes[j].id]
							) 
					);
			}
		}
	}


	return arr;
};

//smaller value returned, that is the node probably close to center.
var centerEstimate = function(wTable, node) {
	var max = 0;
	var arr = wTable[node.id];

	for ( i in arr ) {
		if ( arr[i] > max ) {
			max = arr[i];
		}
	}

	return max;
};

var networkWidth = function(wTable) {
	var max = 0;
	for ( i in wTable ) {
		for ( j in wTable[i] ) {
			if ( wTable[i][j] > max ) {
				max = wTable[i][j];
			}
		}
	}
	return max;
}

var nDistance = function(node) {
	return 250 / node.value;
};

var distance = function(link) {
	var x = d3.min( [link.source.value, link.target.value] );
	return 150 / x;
};

iris.sortNodesByValue = function(nodes) {
	return _.sortBy(nodes, function(node) {
		return node.value;
	});
};

var findParentNode = function(node, links) {

	for ( l in links ) {
		if ( links[l].source == node ) {
			if ( links[l].target.value < node.value ) {
				return links[l].target;
			}
		} else if ( links[l].target == node ) {
			if ( links[l].source.value < node.value ) {
				return links[l].source;
			}
		}
	}

	// there's no parent. you are all alone.
	return node;
};

iris.adjustXandY = function(nodes, links) {
	var ns = iris.sortNodesByValue(nodes);

	// relative degree to parent.
	var degree = [];

	for ( n in ns ) {
		var parent = findParentNode(ns[n], links);
		if ( parent != ns[n] ) {
			if ( degree[parent.id] == undefined ) {
				degree[parent.id] = parent.degree - 180 + 360/parent.weight;
			}
			else {
				degree[parent.id] += 360/parent.weight;
			};

			ns[n].degree = degree[parent.id];

//			console.log(parent.id);
//			console.log('pdeg : ' + parent.degree);
//			console.log('pwei : ' + parent.weight);
//			console.log(ns[n].id);
//			console.log('('+parent.id+','+parent.degree+','+parent.weight+') -- (' + ns[n].id + "," + ns[n].degree + ')');

			var r = nDistance(parent);
			var rad = degree[parent.id]*Math.PI/180;
			var dx = r * Math.cos(rad);
			var dy = r * Math.sin(rad);
			ns[n].x = parent.x + dx;
			ns[n].y = parent.y + dy;

//			console.log('p(' + parent.x + ',' + parent.y + ')');
//			console.log('n(' + ns[n].x + ',' + ns[n].y + ')');
		}
		else {
			parent.degree = -45;
		}
	}
};

iris.topologyPrepare = function() {

	var oldScale = 1;

	function zoom() {

		// JUST DO NOT MANIPULATE translate array directly.
		var x = d3.event.translate[0] * 10;
		var y = d3.event.translate[1] * 10;

		if ( d3.event.scale == oldScale ) {
			svg
			.attr("transform", "translate(" +[x, y] + ")scale(" + d3.event.scale + ")");
		} else {
			svg.transition()
			.ease('linear')
			.attr("transform", "translate(" +[x, y] + ")scale(" + d3.event.scale + ")");
		}

		oldScale = d3.event.scale;
	}

	// remove the previously attached svg tag
	d3.select('div[template="topology"]').selectAll('svg').remove();

	var svg = d3.select('div[template="topology"]')
	.append('svg')
	.style('position', 'absolute')
	.style('top', 0)
	.style('left', 0)
//	.style('background-color', "#effeff")
	.attr("width", iris.topologyWidth)
	.attr("height", iris.topologyHeight)
	.append('g');

	iris.topologyLink = svg.append("g")
	.attr("class", "link")
	.selectAll("line");

	iris.topologyBrush = svg.append("g")
	.datum(function() { return {selected: false, previouslySelected: false}; })
	.attr("class", "brush");

	iris.topologyNode = svg.append("g")
	.attr("class", "node")
	.selectAll("g");

	var svg2 = d3.select('div[template="topology"]')
	.append('svg')
	.style('position', 'absolute')
	.style('left', (iris.topologyWidth/10)*9-10)
	.style('top', (iris.topologyHeight/10)*9-10)
	.append("g");
//	.attr('x', (iris.topologyWidth/10)*9-10)
//	.attr('y', (iris.topologyHeight/10)*9-5)
//	.attr('width', (iris.topologyWidth/10))
//	.attr('height', (iris.topologyHeight/10));

	svg2
	.call( d3.behavior.zoom().scaleExtent([1, 8]).on("zoom", zoom) );

	svg2
	.append('text')
	.attr('x', 5)
	.attr('y', 15)
	.text('touchpad');

	svg2
	.append('rect')				// touchpad
	.attr('class', 'touchpad')
//	.attr('fill', 'rgb(21,177,226)')
//	.attr('x', (iris.topologyWidth/10)*9-10)
//	.attr('y', (iris.topologyHeight/10)*9-5)
	.attr('width', (iris.topologyWidth/10))
	.attr('height', (iris.topologyHeight/10));



//	console.log($('#topology svg g.node > g').length);
};

iris.topology = function(nodes, hosts, links) {

	iris.topologyPrepare();

	var circleSizeScale = d3.scale.linear().domain([0, 32]).range([10, 42]);

	var shiftKey;
	var ctrlKey;

	nodes.forEach(function (d) {
		// random enables the fast loading of the topology graph because
		// node positions are probably not colliding. 
		d.x = iris.topologyWidth/2;	
		d.y = iris.topologyHeight/2;
		d.px = iris.topologyWidth/2;
		d.py = iris.topologyHeight/2;
		d.fixed = false;
		d.weight = 0;
		d.id = d.name;
		d.group = 1;		/* switch */
	});

//	console.log(iris.topologyCollection.nodes);

	hosts.forEach(function (d) {
		// random enables the fast loading of the topology graph because
		// node positions are probably not colliding. 
		d.x = iris.topologyWidth/2;	
		d.y = iris.topologyHeight/2;
		d.px = iris.topologyWidth/2;
		d.py = iris.topologyHeight/2;
		d.fixed = false;
		d.weight = 0;
		if (d['ipv4'].length > 0) {
			d.name = d['ipv4'][0] + "(" + d.id + ")";
		} else {
			d.name = d.id;
		}
		d.group = 2;		/* host */
	});

	nodes = nodes.concat(hosts);

//	console.log(nodes.length);
//	console.log(nodes);

	var nodes_map = [];

	_.each(nodes, function(n) {
		nodes_map[n.id] = n;
	});

	links.forEach(function (d) {
		d.source = nodes[d.source];
		d.source.weight++;
		d.target = nodes[d.target];
	});

	var host_links = [];

	for (var i = 0; i < hosts.length; i++) {
		var host = hosts[i];

		//for (var j = 0; j < host.attributes['attachmentPoint'].length; j++) {
		for (var j = 0; j < 1; j++) { // FIXME hack to ignore multiple APs
			var link = {
					source:nodes_map[host.id],
					target:nodes_map[host['attachmentPoint'][j]['switchDPID']],
					value:10
			};
//			console.log(j);
//			console.log(host.attributes['attachmentPoint']);
//			console.log(nodes_map);
			link.source.weight++;
			link.target.weight++;

			//console.log(link);
			if ( link.source && link.target) {
				host_links.push(link);
			} else {
				console.log("Error: skipping link with undefined stuff!")
			}
		}
	}

	links = links.concat(host_links);

	// we need to calculate how close every node to the network edge
	// at this point, and assign the value to each node. 
	// by the value, we can calculate link distance correctly,
	// charge value correctly, and link strength correctly - bjlee

	// returned table is 2-dimensional matrix that contains the minimal
	// distances between each node pair.
	var wTable = iris.minimumDistances(nodes, links);
	var networkSize = networkWidth(wTable);

	var nodeValueScale = d3.scale.linear().range([1, networkSize+1]);

	// assign center estimation value to each node.
	nodes.forEach(function (d) {
		d.value =  centerEstimate(wTable, d) ;
	});

	// using the estimated value, we adjust the X and Y location of each node.
	// (this will reduce the latency that the initial topology graph is drawn.
	iris.adjustXandY(nodes, links);

	var force = d3.layout.force();

	force
	.linkDistance( function(l) {
		return distance(l);
	})
	.charge(function(node, index) {
		// charge negatively increases when its far from network center.
		return -150 * node.value;
	}) 
	.friction(0.9)
	.theta(0.1)
	.gravity(0.1)
	.size([iris.topologyWidth, iris.topologyHeight])
	.nodes(nodes).links(links)
	.start();


	var node = iris.topologyNode.data(nodes).enter().append("g").attr('class', 'node');
	node.append("text")
//	.attr("transform", function(d) {return "translate(" + d + ")"; })
	.attr("x", function(d) { return d.x+10; })
	.attr("y", function(d) { return d.y-3; })
	.text( function(d) { return d.name; } );
	node.append("ellipse")
	.attr("rx", function(d) { return circleSizeScale(networkSize-d.value)*0.7; })
	.attr("ry", function(d) { return circleSizeScale(networkSize-d.value)*0.55})
//	.attr("transform", function(d) {return "translate(" + d + ")"; })
	.attr("cx", function(d) { return d.x; })
	.attr("cy", function(d) { return d.y; });
	node.append("image")
//	.attr("transform", function(d) {return "translate(" + d + ")"; })
	.attr("xlink:href", function (d) {return d.group==1 ? "/img/switch.png" : "/img/desktop.png"})
	.attr("x", function(d){return d.x - circleSizeScale(networkSize-d.value)+1;})
	.attr("y", function(d) { return d.y - circleSizeScale(networkSize-d.value)-3;})
	.attr("width", function(d) { return circleSizeScale(networkSize-d.value)*2-2;})
	.attr("height", function(d) { return circleSizeScale(networkSize-d.value)*2-2;});

	var link = iris.topologyLink.data(links).enter().append("line")
	.attr('class', 'line')
	.attr("x1", function(d) { return d.source.x; })
	.attr("y1", function(d) { return d.source.y; })
	.attr("x2", function(d) { return d.target.x; })
	.attr("y2", function(d) { return d.target.y; });

	setInterval(function() {
		link.style('stroke-width', function(l) {
			var MIN_THRESHOLD = 200; // Byte per second
			var load = iris.getLinkLoad(l.source, l.target);
			if (load != 0) {
				return Math.max(Math.log10(load - MIN_THRESHOLD), 1);
			} else {
				return 1;
			}
		});
	}, 1000);

	node.on("mousedown", function(d) {
		if (ctrlKey) {
			d.selected = false;
			d.fixed = false;
		} else {
			d.fixed = true;
			if (!d.selected) { // Don't deselect on shift-drag.
				if (!shiftKey) {
					node.classed("selected", function(p) { 
						return p.selected = d === p; 
					});
				} else {
					console.log("shift click");
					d3.select(this).classed("selected", d.selected = true);
				}
			}
		}
	})
	// USELESS!! -- bjlee
//	.on("mouseup", function(d) {
//	if (d.selected && shiftKey ) 
//	d3.select(this).classed("selected", d.selected = false);
//	})
	.call(d3.behavior.drag().on("drag", function(d) { 
		nudge(d3.event.dx, d3.event.dy); 
	}));

	d3.selectAll('[template=topology] .node .node').call(force.drag);

	iris.topologyBrush.call(d3.svg.brush()
			.x(d3.scale.identity().domain([0, iris.topologyWidth]))
			.y(d3.scale.identity().domain([0, iris.topologyHeight]))
			.on("brushstart", function(d) {
				node.each( function(d) { d.previouslySelected = shiftKey && d.selected; });
			})
			.on("brush", function() {
				var extent = d3.event.target.extent();
				node.classed("selected", function(d) {
					return d.selected = d.previouslySelected ^
					(extent[0][0] <= d.x && d.x < extent[1][0]
					&& extent[0][1] <= d.y && d.y < extent[1][1]);
				});
			})
			.on("brushend", function() {
				d3.event.target.clear();
				d3.select(this).call(d3.event.target);
			}));    


	force.on("tick", function() {
		link.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });
//		node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
		node.selectAll('ellipse')
		.attr('cx', function(d) { return d.x; })
		.attr('cy', function(d) { return d.y; });
		node.selectAll('text')
		.attr('x', function(d) { return d.x+10; })
		.attr('y', function(d) { return d.y-3; });
		node.selectAll('image')
		.attr('x', function(d) { return d.x-circleSizeScale(networkSize-d.value)+1; })
		.attr('y', function(d) { return d.y-circleSizeScale(networkSize-d.value)-3; });
	});  

	var nudge = function(dx, dy) {
		var n = node.filter(function(d) { return d.selected; });

		n.select('ellipse')
		.attr("cx", function(d) { return d.x += dx; })
		.attr("cy", function(d) { return d.y += dy; });

		n.select('text')
		.attr("x", function(d) { return d.x + 10; })
		.attr("y", function(d) { return d.y -3; });

		n.select('image')
		.attr('x', function(d) { return d.x-circleSizeScale(networkSize-d.value)+1; })
		.attr('y', function(d) { return d.y-circleSizeScale(networkSize-d.value)-3; });

		link.filter(function(d) { return d.source.selected; })
		.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; });

		link.filter(function(d) { return d.target.selected; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });

//		d3.event.preventDefault();
	}

	var keydown = function() {
		if (!d3.event.metaKey) {
			switch (d3.event.keyCode) {
			case 38: nudge( 0, -1); break; // UP
			case 40: nudge( 0, +1); break; // DOWN
			case 37: nudge(-1,  0); break; // LEFT
			case 39: nudge(+1,  0); break; // RIGHT
			}
		}
		shiftKey = d3.event.shiftKey || d3.event.metaKey;
		ctrlKey = d3.event.ctrlKey;
		//		d3.event.preventDefault();
	}

	var keyup = function() {
		shiftKey = d3.event.shiftKey || d3.event.metaKey;
		ctrlKey = d3.event.ctrlKey;
	}

	// 'body' is important to make these handers called correctly.
	d3.select("body")		
	.on("keydown.brush", keydown)
	.on("keyup.brush", keyup);
};


// topology controller definition.
irisApp.controller('CntlTopology', 
		['$scope', '$http', '$timeout', '$iris',
		 function($scope, $http, $timeout, $iris) {

			// define getData method
			$scope.render = function() {
				$http.get('/wm/topology/links/json')
				.success(function(data) {
					var switches = $iris.getSwitches();
					var ids = _.pluck(switches, 'id');
					
					// initialize
					$scope.nodes = _.map(ids, function (n) {return {name:n}});
					$scope.links = [];
					$scope.hosts = [];
					
					_.each(data, function (link) {
						$scope.links.push({
							source:ids.indexOf(link['src-switch']),
							target:ids.indexOf(link['dst-switch']),
							value:10
						});
					});
					$scope.hosts.push.apply($scope.hosts, $iris.getDevices());

					iris.topology($scope.nodes, $scope.hosts, $scope.links);
				})
				.error(function() {
					$scope.nodes = [];
					$scope.links = [];
					$scope.hosts = [];
					iris.topology([], [], []);
				});
				iris.getSwitches();
			};

			// Get statistics
			setInterval(iris.refreshStatistics, 1000);

			angular.element('div.content > div.topology').bind('displayed', function() {
				$scope.render();
			});
		}]
);
