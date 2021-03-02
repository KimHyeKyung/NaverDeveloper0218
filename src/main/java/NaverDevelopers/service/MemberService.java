package NaverDevelopers.service;

import java.io.IOException;

import NaverDevelopers.domain.dto.MemberDto;

public interface MemberService {

	void save(MemberDto dto);


	void Check(String email) throws IOException;


}
