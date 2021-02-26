package NaverDevelopers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import NaverDevelopers.domain.dto.MemberDto;
import NaverDevelopers.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
//-------------------------------------------------------------------

	@GetMapping("/member/join")
	public String join() {
		return "/member/join";
	}
	
	//회원가입버튼 누르면 회원가입 정보 DB에 저장
	@PostMapping("/member/join")
	public String join(MemberDto dto) {
		log.debug("dto:"+dto);
		service.save(dto);
		return "redirect:/";
	}
}
