package NaverDevelopers.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import NaverDevelopers.domain.dto.ImgDto;
import NaverDevelopers.domain.dto.ImgRequestDto;
import NaverDevelopers.domain.entity.Img;
import NaverDevelopers.service.ImgService;

@Controller
public class ImgController {
	
	@Autowired //객체를 Bean에 자동으로 등록
	ImgService service;
	
	// /img/write페이지로 이동해주세요
	@GetMapping("/img/write")
	public String write() {
		return"/img/write"; //viewName:/img/write.html
	}
	
	//MultipartFile로 받으면 파일만 받아지는거
	//parts를 사용하면 제목 내용 파일 한번에 받아짐
	//dto를 사용하면 되니깐 parts보다는 MultipartFile로 사용하자
	//파일은 MultipartFile로 제목내용은 dto로 받자
	@PostMapping("/img/write")
	public String write(MultipartFile file, ImgRequestDto dto) throws IOException {
		//System.out.println(file.getOriginalFilename());
		//System.out.println(dto);
		service.uploadAndSave(file,dto);
		return "redirect:/img/list";
	}
	
	//이미지리스트 페이지  -->  read
	@GetMapping("/img/list")
	public String list(Model model) {
		
		//데이터 읽어오기
		List<ImgDto> list = service.getListAll(); //Img폴더가 메인폴더
		model.addAttribute("list",list);
		
		return "/img/list";
	}

	
	
	
	
	
}
