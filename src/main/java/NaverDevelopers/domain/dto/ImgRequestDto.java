package NaverDevelopers.domain.dto;

import NaverDevelopers.domain.entity.Img;
import NaverDevelopers.domain.entity.ImgFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImgRequestDto {

	private String subject;
	private String contents;
	
	public Img toEntity(ImgFile fileEntity){
		//return Img.builder().build(); == new Img();
		return Img.builder()
					.subject(subject)
					.contents(contents)
					.img(fileEntity)
					.build(); 
		// new Img(subject, contents);
	}
}
