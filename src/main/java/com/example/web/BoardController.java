package com.example.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.Board;
import com.example.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // @Resource
    // public void setBoardService(BoardService boardService) {
    // this.boardService = boardService;
    // }

    // http://localhost:8181/springanno/app/member/login

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "board/writeForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpSession session, @RequestParam("file") MultipartFile multipartFile,
            @ModelAttribute Board board, HttpServletRequest request) throws Exception {

        String id = (String) session.getAttribute("id");
        // 세션값 없으면 /member/loginForm 으로 이동
        if (id == null) {
            return "redirect:/member/login";
        }

        // 파일 업로드 수행
        String filename = ""; // 파일명 초기화
        if (!multipartFile.isEmpty()) { // 파일 있으면
            filename = multipartFile.getOriginalFilename(); // 파일명 가져오기
            String uploadPath = session.getServletContext().getRealPath("/upload");
            
            // 업로드할 디렉토리 준비
//            String contextPath = session.getServletContext().getRealPath("/");
//            File uploadPath = new File(contextPath, "upload");
//            if (!uploadPath.exists()) {
//                uploadPath.mkdir();
//            }
            
            File file = new File(uploadPath, filename);
            if (file.exists()) { // 해당 경로안에 동일한 파일명이 이미 존재할 경우
                // 파일명 앞에 업로드 시간 밀리초 단위로 붙여 파일명 중복을 방지
                filename = System.currentTimeMillis() + "_" + filename;
                file = new File(uploadPath, filename);
            }

            System.out.println("업로드 경로: " + uploadPath);
            System.out.println("업로드 파일명: " + filename);

            // 업로드 수행
            IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
        } else {
            System.out.println("파일이 존재하지 않거나 파일크기가 0입니다.");
        }

        // DB insert
        board.setFilename(filename); // 파일명 저장
        board.setReg_date(new Timestamp(System.currentTimeMillis()));
        board.setIp(request.getRemoteAddr());

        boardService.add(board); // 주글 등록

        return "redirect:/board/list";
    }

    @RequestMapping("list")
    public String list(HttpSession session,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum, ModelMap modelMap) {

        String id = (String) session.getAttribute("id");
        // 세션값 없으면 /member/loginForm 으로 이동
        if (id == null) {
            return "redirect:/member/login";
        }

        // 전체글개수 가져오기 메소드 호출
        int totalRowCount = boardService.getBoardCount();

        // 우리가 원하는 페이지 글 가져오기

        // 한페이지 당 보여줄 글 개수!!
        int pageSize = 15;
        // 클라이언트가 전송하는 페이지번호를 기준으로
        // 가져올 글의 시작행번호와 종료행번호를 계산하면 됨.
//		if (strPageNum == null || strPageNum.equals("")) {
//			strPageNum = "1";
//		}
//		int pageNum = Integer.parseInt(strPageNum); // 페이지번호

        // 시작행번호 구하기 공식
        int startRow = (pageNum - 1) * pageSize + 1;
        // 종료행번호 구하기 공식
        int endRow = pageNum * pageSize;
        // 원하는 페이지의 글을 가져오는 메소드
        List<Board> boards = boardService.getBoardList(startRow, pageSize);

        // 전체 페이지블록 갯수 구하기
        // 글갯수50개, 한화면보여줄글10개 => 50/10 = 몫5 + 나머지0 = 페이지블록5개
        // 글갯수52개, 한화면보여줄글10개 => 52/10 = 몫5 + 나머지2 = (+1)페이지블록6개
        int pageCount = totalRowCount / pageSize + (totalRowCount % pageSize == 0 ? 0 : 1);

        // 한 화면에 보여줄 페이지블록 갯수 설정
        int pageBlock = 10;

        // 화면에 보여줄 "페이지블록 범위내의 시작번호" 구하기
        // 1~10 11~20 21~30
        // 1~10 => 1 11~20 => 11
        int startPage = (pageNum / pageBlock - (pageNum % pageBlock == 0 ? 1 : 0)) * pageBlock + 1;

        // 화면에 보여줄 "페이지블록 범위내의 끝번호" 구하기
        int endPage = startPage + pageBlock - 1;
        if (endPage > pageCount) {
            endPage = pageCount;
        }

        // 저장
        modelMap.addAttribute("totalRowCount", totalRowCount);
        modelMap.addAttribute("pageNum", pageNum);
        modelMap.addAttribute("boards", boards);
        modelMap.addAttribute("pageCount", pageCount);
        modelMap.addAttribute("pageBlock", pageBlock);
        modelMap.addAttribute("startPage", startPage);
        modelMap.addAttribute("endPage", endPage);

        // 이동
        return "board/list";
    } // list()

    @RequestMapping("detail")
    public String detail(HttpSession session, @RequestParam int num, @RequestParam String pageNum, ModelMap modelMap) {
        String id = (String) session.getAttribute("id");
        // 세션값 없으면 /member/loginForm 으로 이동
        if (id == null) {
            return "redirect:/member/login";
        }

        boardService.updateReadcount(num); // 조회수 1 증가
        Board board = boardService.getBoard(num);

        // 저장
        modelMap.addAttribute("board", board);
        modelMap.addAttribute("pageNum", pageNum);

        return "board/content";
    } // detail()

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(HttpSession session, @RequestParam int num, @RequestParam String pageNum, ModelMap modelMap) {
        String id = (String) session.getAttribute("id");
        // 세션값 없으면 /member/loginForm 으로 이동
        if (id == null) {
            return "redirect:/member/login";
        }

        Board board = boardService.getBoard(num);

        // 저장
        modelMap.addAttribute("board", board);
        modelMap.addAttribute("pageNum", pageNum);

        return "board/updateForm";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ModelAndView update(HttpSession session, @ModelAttribute Board board, @RequestParam String pageNum,
            HttpServletResponse response) throws IOException {
        String id = (String) session.getAttribute("id");
        // 세션값 없으면 /member/loginForm 으로 이동
        if (id == null) {
            return new ModelAndView("redirect:/member/login");
        }

        int check = boardService.updateBoard(board); // 글수정
        if (check == 0) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('패스워드틀림. 수정권한 없음');");
            out.println("history.back();</script>");
            out.close();
            return null;
        }

        ModelAndView mav = new ModelAndView("redirect:/board/list");
        mav.addObject("pageNum", pageNum);
        return mav;
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String delete() {
        return "board/deleteForm";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ModelAndView delete(HttpSession session, @RequestParam int num, @RequestParam String passwd,
            @RequestParam String pageNum, HttpServletResponse response) throws IOException {
        String id = (String) session.getAttribute("id");
        // 세션값 없으면 /member/loginForm 으로 이동
        if (id == null) {
            return new ModelAndView("redirect:/member/login");
        }

        int check = boardService.deleteBoard(num, passwd); // 글삭제
        if (check == 0) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('패스워드틀림. 삭제권한 없음');");
            out.println("history.back();</script>");
            out.close();
            return null;
        }

        ModelAndView mav = new ModelAndView("redirect:/board/list");
        mav.addObject("pageNum", pageNum);
        return mav;
    }

    @RequestMapping(value = "reAdd", method = RequestMethod.GET)
    public String reAdd() {
        return "board/reWriteForm";
    }

    @RequestMapping(value = "reAdd", method = RequestMethod.POST)
    public ModelAndView reAdd(HttpSession session, @ModelAttribute Board board, HttpServletRequest request,
            @RequestParam String pageNum) {
        String id = (String) session.getAttribute("id");
        // 세션값 없으면 /member/loginForm 으로 이동
        if (id == null) {
            return new ModelAndView("redirect:/member/login");
        }

        board.setReg_date(new Timestamp(System.currentTimeMillis()));
        board.setIp(request.getRemoteAddr());

        boardService.reInsertBoard(board); // 답글 등록

        ModelAndView mav = new ModelAndView("redirect:/board/list");
        mav.addObject("pageNum", pageNum);
        return mav;
    }

} // BoardController 클래스 끝
