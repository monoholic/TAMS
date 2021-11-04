package kr.co.trito.tams.web.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.sample.dto.BoardDto;
import kr.co.trito.tams.web.sample.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping(value = "/sample")
public class SampleController {

	@Autowired
	ResponseService responseService;

	@Autowired
	BoardService boardService;

	@GetMapping(value = "/menu1")
	public String menu1() {
		return "/sample/00-main_메뉴1";
	}
	
	@GetMapping(value = "/menu2")
	public String menu2() {
		return "/sample/00-main_메뉴2";
	}
	
	@GetMapping(value = "/menu3")
	public String menu3() {
		return "/sample/00-메뉴1";
	}
	
	@GetMapping(value = "/menu4")
	public String menu4() {
		return "/sample/00-메뉴2_1";
	}
	
	@GetMapping(value = "/menu5")
	public String menu5() {
		return "/sample/00-메뉴2";
	}
	
	@GetMapping(value = "/screen")
	public String screen() {
		return "/sample/00-화면구성";
	}
	
	@GetMapping(value = "/list1")
	public String list1() {
		return "/sample/01-검색+리스트_투자정보조회";
	}
	
	@GetMapping(value = "/list2")
	public String list2() {
		return "/sample/01-검색+리스트_팝업";
	}
	
	@GetMapping(value = "/list3")
	public String list3() {
		return "/sample/01-입력+리스트_재활용_재활용의뢰작성";
	}
	
	@GetMapping(value = "/list4")
	public String list4() {
		return "/sample/01-입력+리스트_팝업-일괄업로드";
	}
	
	@GetMapping(value = "/list5")
	public String list5() {
		return "/sample/03-검색+리스트";
	}
	
	@GetMapping(value = "/list6")
	public String list6() {
		return "/sample/04-탭+검색+리스트_소계";
	}
	
	@GetMapping(value = "/list7")
	public String list7() {
		return "/sample/06-탭+검색+리스트_버튼_선택_입력";
	}
	
	@GetMapping(value = "/list8")
	public String list8() {
		return "/sample/08-리스트안내문구";
	}
	
	@GetMapping(value = "/list9")
	public String list9() {
		return "/sample/17-리스트헤더유지";
	}
	
	@GetMapping(value = "/regist1")
	public String regist1() {
		return "/sample/01-조회+등록_신규자산등록";
	}
	
	@GetMapping(value = "/regist2")
	public String regist2() {
		return "/sample/01-조회+리스트_등록자산목록";
	}
	
	@GetMapping(value = "/tab1")
	public String tab1() {
		return "/sample/01-탭+검색";
	}
	
	@GetMapping(value = "/tab2")
	public String tab2() {
		return "/sample/01-탭+상세_변경이력";
	}
	
	@GetMapping(value = "/tab3")
	public String tab3() {
		return "/sample/01-탭+상세_자산정보탭";
	}
	
	@GetMapping(value = "/popup1")
	public String popup1() {
		return "/sample/02-메세지창_모달";
	}
	
	@GetMapping(value = "/popup2")
	public String popup2() {
		return "/sample/05-팝업_모달";
	}
	
	@GetMapping(value = "/popup3")
	public String popup3() {
		return "/sample/02-메세지창_팝업_경고";
	}
	
	@GetMapping(value = "/popup4")
	public String popup4() {
		return "/sample/02-메세지창_팝업_에러";
	}
	
	@GetMapping(value = "/popup5")
	public String popup5() {
		return "/sample/02-메세지창_팝업_확인";
	}
	
	@GetMapping(value = "/popup6")
	public String popup6() {
		return "/sample/07-팝업호출버튼";
	}
	
	@GetMapping(value = "/popup7")
	public String popup7() {
		return "/sample/10-팝업뷰";
	}
	
	@GetMapping(value = "/popup8")
	public String popup8() {
		return "/sample/11-팝업이미지뷰";
	}
	
	@GetMapping(value = "/btn1")
	public String btn1() {
		return "/sample/09-선택박스(다중)";
	}
	
	@GetMapping(value = "/btn2")
	public String btn2() {
		return "/sample/15-비활성버튼";
	}
	
	@GetMapping(value = "/btn3")
	public String btn3() {
		return "/sample/16-공통버튼_라디오버튼";
	}
	
	@GetMapping(value = "/board1")
	public String board1() {
		return "/sample/19-게시판 등록";
	}
	
	@GetMapping(value = "/board2")
	public String board2() {
		return "/sample/20-게시판 상세";
	}
	
	@GetMapping(value = "/etc1")
	public String etc1() {
		return "/sample/12-로딩";
	}
	
	@GetMapping(value = "/etc2")
	public String etc2() {
		return "/sample/13-메일폼";
	}
	
	@GetMapping(value = "/etc3")
	public String etc3() {
		return "/sample/18-달력";
	}
}
