package NaverDevelopers.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import NaverDevelopers.domain.dto.MemberDto;
import NaverDevelopers.domain.entity.MemberEntity;
import NaverDevelopers.domain.entity.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j //log.debug쓰려고 적어줌
@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(MemberDto dto) {
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));//save전 passwordEncoder로 비밀번호 변환
		MemberEntity resultEntity = repository.save(dto.toEntity());
		if(resultEntity == null) {
			log.debug("회원가입오류");
		}
	}

	@Autowired //HttpServletResponse response 사용해주기
	HttpServletResponse response; //응답코드, 응답메세지를 담아서 전송해준다 -> cycle이 끝날때까지 지속
	
	@Override
	public void Check(String email) throws IOException {
		MemberEntity result = repository.findByEmail(email).orElse(null);//email은 pk값이 아니여서 findByEmail로 새로 만들자
		//email이 존재하면 entity객체, 존재하지않으면 null
		int check=0;
		if(result==null) { //result.isEmpty()도 사용가능
			//result==null이면 사용가능한 이메일
			check=1;
		}
		
		response.getOutputStream().print(check); //문자열로 보내기 싫어서 getOutputStream사용
	}
	

	/*처음에 사용했지만 $.post에서 if(result=="1")과 같이 문자열("1")로 넘어가서 getOutputStream으로 변경
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(check); //PrintWriter는 문자열로 보낸다
	*/



	

		
	

}
