package kr.co.trito.tams.comm.utils.search;

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
	private Map<String, Object> params; //검색 파라미터
	
	public SearchCondition() {}
	
	public SearchCondition(Map<String, Object> params) {
		this.params = params;
	}	
	
	public void pageSetup(int totalCount, int currentPage, int numOfRows) {
		setCurrentPage(currentPage);
		setNumOfRows(numOfRows);
		setTotalCount(totalCount);
		calcLastPage(getTotalCount(), getNumOfRows());
		calcStartEndPage(getCurrentPage(), numOfPages);
		calcStartEnd(getCurrentPage(), getNumOfRows());		
	}
	
	// 제일 마지막 페이지 계산
	public void calcLastPage(int totalCount, int numOfRows) {
		setLastPage((int) Math.ceil((double)totalCount / (double)numOfRows));
	}
	
	// 시작, 끝 페이지 계산
	public void calcStartEndPage(int currentPage, int numOfPages) {
		setEndPage(((int)Math.ceil((double)currentPage / (double)numOfPages)) * numOfPages);
		if (getLastPage() < getEndPage()) {
			setEndPage(getLastPage());
		}
		setStartPage(getEndPage() - numOfPages + 1);
		if (getStartPage() < 1) {
			setStartPage(1);
		}
	}
	
	// DB 쿼리에서 사용할 start, end값 계산
	public void calcStartEnd(int currentPage, int numOfRows) {
		setEnd(currentPage * numOfRows);
		setStart(getEnd() - numOfRows + 1);
	}
}