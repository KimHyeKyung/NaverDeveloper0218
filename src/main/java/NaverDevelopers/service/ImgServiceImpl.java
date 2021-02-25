package NaverDevelopers.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

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
	//1. temp -> upload copy NIO배운다 (양방향 데이터 처리, 스트림 별도로 구분할 필요 없음)
		String[] temps = dto.getTemp().split("/");
		String newName = temps[temps.length-1];
		
		ClassPathResource cpr = new ClassPathResource("static/images"); //물리적으로 폴더 하나 만들어주자
		File source = new File( cpr.getFile(), "/temp/" + newName);//여기있는 파일을
		File upload = new File( cpr.getFile(), "/upload/" + newName);//여기로 옮기고 싶어요
		
		Path sourcePath = Paths.get(source.getPath());
		Path uploadPath = Paths.get(upload.getPath());
		
		//파일이동, 똑같은 이름이 존재하면 덮어쓰기
		Files.move(sourcePath, uploadPath, StandardCopyOption.ATOMIC_MOVE);
		
	//2. save처리 합시다
		String orgName = file.getOriginalFilename(); //위치폴더
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

	@Transactional
	@Override
	public void edit(ImgRequestDto dto) {
		//entity->entity로 변환
		//id값으로 검색하고 존재하지않으면 save 존재하면 update
		
		/*
		//1.원본을 갖고와서 수정할 부분만 수정(null로 바꾸지 않도록 하기위해서)
		Img resultEntity = repositoy.findById(dto.getNo()).orElse(null);//orElse(null)대신에 get()써도 된다.(get():대체자원. (ex)이미 삭제된 데이터입니다.)
		
		//2. 수정된 데이터를 바꾸기 위해서는 set메서드가 있어야 하는데 없으니깐 따로 메서드를 만들자 update로 
		resultEntity.update(dto);
		
		//3. 저장
		repositoy.save(resultEntity);
		*/
		repositoy.findById(dto.getNo())
					.map(Img -> Img.update(dto))//수정된 데이터를 바꾸기 위해서는 set메서드가 있어야 하는데 없으니깐 따로 메서드를 만들자 update로 
					.orElse(null);
	}


	@Override
	public void delete(long no) {
		repositoy.deleteById(no);
	}

	
	
	

	
	@Autowired
	HttpServletResponse response;
	
	@Override
	public void uploadTemp(MultipartFile file, String temp) throws IOException {
	//1. upload처리 먼저 합시다 (fileupload), 위치 : /images/temp
		
		//저장될 path경로 지정   
		ClassPathResource cpr = new ClassPathResource("static/images/temp"); //물리적으로 폴더 하나 만들어주자
		
		//getFile: 파일객체로 변환해준다
		File location = cpr.getFile(); //파일이름
		
		//파일을 선택했을때 내가 올린 파일 빼고 나머지 bin에 쌓인 파일들은 삭제해주세요
		//위치 : /images/temp/어쩌구
		if(!temp.trim().equals("")) {
			String[] temps = temp.split("/");
			File deleteFile = new File(location, temps[temps.length-1]); // temps[temps.length-1] : 파일이름
			deleteFile.delete();
		}
		
		String orgName = file.getOriginalFilename(); //위치폴더
		String[] names = orgName.split("[.]"); //확장자 전에 있는 .을 기준으로 나눠서
		String extension = "." + names[1]; //. + 확장자
		String newName = names[0] + "_" + (System.nanoTime()/1000000000) + extension;//새로운 이름 + 확장자
		
		//파일업로드 위치와 이름을 파일객체로 만들자
		File uploadFile = new File(location, newName); //업로드를 새로운 이름으로 한다.
		
		//업로드하자
		file.transferTo(uploadFile);
		//--------여기까지 처리해서 넣어진 파일은 bin(바이너리)파일에서 확인 가능하다.---------
			
		
	//2. 웹에서 접근가능한 url주소를 페이지로 리턴하자
		String fileUrl = "/images/temp/" + newName;
		
		//utf-8로 인코딩 먼저 하자(getWriter가 ISO어쩌구로 인코딩되어서 바꿔주려고)
		response.setCharacterEncoding("utf-8");
		//전송하자
		response.getWriter().print(fileUrl);
	
		
	
	}

}
