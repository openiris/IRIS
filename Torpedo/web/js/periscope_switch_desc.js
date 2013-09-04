
iris.switch_desc = function(id, target) {
	
	if ( ! id ){
		// if id is undefined, just return
		return;
	}
	
	var aSwitch = iris.switchCollection.get(id);
	
	var obj = _.template(iris.tpl['switch_desc'], aSwitch.toJSON());
	
	target.find('tbody').append($(obj));
};