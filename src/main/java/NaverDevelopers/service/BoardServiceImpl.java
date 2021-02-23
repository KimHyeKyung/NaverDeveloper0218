package NaverDevelopers.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NaverDevelopers.domain.dto.BoardDto;
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
	
	//map(Function<t,r>): t->r 로 매핑해주는 기능
	
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

	//상세페이지 -> list가 필요없다, pk값만 가져와서 처리하자
	@Override
	public BoardDto getDetailList(long bno) {
		return repository.findById(bno)
				.map(BoardDto::new) //데이터가 하나만 있는 경우 map처리만 하기(stream,collect안씀)
				.orElse(null);
	/* 이것도 가능
	  public BoardEntity getDetailList(long bno) { 
			 BoardEntity result = repository.findById(bno).orElse(null);
			 return result;
	 */
	}

	@Transactional//이거 만들자
	@Override
	public void updateDetail(BoardDto dto) {
		/* 원래 하던거
		//원래 저장된 데이터 불러와서 Id값을 기준으로 검색해서 확인하고 나온 결과(수정 전)을 수정한 후(update메서드 실행) 다시 엔티티에 저장
		 	//ID를 이용하여 DB의 원본확인(수정 전) -> DTO객체를 Entity로 넣을래요
			BoardEntity entity = repository.findById(dto.getBno()).orElse(null);//orElse(null):없으면 null을 넣을게요
			
			//Entity에 적은 update메서드를 이용해서 변경할 정보 수정 -> 여기 메서드가 실행되면 subject와 contents가 수정된다.
			entity.updateDetail(dto);
			
			//수정된 내용을 Entity로 저장(update처리)
			repository.save(entity);
		*/
	//--------------------------
		// 이거는 트랜잭션 처리해야지만 사용가능
		repository.findById(dto.getBno())
					.map(e->e.setUpdate(dto)) // 따로 save하지 않아도 update처리 -> 트랜젝션이 처리되어서(영속성)
					.orElse(null);
		
	}

	//delete
	@Override
	public void deleteDetail(long bno) {
		repository.deleteById(bno);
	}


}
