package NaverDevelopers.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice///예외처리를 위한 클래스입니다.
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {//HttpServletRequest:요청주소
		
		ModelAndView mv = new ModelAndView("/err/err");
		mv.addObject("exception", exception);
		log.error("exception",exception);
		return mv;
	}
}
