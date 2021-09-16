package kr.co.trito.tams.comm.util.search;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchCondition {
	private int currentPage; //현재페이지
	private int startPage;	//시작 페이지
	private int endPage;	//마지막 페이지
	private int lastPage;	// 마지막 페이지	
	private int totalCount;	//조회 전체수
	private int start;	//쿼리 조회 시작 번호
	private int end;	//쿼리 조회 마지막 번호
	private int numOfRows;	//페이지당 글 목록수	
	private int numOfPages = 5;	//하단 페이지 출력수
	private Map<String, Object> params;  //검색 파라미터
	
	public SearchCondition() {}
		
	public SearchCondition(String currentPage, String numOfRows, Map<String, Object> params) {
		this.currentPage = Integer.parseInt(currentPage);
		this.numOfRows = Integer.parseInt(numOfRows);
		this.params = params;
	}	
	
	public void pageSetup(int totalCount) {
		setTotalCount(totalCount);
		calcLastPage();
		calcStartEndPage();
		calcStartEnd();		
	}
	
	// 제일 마지막 페이지 계산
	public void calcLastPage() {
		this.lastPage = (int) Math.ceil((double)this.totalCount / (double)this.numOfRows);
	}
	
	// 시작, 끝 페이지 계산
	public void calcStartEndPage() {
		this.endPage = ((int)Math.ceil((double)this.currentPage / (double)this.numOfPages)) * this.numOfPages;

		if (this.lastPage < this.endPage) {
			this.endPage = this.lastPage;
		}
		
		this.startPage = this.endPage - this.numOfPages + 1;
		
		if (this.startPage < 1) {
			this.startPage = 1;
		}
	}
	
	// DB 쿼리에서 사용할 start, end값 계산
	public void calcStartEnd() {
		this.end = this.currentPage * this.numOfRows;
		this.start = this.end - this.numOfRows + 1;
	}
}
