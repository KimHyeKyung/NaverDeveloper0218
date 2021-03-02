package NaverDevelopers.service;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import NaverDevelopers.domain.dto.LoginDto;
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
	
	

	//회원가입처리
	@Override
	public void save(MemberDto dto) {
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));//save전 passwordEncoder로 비밀번호 변환
		MemberEntity resultEntity = repository.save(dto.toEntity());
		if(resultEntity == null) {
			log.debug("회원가입오류");
		}
	}


	//$.post ajax처리하기 = 회원가입 validation
	@Autowired //HttpServletResponse response 사용해주기
	HttpServletResponse response; //응답코드, 응답메세지를 담아서 전송해준다 -> cycle이 끝날때까지 지속
	
	@Override
	public void Check(String email) throws IOException {
		MemberEntity result = repository.findByEmail(email).orElse(null);//email은 pk값이 아니여서 findByEmail로 새로 만들자
																		//email은 String이니 MemberEntity를 써서 entity객체로 만들자
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
		
		
		**PrintWriter는 문자열이기 때문에 setCharacterEncoding을 사용한거고
		  getOutputStream은 int형이기 때문에 setCharacterEncoding을 사용하지 않았다.
	*/

	
	
	
	
	@Autowired
	private HttpSession httpsession; //로그인처리한거 session에 저장하려고 
	
	//로그인처리
	@Override
	public void login(MemberDto dto) {
		//email이 일치하는 데이터가 있는지 확인
		Optional<MemberEntity> op = repository.findByEmail(dto.getEmail());
		if(op.isPresent()){ //isPresent()존재를 판단
			log.debug("일치하는 데이터 존재");
			MemberEntity member = op.get();
			boolean check = passwordEncoder.matches(dto.getPassword(), member.getPassword()); //matches:특정 패턴의 문자열을 포함하는지 확인 (암호화되지 않은 데이터, 암호화 된 데이터)
			log.debug("password:"+check);
			if(check) { //이메일과 비밀번호가 알맞음
				LoginDto logInfo = new LoginDto(member);
				httpsession.setAttribute("logInfo", logInfo);
			}else {
				log.debug("비밀번호가 다릅니다."); //이메일은 맞지만 비밀번호가 다름
			}
		}else {
			log.debug("email이 존재하지 않습니다");//이메일 자체가 없음
		}
	}
	
	
	
	
	@Override
	public void logout() {
		httpsession.removeAttribute("logInfo");
		
	}
	

}
