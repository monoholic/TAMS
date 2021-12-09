package kr.co.trito.tams.comm.util.res;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.msg.Message;
import kr.co.trito.tams.comm.util.search.SearchCondition;

@Service
public class ResponseService {
	
	@Autowired
	Message message;
	
	public ResponseEntity<PageResponse> success(SearchCondition condition, List<?> data){
		PageResponse pageResponse = new PageResponse(condition, data);
		this.setSuccessMsg(pageResponse);
		return ResponseEntity.ok().body(pageResponse);
	}
	
	public ResponseEntity<PageResponse> success(SearchCondition condition, List<?> data, List<?> data2){
		PageResponse pageResponse = new PageResponse(condition, data, data2);
		this.setSuccessMsg(pageResponse);
		return ResponseEntity.ok().body(pageResponse);
	}
	
	public <T>ResponseEntity<GeneralResponse<T>> success(String code, String message){
		return this.success(code, message, null);
	}	
	
	public <T>ResponseEntity<GeneralResponse<T>> success(T data){
		GeneralResponse<T> generalResponse = new GeneralResponse<>(data);
		this.setSuccessMsg(generalResponse);
		return ResponseEntity.ok().body(generalResponse);
	}
	
	public <T>ResponseEntity<GeneralResponse<T>> success(String code, String message, T data){
		GeneralResponse<T> generalResponse = new GeneralResponse<>(data);
		generalResponse.setCode(code);
		generalResponse.setMessage(message);
		return ResponseEntity.ok().body(generalResponse);
	}	

	public <T>ResponseEntity<ApiResponse<T>> success(T data, int totalCount, int currentPage, int numOfRows){
		ApiResponse<T> apiResponse = new ApiResponse<>(data, totalCount, currentPage, numOfRows);
		this.setSuccessMsg(apiResponse);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	public ResponseEntity<Response> error(){
		return this.error(HttpStatus.INTERNAL_SERVER_ERROR, message.getMessage("info.http.status.internal.server.error"), 
				message.getMessage("info.http.status.internal.server.error.msg"));
	}
	
	public ResponseEntity<Response> error(String code, String message){
		GeneralResponse<?> generalResponse = new GeneralResponse<>();
		generalResponse.setCode(code);
		generalResponse.setMessage(message);
		return ResponseEntity.ok().body(generalResponse);
	}	

	public ResponseEntity<Response> error(HttpStatus status, String code, String message){
		GeneralResponse<?> generalResponse = new GeneralResponse<>();
		generalResponse.setCode(code);
		generalResponse.setMessage(message);
		return ResponseEntity.status(status).body(generalResponse);
	}		
	
	private void setSuccessMsg(Response response) {
		response.setCode(message.getMessage("info.http.status.ok"));
		response.setMessage(message.getMessage("info.http.status.ok.msg"));		
	}
}
