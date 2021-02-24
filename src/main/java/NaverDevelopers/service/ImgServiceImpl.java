package NaverDevelopers.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import NaverDevelopers.domain.dto.ImgDto;
import NaverDevelopers.domain.dto.ImgRequestDto;
import NaverDevelopers.domain.entity.Img;
import NaverDevelopers.domain.entity.ImgFile;
import NaverDevelopers.domain.entity.ImgRepository;

@Service
public class ImgServiceImpl implements ImgService {

	@Autowired
	private ImgRepository repositoy;
	
	
	@Override
	public void uploadAndSave(MultipartFile file, ImgRequestDto dto) throws IOException {
	//1. upload처리 먼저 합시다 (fileupload)
		
		//저장될 path경로 지정   
		ClassPathResource cpr = new ClassPathResource("static/images/upload");
		
		//getFile: 파일객체로 변환해준다
		File location = cpr.getFile(); //파일이름
		String orgName = file.getOriginalFilename(); //위치폴더
		String[] names = orgName.split("[.]"); //확장자 전에 있는 .을 기준으로 나눠서
		String extension = "." + names[1]; //확장자
		String newName = names[0] + "_" + (System.nanoTime()/1000000000) + extension;//새로운 이름 + 확장자
		
		//파일업로드 위치와 이름을 파일객체로 만들자
		File uploadFile = new File(location, newName); //업로드를 새로운 이름으로 한다.
		
		//업로드하자
		file.transferTo(uploadFile);
		//-----여기까지 처리해서 넣어진 파일은 bin(바이너리)파일에서 확인 가능하다.-----
		
	//2. save처리 합시다
		String fileUrl = "/images/upload/" + newName;
		long fileSize = file.getSize();
		ImgFile fileEntity = new ImgFile(orgName, newName, fileSize, fileUrl); //ImgFile에 셋팅
		
		//이미지 객체가 만들어진다
		//toEntity에는 subject, contents만 되어있고 이미지는 안되어있다.
		//파라미터에 fileEntity추가, dto에도 fileEntity추가추가
		Img imgEntity = dto.toEntity(fileEntity);
		
		//save하자
		repositoy.save(imgEntity);
	
	}


	@Override
	public List<ImgDto> getListAll() {
	/*	
		List<Img> resultList = repositoy.findAll();
		List<ImgDto> list = new Vector<ImgDto>();
		for(Img img : resultList) {
			ImgDto dto = new ImgDto(img);
			list.add(dto);
		}
		
		return list;
		
	*/
		
		return repositoy.findAll()
						.stream()
						.map(ImgDto::new) //.map(e -> new ImgDto(e))
						.collect(Collectors.toList());
	}


	//detail페이지
	@Override
	public ImgDto getDetail(long no) {
		return repositoy.findById(no)
						.map(ImgDto::new) //이미지로 올라오는 아이를 ImgDto로 매핑해주세요 
						.orElse(null);

	}


}
