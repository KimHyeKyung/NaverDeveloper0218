package NaverDevelopers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NaverDevelopers.domain.dto.MemberDto;
import NaverDevelopers.domain.entity.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberRepository repository;
	
	@Override
	public void doJoin(MemberDto dto) {
		
		//저장
		//repository.save(dto.toEntity());
		
		
	}

}
