package NaverDevelopers.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor//<select>쿼리할때 .map(BoardDto::new)의 new를 파라미터가 없는 생성자로 만들기 위해서 사용한다.
@Data
public class MemberDto {

	private String email;
	
	private String password;
	
	private String name;


	public MemberDto(String email, String password, String name) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
	}


}
