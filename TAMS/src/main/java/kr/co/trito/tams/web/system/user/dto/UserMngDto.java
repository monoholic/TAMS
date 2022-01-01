package kr.co.trito.tams.web.system.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ApiModel("사용자 정보")
public class UserMngDto {
	private String userId;
	private String userPw;
	private String deptCd;
	private String deptNm;
	private String clpst;
	private String clpstNm;
	private String userNm;
	private String email;	
	private String telno;	
	private String sex;
	private String sexNm;
	private String useYn;
	private String regr;
	private String regdt;
	private String updr;
	private String updt;
	private String chk;
}

