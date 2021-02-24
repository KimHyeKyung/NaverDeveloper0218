package NaverDevelopers.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import NaverDevelopers.domain.dto.ImgDto;
import NaverDevelopers.domain.dto.ImgRequestDto;

public interface ImgService {

	void uploadAndSave(MultipartFile file, ImgRequestDto dto) throws IOException;

	List<ImgDto> getListAll();


}
