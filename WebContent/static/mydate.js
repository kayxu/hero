//初始化日期选择
function dateInit(){
	$(".time_from").datepicker({
		//defaultDate: "+1w",
		changeMonth : true,
		numberOfMonths : 1,
		dateFormat : "yy-mm-dd",
		onSelect : function(selectedDate) {
			$("#to").datepicker("option", "minDate", selectedDate);
		}
	});
	$(".time_to").datepicker({
		//defaultDate: "+1w",
		changeMonth : true,
		numberOfMonths : 1,
		dateFormat : "yy-mm-dd",
		onSelect : function(selectedDate) {
			$("#from").datepicker("option", "maxDate", selectedDate);
		}
	});

}
//日期格式化函数
function formatDate(pattern,date){
    function formatNumber(data,format){//3
        format = format.length;
        data = data || 0;
        //return format == 1 ? data : String(Math.pow(10,format)+data).substr(-format);//该死的IE6！！！
        return format == 1 ? data : (data=String(Math.pow(10,format)+data)).substr(data.length-format);
    }
    return pattern.replace(/([YMDhsm])\1*/g,function(format){
        switch(format.charAt()){
        case 'Y' :
            return '20'+formatNumber(date.getFullYear(),format);
        case 'M' :
            return formatNumber(date.getMonth()+1,format);
        case 'D' :
            return formatNumber(date.getDate(),format);
        case 'w' :
            return date.getDay()+1;
        case 'h' :
            return formatNumber(date.getHours(),format);
        case 'm' :
            return formatNumber(date.getMinutes(),format);
        case 's' :
            return formatNumber(date.getSeconds(),format);
        }
    });
}