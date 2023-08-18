package com.goodee.mvcboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.mvcboard.mapper.BoardMapper;
import com.goodee.mvcboard.mapper.BoardfileMapper;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardfileMapper boardfileMapper;
	
// --------------------------------------------------------------------------------------------------------------------------------------------------	
	// REST API chart 호출.....
	public List<Map<String, Object>> getLocalNameList(){
		return boardMapper.selectLocalNameList();
	}

// --------------------------------------------------------------------------------------------------------------------------------------------------		
	
	public Board selectBoardOne(Board board) {
		Board boardOne = boardMapper.selectBoardOne(board);
		return boardOne;
	}
	
// --------------------------------------------------------------------------------------------------------------------------------------------------	
	public int removeBoard(String path, Boardfile boardfile) {
		// saveSavefilename 저장
		List<Boardfile> bf = boardfileMapper.selectSavefilename(boardfile);
		log.debug(""+bf);
		if(bf.size() > 0) {
			// boardfile 테이블 삭제
			int row2 = boardfileMapper.deleteBoardfile(boardfile); 
			if(row2 > 0) {
				for(Boardfile i : bf) {
					File file = new File(path+i.getSaveFilename());
				    file.delete();
				}
			}
		}
		// board 테이블 삭제
		int row = boardMapper.deleteBoard(boardfile);
		return row;
	}

// --------------------------------------------------------------------------------------------------------------------------------------------------	
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	
// --------------------------------------------------------------------------------------------------------------------------------------------------
	public int addBoard(Board board, String path) {
		// board 추가
		int row = boardMapper.insertBoard(board);
		
		// board 추가에 성공하고 첨부된 파일이 1개 이상이 있다면
		List<MultipartFile> fileList = board.getMultipartFile();
		if(row == 1 && fileList != null && fileList.size() > 0) { // 추가성공, null이 아니고 1개 이상이면  
			int boardNo = board.getBoardNo();
			
			for(MultipartFile mf : fileList) { // 첨부된 파일의 개수만큼 반복
				if(mf.getSize() > 0) {	
					Boardfile bf = new Boardfile();
					bf.setBoardNo(boardNo); // 부모키값
					bf.setOriginFilename(mf.getOriginalFilename()); // 파일원본이름
					bf.setFilesize(mf.getSize()); // 파일사이즈
					bf.setFiletype(mf.getContentType()); // 파일타입(MIME)
					
					// 저장될 파일 이름
					// 확장자
					String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
					
					// 새로운 이름 + 확장자
					bf.setSaveFilename(UUID.randomUUID().toString().replace("-", "") + ext);	
					
					// 테이블에 저장
					boardfileMapper.insertBoardfile(bf);
					
					// 파일저장(저장위치필요 -> path변수)	
					File f = new File(path+bf.getSaveFilename()); // path위치에 저장파일이름으로 빈파일을 생성
					
					// 빈파일에 첨부된 파일의 스트림을 주입한다.
					try {
						mf.transferTo(f);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
						
						// 트랜잭션 작동을 위해 예외(try...catch강요하지 않는 예외 : RuntimeException) 발생이 필요하다.
						throw new RuntimeException();
					}
				}
			}
		}
		return row;
	}

// --------------------------------------------------------------------------------------------------------------------------------------------------
	public Map<String, Object> getBoardList(int currentPage, int rowPerPage, String localName){
		
		// service layer 역할1 : controller가 넘겨준 매개값을 dao의 매개값에 맞게 가공
		int beginRow = (currentPage-1) * rowPerPage;
		
		// 반환값 1
		List<Map<String, Object>> localNameList = boardMapper.selectLocalNameList();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginRow", beginRow);
		paramMap.put("rowPerPage", rowPerPage);
		paramMap.put("localName", localName);
		
		// 반환값 2
		List<Board> boardList = boardMapper.selectBoardListByPage(paramMap);
				
		int boardCount = boardMapper.selectBoardCount();
		// service layer 역할2 : dao에서 반환 받은 값을 controller에 반환
		int lastPage = boardCount / rowPerPage;
		if(boardCount % rowPerPage != 0) {
			lastPage += 1;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("localNameList", localNameList);
		resultMap.put("boardList", boardList);
		resultMap.put("lastPage", lastPage);
		
		return resultMap;
	}
}
