//初始化弹出框
function dialogInit(){
	$("#container").dialog({
		autoOpen : false,
		modal : true,
		height : 500,
		width : 1280,
		buttons : {
			"OK" : function() {
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
		}
	});
	$("#container2").dialog({
		autoOpen : false,
		modal : true,
		height : 500,
		width : 1280,
		buttons : {
			"OK" : function() {
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
		}
	});
	//弹出框初始
	$("#dialog").dialog({
		autoOpen : false,
		height : 500,
		width : 800,
		modal : true,
		buttons : {
			"OK" : function() {
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		},
		close : function() {
		}
	});
}

//提交form请求
var options = {
	beforeSubmit : showRequest,
	success : showResponse
};
//form提交的通用方法
function showRequest(formData, jqForm, options) {
	var queryString = $.param(formData);
	//alert('About to submit: \n\n' + queryString); 
	return true;
}
function showResponse(responseText, statusText, xhr, $form) {
	//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +         '\n\nThe output div should have already been updated with the responseText.'); 
	openDialog(responseText);
}
function showResponse2(responseText, statusText, xhr, $form) {
	//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +         '\n\nThe output div should have already been updated with the responseText.'); 
	//alert(responseText['success']+":"+responseText['id']);
	$("#"+responseText['type']+responseText['id']).val(responseText['success']);
}
function showResponseText(responseText, statusText, xhr, $form) {
	//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +         '\n\nThe output div should have already been updated with the responseText.'); 
	alert(responseText['message'])
}
//刷新当前页面
function refresh(responseText, statusText, xhr, $form) {
	location.reload();
}