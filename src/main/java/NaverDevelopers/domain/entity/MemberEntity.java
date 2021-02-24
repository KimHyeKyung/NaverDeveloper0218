package NaverDevelopers.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "navermemboard")
@ToString
@NoArgsConstructor
@Getter
@Entity
public class MemberEntity {

	@Id
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;

	
	
	public void doJoin(String email, String password, String name) {
		this.email=email;
		this.password=password;
		this.name=name;
		
	}


	

	
}
