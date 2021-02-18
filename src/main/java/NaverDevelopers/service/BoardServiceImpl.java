package NaverDevelopers.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NaverDevelopers.domain.dto.BoardDto;
import NaverDevelopers.domain.entity.BoardEntity;
import NaverDevelopers.domain.entity.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardRepository repository;
//------------------------------------------------------------
	
	//<select>쿼리문
	//1. repository: select쿼리 result는 Entitiy객체이다.
	//2. Entity를 DTO로 바꿔서 가져가야한다(Entity는 데이터 자체라서 DTO를 사용해야한다.)
	//3. .stream .map .collct사용
	
	//포럼페이지에 DB의 글 목록 가져오기 
	@Override
	public List<BoardDto> getforumlist() {
		return repository.findAll() //전체를 가져와주세요 findAll()(데이터를 엔티티로만 리턴)
							.stream() //값을 순서대로 나열한다.
							.map(BoardDto::new) //map: 각 요소에 해당하는 결과를 (변조)매핑하는데 사용한다.
							.collect(Collectors.toList()); //변조한 결과를 집합형태로 모은다.
	}

	@Override
	public void saveWrite(BoardDto dto) {
		repository.save(dto.toEntity());
	}

	@Override
	public BoardEntity getDetailList(long bno) {
		BoardEntity result = repository.findById(bno).orElse(null);
		return result;
	}

	@Override
	public void updateDetail(BoardDto dto) {
		
		//원래 저장된 데이터 불러와서 엔티티에 저장
		BoardEntity entity = repository.findById(dto.getBno()).orElse(null);
		
		//변경할 정보 수정
		entity.updateDetail(dto.getSubject(), dto.getContents());
		
		//저장
		repository.save(entity);
	}

	//delete
	@Override
	public void deleteDetail(long bno) {
		repository.deleteById(bno);
	}


}
