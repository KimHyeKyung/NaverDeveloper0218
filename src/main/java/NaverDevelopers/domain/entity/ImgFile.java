package NaverDevelopers.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class ImgFile {
	
	//이미지 파일을 여기다 따로 놓는다
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long no;
	
	String orgName; //originalName
	String newName;
	long fileSize;
	String fileUrl;
	
	
	public ImgFile(String orgName, String newName, long fileSize, String fileUrl) {
		this.orgName = orgName;
		this.newName = newName;
		this.fileSize = fileSize;
		this.fileUrl = fileUrl;
	}
	
	
}
