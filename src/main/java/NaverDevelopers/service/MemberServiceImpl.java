package NaverDevelopers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import NaverDevelopers.domain.dto.MemberDto;
import NaverDevelopers.domain.entity.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void save(MemberDto dto) {
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		repository.save(dto.toEntity());
	}
	

		
	

}
