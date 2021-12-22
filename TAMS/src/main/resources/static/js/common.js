
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

  /*
	 args -> 0:url, 1:reqType, 3:selectboxId, 4:codeGrpId, 5:codeLvl, 5:upperCodeId
  */
  $.commRequestSelectbox = function(...args) {
		//console.log(args);
		let params = {
			"codeGrpId" : args[3],
			"codeLvl" : args.length > 4 ? args[4]: "", 
			"upperCodeId" : args.length > 5 ? args[5]: ""
		}
		$.commRequest(args[0], args[1], params)
			.then((res) => {
				var str = '';
				$.each(res.data, function(i){
					str += '<option value="' + res.data[i].codeId + '">' + res.data[i].codeNm + '</option>';
				})
				$(args[2]).append(str);
			})
			.catch((error) => {
				console.log('조회 실패');
			});
  };
       
  $.commRequestComCode = function(...args) {
  	let params = {
		"codeGrpId" : args[0],
		"codeLvl" : args.length > 1 ? args[1]: "", 
		"upperCodeId" : args.length > 2 ? args[2]: ""
	}		
	return new Promise(function(resolve, reject){					
		$.commRequest("/common/comm/selectBox", "GET", params)
			.then((res) => {
				resolve(res.data);				
			}).catch((error) => {
				console.log('조회 실패');
			});										
	});		
		 	
  };         
       
  $.commExcelDown = function(url, params){
	let exlForm = $('<form id="downForm"></form>');
	exlForm.attr('action', url); 
	exlForm.attr('method', 'post');
	exlForm.attr('target', '_self');
	
    if(params != null && params != "") {
	   	for (var key in params) {
			var str = '<input type="hidden" name="'+key+'" value="'+params[key]+'">';
			exlForm.append(str);
		} 	     
    }	 
	exlForm.appendTo('body'); 
	exlForm.submit();		 
  };
  
  
  /*
  	key - 화면ID(저장화면 중복이 안되는 상황이면 MenuID를 써도 될듯.)
  	value - 검색조건 ({a:1, b:2} 객체 타입)
  */
  $.commSaveSearchParams = function(key, value){
	localStorage.setItem(key, JSON.stringify(value));
  };

  //화면 조회 파라미터 로드 	  
  $.commLoadSearchParams = function(key){
	return JSON.parse(localStorage.getItem(key));
  };	
  	
  //화면 조회 파라미터 삭제	
  $.commRemoveSearchParams = function(key){
	localStorage.removeItem(key);
  };

  //화면 조회 파라미터 삭제	
  $.commAllRemoveSearchParams = function(){
	localStorage.clear();
  };

  $.commGridLocalization = function(){	
		return {
		        "ko-kr":{
		            "pagination":{
		            	"page_size":"Page Size", 
		                "page_title":"페이지",
		                "first":"<<", 
		                "first_title":"처음 페이지", 
		                "last":">>",
		                "last_title":"마지막 페이지",
		                "prev":"<",
		                "prev_title":"이전 페이지",
		                "next":">",
		                "next_title":"다음 페이지",
		                "all":"All",
		            },
		        },
		        "en-gb":{
		            "pagination":{
		                "first":"First", 
		                "first_title":"First Page",
		                "last":"Last",
		                "last_title":"Last Page",
		                "prev":"Prev",
		                "prev_title":"Prev Page",
		                "next":"Next",
		                "next_title":"Next Page",
		            },		
				},
		}
  };
  
  $.fn.center = function () {
  	this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) + $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + $(window).scrollLeft()) + "px");
    return this;
  }   

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


//천단위 콤마 펑션(value 문자타입)
function addComma(value){
 	value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return value; 
}

//천단위 콤마 제거 펑션
function removeComma(value){
	value = value.replace(/[^\d]+/g, "");
    return value; 
}			

//파일 사이즈 표시
function bytesToSize(bytes) {
	var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
	if (bytes == 0) return '0 Byte';
	var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
	return addComma(Math.round(bytes / Math.pow(1024, i), 2)+"") + ' ' + sizes[i];
}

// asetType 리스트 출력
function asetTypeList(){                                                                    
	let url = '/common/popup/asetType';                                                      
	let reqType = 'POST';                                                                        
	let data = {                                                                                 
		"email" : ""                                                                             
	};                                                                                           
	$.commRequest(url, reqType, JSON.stringify(data))                                            
		.then((res) => {
			console.log(res);
			$.each(res.data, function(index, el){                                                        
				asetType.push(el);
				
				if(el.codeLvl == '1') {
					$("#asetType1").append('<option value="'+ el.codeId+'">' + el.codeNm+ '</option>');
				}
			})
		})
		.catch((error) => {                                                                      
			alert('[[#{screen.info.status.error.occur}]]');                                                                  
		});
};

// 자산 유형 옵션 그리기
function makeAsetCombo(objNm, arr) {
	$(objNm).empty();
	
	$.each(arr, function(i, e) {
		$(objNm).append('<option value="'+ e.codeId+'">' + e.codeNm+ '</option>');
	});
}

// 자산 유형 부모 요소 찾아서 return
function getAsetType(asetType1) {
	return asetType.filter( function(el) {
		return el.uppCodeId == asetType1;
	});
}
/* ///////////////////////////////////////////////////////////////////////// */
/* datepicker 기본 설정  */
/* ///////////////////////////////////////////////////////////////////////// */
$.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',	//날짜 포맷이다. 보통 yy-mm-dd 를 많이 사용하는것 같다.
        prevText: '이전 달',	// 마우스 오버시 이전달 텍스트
        nextText: '다음 달',	// 마우스 오버시 다음달 텍스트
        closeText: '닫기', // 닫기 버튼 텍스트 변경
        currentText: '오늘', // 오늘 텍스트 변경
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],	//한글 캘린더중 월 표시를 위한 부분
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],	//한글 캘린더 중 월 표시를 위한 부분
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],	//한글 캘린더 요일 표시 부분
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],	//한글 요일 표시 부분
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],	// 한글 요일 표시 부분
        showMonthAfterYear: true,	// true : 년 월  false : 월 년 순으로 보여줌
        yearSuffix: '년',	//
        showButtonPanel: true,	// 오늘로 가는 버튼과 달력 닫기 버튼 보기 옵션
});


/**********************************************************/		
/* 팝업호출 공통함수                                           */
/* popId : 팝업창 ID */		
/* url   : url     */		
/* args  : 팝업창 속성 (width, height, fullscreen, resizable) */		
/* param : 팝업창 호출시 param값( id:'' , value:'') */		
/**********************************************************/		
function openPopup(popId, url, args, param) {
	
    var options;
	
	var width 	= args.width;
    var height 	= args.height;
    var fullscreen = args.fullscreen;
    if( fullscreen == null || fullscreen == "" ) fullscreen = "no";
    var resizable = args.resizable;
    if( resizable == null || resizable == "" ) full = "no";
    
    var sTop  = Math.max(0, (($(window).height() - height) / 2) + $(window).scrollTop()) + "px";
    var sLeft = Math.max(0, (($(window).width() - width) / 2) + $(window).scrollLeft()) + "px";
    
    options = "";
    options += "position=absolute";
    options += ",top="+sTop+", left="+sLeft+", height="+height+"px, width="+width+"px";
    options += ",fullscreen="+fullscreen+", resizable="+resizable;
    
    var child;
    child = window.open("", popId, options);
    
    $form = $("<form></form>");
    $form.attr("action", url);
    $form.attr("target", popId);
    $form.appendTo("body");
    
    if( param != null && param != "" ) {
    	var str = '<input name="'+param.id+'" value="'+param.value+'">';
    	var str2 = '<input name="'+param.rcd+'" value="'+param.rnm+'">';
    	$form.append(str);
    	$form.append(str2);
    }
    $form.submit();	        
    $form.empty();
}


function openPopup2(popId, url, args, param) {
	
    var options;
	
	var width 	= args.width;
    var height 	= args.height;
    var fullscreen = args.fullscreen;
    if( fullscreen == null || fullscreen == "" ) fullscreen = "no";
    var resizable = args.resizable;
    if( resizable == null || resizable == "" ) full = "no";
    
	var pheight = args.pheight || $(window).height();
	var pwidth = args.pwidth || $(window).width();
    
    var sTop  = Math.max(0, ((pheight - height) / 2) + $(window).scrollTop()) + "px";
    var sLeft = Math.max(0, ((pwidth - width) / 2) + $(window).scrollLeft()) + "px";
        
    options = "";
    options += "position=absolute";
    options += ",top="+sTop+", left="+sLeft+", height="+height+"px, width="+width+"px";
    options += ",fullscreen="+fullscreen+", resizable="+resizable;
    
    var child;
    child = window.open("", popId, options);
    
    $form = $("<form></form>");
    $form.attr("action", url);
    $form.attr("target", popId);
    $form.appendTo("body");
    
    if( param != null && param != "" ) {
	   	for (var key in param) {
			var str = '<input name="'+key+'" value="'+param[key]+'">';
			$form.append(str);
		} 	     
    }
    
    $form.submit();	        
    $form.empty();
}


