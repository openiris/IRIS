
var hackBase = ""; // put a URL here to access a different REST server

var iris = {
	getURLParam: function(strParamName) {
		var strReturn = "";
		var strHref = window.location.href;
		var bFound = false;
		
		var cmpstring = strParamName + "=";
		var cmplen = cmpstring.length;
		
		if ( strHref.indexOf("?") > -1 ) {
			var strQueryString = strHref.substr( strHref.indexOf("?") + 1 );
			var aQueryString = strQueryString.split("&");
			for ( var iParam = 0; iParam < aQueryString.length; iParam++ ) {
				if ( aQueryString[iParam].substr(0, cmplen) == cmpstring ) {
					var aParam = aQueryString[iParam].split("=");
					strReturn = aParam[1];
					bFound = true;
					break;
				}
			}
		}
		
		if ( bFound == false ) {
			return null;
		}
		return strReturn;
	},
	getVersionString: function(href) {
		var prefix = '&';
		if ( href.lastIndexOf("html") == href.length - 4 ) {
			prefix = '?';
		}
		// this is to prevent page caching that cause unwanted behavior.
		return prefix + "version=" + new Date().getTime();
	},
	modifyHrefWithVersion: function(href) {
		return href + iris.getVersionString(href);
	},
	collectionTo: function(collection, target, pagenate) {
		if ( ! target.is('tbody') ) {
			return;
		}
		
		target.empty();
		
		if ( ! pagenate ) {
			pagenate = collection.models.length;
		}
		
		var loc = 0;
		_.each(collection.models, function(model) { 
			// this is text.
			var obj = _.template(iris.tpl[target.attr('template')], model.toJSON());
			// to attach attribute, we convert it to jquery object.
			var obj_j = $(obj).attr('page', Math.floor(loc++ / pagenate));
			target.append( obj_j );
		});
	},
	ajaxTo: function(loc, target, defaults, translates) {
		if ( !translates ) {
			translates = function(data) {
				return data;
			}
		}
		
		if ( target.is('tbody') ) {
			// clear all child elements of this table.
			target.empty();
			// 'defaults' is ignored
			$.ajax({
				url: loc,
				dataType: 'json',
				success: function( resp ) {
					for ( item in resp ) {
						target.append(
							_.template(iris.tpl[target.attr('template')], 
							translates(resp[item]))
						);
					}
				},
				error: function( req, status, err ) {
					// does nothing
				}
			});
		} else {
			$.ajax({
				url: loc,
				dataType: 'json',
				defaults: defaults,
				success: function( resp ) {
					target.html( function(index, oldHtml) {
						return _.template(iris.tpl[target.attr('template')], resp);
					});
				},
				error: function(req, status, err) {
					target.html( function(index, oldHtml) {
						return _.template(iris.tpl[target.attr('template')], defaults);
					});
				}
			});
		}
	},
	classCallbacks: {},
	blockCallbacks: {},
	/*
	 * block is the page block that the function 'name' is located.
	 */
	addClassCallback: function(blockArray, name, func) {
		iris.classCallbacks[name] = func;
		for ( var i = 0; i < blockArray.length; ++i ) {
			if ( ! iris.blockCallbacks[blockArray[i]] )  {
				iris.blockCallbacks[blockArray[i]] = [];
			}
			iris.blockCallbacks[blockArray[i]].push(func);
		}
	},
	tpl: {},
	loadTemplates: function(names) {
		
//		console.log(names);
		
		for ( var i = 0; i < names.length; ++i ) {
			
			// load template for the name
			$.ajax({
				type: "GET",
				url: '/tpl/' + names[i] + '.html',
				async: false,
				success: function(data) {
					iris.tpl[names[i]] = data;
				}
			});

			// first, find div's with that class assigned
			var div = $('div.' + names[i])
			if ( div.length <= 0 ) {
				// if not found, we just return.
				// it means, we just load the template and satisfy.
				continue;
			}
			
			// next, we retrieve all classnames prefixed to the name (block names)
			var cls = div.attr('class').split(' ');
			// and we call addClassCallback 
	        iris.addClassCallback( cls.slice(0, cls.length-1), names[i], iris[names[i]] );
		}
	},
	timers: {},
	doTimers: function(blockList, name, interval, f) {
		if ( iris.timers[name] ) {
			// if there is a timer that is already defined
			return;
		}
		var iid = setInterval(f, interval);
		iris.timers[name] = {
			intervalId: iid,
			blockNames: blockList
		};
	},
	cancelAllTimers: function() {
		for ( i in iris.timers ) {
			clearInterval( iris.timers[i].intervalId );
			delete iris.timers[i];
		}
	},
	cancelAllTimersNotInBlock: function(block) {
		for ( i in iris.timers ) {
			var remove = true;
			for ( var j = 0; j < iris.timers[i].blockNames.length; ++j ) {
				if ( iris.timers[i].blockNames[j] == block ) {
					remove = false;
					break;
				}
			}
			if ( remove ) {
//				console.log('clearing ' + iris.timers[i].intervalId);
				clearInterval( iris.timers[i].intervalId );
				delete iris.timers[i];
			}
		}
	}
}

$(document).ready( function() {
	//load all templates to be used in this IRIS page.
	iris.loadTemplates([
	    // normal templates which are associated with class callbacks
		'controller_statistics', 'switches', 'devices', 'topology',
		// special templates not associated with class callbacks
		'switch_flows', 'ports', 'switch_desc'
	]);
	
	// assign handlers to menu buttons
	$('.nav a').click( function() {
		var cl = $(this).attr('class');
		// the class assigned to body represents the block (role)
		// of the current page.
		$('body').removeClass();
		$('body').addClass(cl);
		// find block handlers
		var cs = iris.blockCallbacks[ cl ];
		for ( c in cs ) {
//			console.log(cs[c]);
			cs[c] ();
		}
		// show div's belongs to the current block
		$('div.content > div.' + cl).show();
		// and hide others
		$('div.content > div:not(.'+cl+')').hide();
	});
	
	// assign handler to the reload icon next to each block name (h1)
	$.each( $('div h1+a'), function(index, value) {
		var cl = $(this).parent().attr('class');
		var cls = cl.split(' ');
		$(this).click( function() {
			iris.classCallbacks[cls[cls.length-1]] ();
		});
	});
	
	// emulate that the first button is pressed when a page is loaded.
	// therefore, after reload, always the 'Home' button is enabled.
	$('.nav a')[0].click();
});