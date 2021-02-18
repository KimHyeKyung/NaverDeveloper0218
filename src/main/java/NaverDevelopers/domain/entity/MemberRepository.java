package NaverDevelopers.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import NaverDevelopers.domain.dto.MemberDto;

public interface MemberRepository extends JpaRepository<MemberEntity, String>{


}
