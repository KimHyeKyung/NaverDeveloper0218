package NaverDevelopers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import NaverDevelopers.domain.dto.BoardDto;
import NaverDevelopers.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService service;
	
	//index페이지 보여주기
	@GetMapping("/index")
	public String boardList() {
		return "redirect:/";
	}
	
	//포럼페이지에 DB의 글 목록 가져오기 -> ModelAndView, list, addObject
	@GetMapping("/board/forum")
	public ModelAndView getforumlist() {
		ModelAndView mv = new ModelAndView();//데이터를 ModelAndView로 담아가고 보여주기 위해서
		List<BoardDto> list = service.getforumlist();//일 시키면서 no값이나 아이디나 필요없으니깐 ()안에 아무것도 안씀
		mv.addObject("list",list);//페이지로 이동할때 데이터 갖고가자
		return mv;
	}
	
	//포럼페이지에서 글쓰기 버튼 누르면 write.html로 넘어가기
	@GetMapping("/board/write")
	public String doWrite() {
		return "/board/write";
	}
	
	
	//글쓰기페이지에서 글 작성 후 포럼페이지로 넘어가기
	@PostMapping("/board/forum")//요청주소
	public ModelAndView saveWrite(BoardDto dto) {
		ModelAndView mv = new ModelAndView("redirect:/board/forum");
		service.saveWrite(dto);
		return mv;
	}
	
	//포럼페이지에서 제목을 누르면 detail페이지로 넘어가서 내용보여주기
	@GetMapping("/board/detail/{bno}")
	public ModelAndView getDetailList(@PathVariable long bno) {
		ModelAndView mv = new ModelAndView("/board/detail");//여기는 /board/detail.html이라 경로가 아니라서 뒤에 {bno}값 안쓴다
		BoardDto detail = service.getDetailList(bno);
		mv.addObject("detail", detail);//페이지로 이동할때 detail이란 이름으로 데이터 갖고가자
		return mv;
	}
	
	//detail페이지에서 수정완료 버튼을 누르면 수정한 내용 넘겨주고 수정된 게시글 보여주기
	@PostMapping("/board/detail")
	public String updateDetail(BoardDto dto) {
		service.updateDetail(dto);
		return "redirect:/board/detail/"+dto.getBno();

	}
	

	//detail페이지에서 삭제버튼 누르면 글 삭제되기
	@GetMapping("/board/delete/{bno}")
	public String deleteDetail(@PathVariable long bno) {
		service.deleteDetail(bno);
		return "redirect:/board/forum"; //데이터를 다시 갖고와야하니 redirect쓴다
	}
	
	
}










