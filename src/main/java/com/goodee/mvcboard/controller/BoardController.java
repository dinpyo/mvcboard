package com.goodee.mvcboard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.mvcboard.service.BoardService;
import com.goodee.mvcboard.vo.Board;
import com.goodee.mvcboard.vo.Boardfile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
// ------------------------------------------- selectBoardOne ---------------------------
	@GetMapping("/board/boardOne")
	public String selectBoardOne(Model model ,Board board) {
		Board boardOne = boardService.selectBoardOne(board);
		model.addAttribute("boardOne", boardOne);
		return "/board/boardOne";
	}
	
// ------------------------------------------- removeBoard -------------------------------
	@GetMapping("/board/removeBoard")
	public String removeBoard(Model model, Board board) {
		model.addAttribute("boardNo", board.getBoardNo());
		model.addAttribute("memberId", board.getMemberId());
		return "/board/removeBoard";
	}
	
	@PostMapping("/board/removeBoard")
	public String removeBoard(HttpServletRequest request,Boardfile boardfile) {
		String path = request.getServletContext().getRealPath("/upload/");
		int row = boardService.removeBoard(path, boardfile);
		log.debug("\u001B[31m"+"row : "+row+"\u001B[0m"); // 로그 출력 문자열 색상지정
		return "redirect:/board/boardList";
	}
	
// -------------------------------------------- modifyBoard -------------------------------------
	@GetMapping("/board/modifyBoard")
	public String modifyBoard(Model model, Board board) {
		model.addAttribute("boardNo", board.getBoardNo());
		model.addAttribute("memberId", board.getMemberId());
		return "/board/modifyBoard";
	}
	
	@PostMapping("/board/modifyBoard")
	public String modifyBoard(Board board) {
		int row = boardService.modifyBoard(board);
		log.debug("\u001B[31m"+"row : "+row+"\u001B[0m"); // 로그 출력 문자열 색상지정
		return "redirect:/board/boardList";
	}
	 
// -------------------------------------------- addBoard -----------------------------------------
	@GetMapping("/board/addBoard")
	public String addBoard() {
		return "/board/addBoard";
	}
	
	@PostMapping("/board/addBoard")
	public String addBoard(HttpServletRequest request,Board board) { // 매개값으로 request객체를 받는다 <- request api를 직접호출하기 위해서
		String path = request.getServletContext().getRealPath("/upload/"); // 절대경로, getRealPath() : 실제 톰캣이 돌아가고 있는 컴퓨터에서 그곳의 절대 주소값 리턴
		int row = boardService.addBoard(board, path);
		log.debug("\u001B[31m"+"row : "+row+"\u001B[0m"); // 로그 출력 문자열 색상지정
		return "redirect:/board/boardList";
	}
	
// -------------------------------------------- boardList --------------------------------------------
	@GetMapping("/board/boardList")
	public String boardList(Model model, 
							@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
							@RequestParam(name = "rowPerPage", defaultValue = "10") int rowPerPage,
							@RequestParam(name = "localName", required = false) String localName) {
		
		log.debug("\u001B[31m"+"localName : "+ localName+"\u001B[0m");
		
		Map<String, Object> resultMap = boardService.getBoardList(currentPage, rowPerPage, localName);
		
		// view로 넘길때는 다시 분리해서
		model.addAttribute("localNameList", resultMap.get("localNameList"));
		model.addAttribute("boardList", resultMap.get("boardList"));
			
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		
		return "/board/boardList";
	}
}
