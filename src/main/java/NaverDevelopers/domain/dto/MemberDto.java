package NaverDevelopers.domain.dto;

import java.time.LocalDateTime;

import NaverDevelopers.domain.entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor//<select>쿼리할때 .map(BoardDto::new)의 new를 파라미터가 없는 생성자로 만들기 위해서 사용한다.
@Data
public class MemberDto {

	private long no; //자동으로 셋팅되어서 builder에 포함 안시켰다
	private String email;
	private String name;
	private String password;
	private LocalDateTime regDate; //자동으로 셋팅되어서 builder에 포함 안시켰다
	
	
	public MemberEntity toEntity() {
		return MemberEntity.builder()
							.email(email)
							.name(name)
							.password(password)
							.build();
	}


}
