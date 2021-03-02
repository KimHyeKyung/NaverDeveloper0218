package NaverDevelopers.domain.dto;

import NaverDevelopers.domain.entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

	String name;
	String email;
	
	
	
	public LoginDto(MemberEntity entity) {
		this.name = entity.getName();
		this.email = entity.getEmail();
	}
	
	
}
