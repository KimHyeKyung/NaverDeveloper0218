package NaverDevelopers.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import NaverDevelopers.domain.dto.ImgDto;
import NaverDevelopers.domain.dto.ImgRequestDto;
import NaverDevelopers.service.ImgService;
import lombok.extern.slf4j.Slf4j;

@Slf4j  //(  syso말고 log.debug()로 활용하자  )
@Controller
public class ImgController {
	
	@Autowired //객체를 Bean에 자동으로 등록
	ImgService service;
	
	// /img/write페이지로 이동해주세요
	@GetMapping("/img/write")
	public String write() {
		return"/img/write"; //viewName:/img/write.html
	}
	
	//이미지 등록
	//MultipartFile로 받으면 파일만 받아지는거
	//parts를 사용하면 제목 내용 파일 한번에 받아짐
	//dto를 사용하면 되니깐 parts보다는 MultipartFile로 사용하자
	//파일은 MultipartFile로 제목내용은 dto로 받자
	@PostMapping("/img/write")
	public String write(MultipartFile file, ImgRequestDto dto) throws IOException {
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
	
	//detail페이지로 가주세요!!
	@GetMapping("/img/detail/{no}")
	public String detail(@PathVariable long no, Model model) { //Model model: 정보 가져가야해서
		
		ImgDto detail = service.getDetail(no);
		model.addAttribute("detail",detail);
		
		return "/img/detail";
	}

	//수정버튼누르면 post로 넘어가는거 처리
	@PostMapping("/img/edit")
	public String edit(ImgRequestDto dto) {
		service.edit(dto);
		
		return "redirect:/img/detail/"+dto.getNo(); //위에있는 detail페이지로 이동
	}
	
	//삭제
	@GetMapping("/img/delete/{no}")
	public String delete(@PathVariable long no) {
		service.delete(no);
		return "redirect:/img/list";
	}
	
	//이미지 업로드 ajax에 넣은 주소
	//복붙해서 다른곳에서 쓰라ㅏ아아
	//이미지 업로드하면 서버에 전송완료!
	@ResponseBody
	@PostMapping("/img/preView")
	public void preView(MultipartFile file, String temp) throws IOException { // ajax에 "file"로 넣은 key값 파라미터에 넣어준다
		//System.out.println(file.getOriginalFilename());
		////파일이 넘어왔으니 임시파일에 넣어야한다.////
		System.out.println("temp :"+temp);
		//최초 temp=""
		//다음 선택할때 이전에 선택된 temp의 값이 나온다
		service.uploadTemp(file,temp); //서비스한테 일 시키자
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
