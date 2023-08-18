package com.goodee.mvcboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

@Mapper
public interface BoardMapper {
	
	Board selectBoardOne(Board board);
	
	int deleteBoard(Boardfile Boardfile);
	
	int updateBoard(Board board);
	
	int insertBoard(Board board);

	// local_name 컬럼과 count() 반환
	List<Map<String, Object>> selectLocalNameList();

	// mybatis 메서는 매개값을 하나만 허용
	// param : Map<String, Object> map --> int beginRow, int rowPerPage
	List<Board> selectBoardListByPage(Map<String, Object> map);
	
	// 전체 행의 수
	int selectBoardCount();
}
