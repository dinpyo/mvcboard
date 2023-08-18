package com.goodee.mvcboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.mvcboard.vo.Boardfile;

@Mapper
public interface BoardfileMapper {
	// 게시글 파일 추가
	int insertBoardfile(Boardfile boardfile);
	
	// 게시글 파일 삭제
	int deleteBoardfile(Boardfile boardfile);
	
	// savefilename 조회
	List<Boardfile> selectSavefilename(Boardfile boardfile);	

}

