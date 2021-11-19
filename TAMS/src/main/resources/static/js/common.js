(function($) {
  $.commRequest = function(url, reqType, data) {
	return new Promise(function(resolve, reject){
		$.ajax({
			url:url,
			data:data,
			type:reqType,
			dataType:'json',
			contentType: 'application/json',
			success: (result) => resolve(result),
			error: (request, status, error) => {
				reject(error);
			}
		});
	});
  };

  $.commRequestFile = function(url, reqType, data) {
	return new Promise(function(resolve, reject){
		$.ajax({
			url:url,
			data:data,
			enctype: 'multipart/form-data',  
			type:reqType,
			dataType:'json',
			contentType : false,
	        processData : false,
			success: (result) => resolve(result),
			error: (request, status, error) => {
				reject(error);
			}
		});
	});
  };

  $.commRequestSelectbox = function(url, reqType, data, selectboxId) {

		$.commRequest(url, reqType, data)
			.then((res) => {
				var str = '';
				$.each(res.data, function(i){
					str += '<option value="' + res.data[i].codeId + '">' + res.data[i].codeNm + '</option>';
				})
				$(selectboxId).append(str);
			})
			.catch((error) => {
				console.log('조회 실패');
			});
  };

}(jQuery));

//excel 업로드용 조회

var excelItems = [];

function showUploadExcelData(data, gridId) {
	
	excelItems.lengths = 0;

	excelItems = data;
	
	$(gridId).jsGrid(getGridBody()).trigger("loadData");

}

//화면 재조회시 화면 권한체크 
$(document).ready(function(){
	console.log("[ready] "+ $("#currMenuId").val());
	getMyRole($("#currMenuId").val());
	
});

//화면 권한체크
function getMyRole(menuId) { 
	
	
	let url = '/common/menu/menuRoleCheck';
	let reqType = 'GET';				
	let data = {
		"menuId" : menuId
	};
	$.commRequest(url, reqType, data)
		.then((res) => {
			console.log('메뉴 권한 조회 성공!!');
		})
		.catch((error) => {
			//alert('메뉴조회 실패!!');
			console.log('메뉴 권한 조회 실패!!');
		});								
}
			