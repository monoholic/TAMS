package kr.co.trito.tams.comm.util.res;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralResponse<T> extends Response {
	private int size;
	private T data;
	
	public GeneralResponse() {}
	
	public GeneralResponse(T data) {
		this.data = data;
		
		if(data instanceof List) {
			this.size = ((List<?>)data).size();
		} else if (data instanceof Map) {
			this.size = ((Map<?, ?>)data).size();
		} else if (data != null) {
			this.size = 1;
		} else {
			this.size = 0;
		}		
	}
}
