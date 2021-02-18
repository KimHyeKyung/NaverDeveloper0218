package NaverDevelopers.domain.dto;

import java.time.LocalDateTime;

import NaverDevelopers.domain.entity.BoardEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor//<select>쿼리할때 .map(BoardDto::new)의 new를 파라미터가 없는 생성자로 만들기 위해서 사용한다.
@Data
public class BoardDto {

	private long bno;
	private String subject;
	private String contents;
	private String creatorId;
	private LocalDateTime createdDate;
	
	
	//<save>쿼리: 저장할때 DTO로 저장할 수 없어서 Entity로 바꾸기 위한 메서드
	public BoardEntity toEntity(){ 
		return BoardEntity.builder()
				.bno(bno)
				.subject(subject)
				.contents(contents)
				.creatorId(creatorId)
				.createdDate(createdDate)
				.build();
	
	}


	//<select>쿼리: Entity를 Dto로 가져가기위해서 만든 메소드
	public BoardDto(BoardEntity entity) {
		super();
		this.bno = entity.getBno();
		this.subject = entity.getSubject();
		this.contents = entity.getContents();
		this.creatorId = entity.getCreatorId();
		this.createdDate = entity.getCreatedDate();
	}
	
	
}
