function putTableDay() {
	var curr_time = new Date();
	var day = curr_time.getDate();
	if (day < 11) {
		$('#tableDay').combobox('select', 0);
		$("#tableDay").val("0");
	} else if (day < 21) {
		$('#tableDay').combobox('select', 1);
		$("#tableDay").val("1");
	} else {
		$('#tableDay').combobox('select', 2);
		$("#tableDay").val("2");
	}
	var strDate = curr_time.getFullYear().toString()
			+ (curr_time.getMonth() + 1);
	$("#tableMonth").datebox("setValue", strDate);
}
function monthformatter(date) {
	var y = date.getFullYear().toString();
	var m = date.getMonth() + 1;
	return y + (m < 10 ? ('0' + m) : m);
}

function monthparser(s) {
	if (!s)
		return new Date();
	var y = s.substr(0, 4);
	var m = s.substr(4, 5);
	if (!isNaN(y) && !isNaN(m)) {
		return new Date(y, m - 1, 1);
	} else {
		return new Date();
	}
}
