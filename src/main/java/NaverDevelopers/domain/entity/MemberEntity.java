package NaverDevelopers.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EntityListeners(AuditingEntityListener.class)
@Table(name = "navermemboard")
@ToString
@NoArgsConstructor
@Getter
@Entity
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	@Column(nullable = false, unique = true) //unique = true로 중복 안되게 해주자
	private String email;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@CreatedDate
	private LocalDateTime regDate;

	
	@Builder
	public MemberEntity(long no, String email, String name, String password, LocalDateTime regDate) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
	}
	
	

	

	
}
