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
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import NaverDevelopers.domain.dto.BoardDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EnableJpaAuditing //데이터가 생성됬을때 이벤트 시간을 자동으로 삽입하기위해서 꼭 넣어야한다.☆main메서드에도 넣기☆
@Table(name = "naverboard")
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bno;
	
	@Column(nullable = false)
	private String subject;
	
	@Column(nullable = false)
	private String contents;
	
	@Column(nullable = false)
	private String creatorId;
	
	@CreatedDate
	private LocalDateTime createdDate;

	
	@Builder
	public BoardEntity(long bno, String subject, String contents, String creatorId, LocalDateTime createdDate) {
		super();
		this.bno = bno;
		this.subject = subject;
		this.contents = contents;
		this.creatorId = creatorId;
		this.createdDate = createdDate;
	}



/*쌤이 쓴거 트랜젝셔널처리 안할때 쓰는거
 	//수정처리를 위한 메서드
	public BoardEntity updateDetail(BoardDto dto) {
		this.subject = dto.getSubject();
		this.contents = dto.getContents();
		return this;//this를 리턴하는게 특징
	}
	
*/

	//트랜젝셔널처리할때 쓰는거
	public BoardEntity setUpdate(BoardDto dto) {
		this.subject = dto.getSubject();
		this.contents = dto.getContents();
		return this;
	
	}




	
	
	
}
