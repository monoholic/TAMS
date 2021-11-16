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

}(jQuery));
