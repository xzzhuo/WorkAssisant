
var my_sex = new Object;

my_sex.info = ["male","female","unknown"];

my_sex.initSex = function(sex, defaultSex, sex_group) {

	my_sex.info = sex_group;
	
    var sexEl = $(sex);
    var hasDefaultSex = (typeof(defaultSex) != 'undefined');

    var sexHtml = '';
    for(var i = 0; i < my_sex.info.length; i++) {
        sexHtml += '<option value="' + my_sex.info[i] + '"' + ((hasDefaultSex && my_sex.info[i] == defaultSex) ? ' selected="selected"' : '') + '>' + my_sex.info[i] + '</option>';
    }
    sexEl.html(sexHtml);
};

my_sex.getSex = function(sexIndex) {
	var sex = ''; 
	var hasSexIndex = (typeof(sexIndex) != 'undefined');
	if (hasSexIndex && sexIndex != "" && parseInt(sexIndex) >= 0)
	{
		sex = my_sex.info[sexIndex];
	}
	
	return sex;
};

