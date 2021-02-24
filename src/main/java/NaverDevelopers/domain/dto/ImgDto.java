package NaverDevelopers.domain.dto;

import NaverDevelopers.domain.entity.Img;
import NaverDevelopers.domain.entity.ImgFile;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImgDto {

	long no;
	String subject;
	String contents;
	
	ImgFileDto img;





	public ImgDto(Img img) {
		this.no = img.getNo();
		this.subject = img.getSubject();
		this.contents = img.getContents();
		
		ImgFile imFile = img.getImg();
		this.img = new ImgFileDto( imFile );
	}
	
	
}
