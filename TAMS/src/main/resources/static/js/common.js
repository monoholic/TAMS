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

function getMyRole() { 
	
	let url = '/common/menu/menuRoleCheck';
	let reqType = 'GET';				
	let data = {
		"menuId" : $("#menuId").val()
	};
	$.commRequest(url, reqType, data)
		.then((res) => {
			
			console.log(res);
			
		})
		.catch((error) => {
			alert('메뉴조회 실패!!');
		});								
}