package org.suhodo.sb_ex01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.suhodo.sb_ex01.dto.BoardDTO;
import org.suhodo.sb_ex01.dto.BoardListAllDTO;
import org.suhodo.sb_ex01.dto.PageRequestDTO;
import org.suhodo.sb_ex01.dto.PageResponseDTO;
import org.suhodo.sb_ex01.service.BoardService;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardListAllDTO> responseDTO=boardService.listWithAll(pageRequestDTO);
        log.info(responseDTO+"@@@@@@@@@@@@@@");
        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("보드 포스트 레지스텈ㅋㅋㅋㅋㅋㅋㅋ ");

        if(bindingResult.hasErrors()) {
            log.info("에렄ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }
        log.info(boardDTO+"보드디티옼ㅋㅋ");
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public String read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO+"<<< 얘는 보드 디티오임 리드 메서드임 스트링임 ㅋㅋ");
        model.addAttribute("dto", boardDTO);
    }


    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("보드 모디파이 포스틐ㅋㅋ" + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors..........에렄ㅋㅋ..");

            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/modify?" + link;

        }
        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        Long bno = boardDTO.getBno();
        log.info("게시글 지웤ㅋㅋㅋ" + bno);
        boardService.remove(bno);

        log.info(boardDTO.getFileName()+"게시물이 데이터베이스 상에서 삭제되었다면 첨부파일도 삭제zzzz");
        List<String> fileNames = boardDTO.getFileName();
        if(fileNames != null && fileNames.size() > 0) {
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }

    @Value("${org.suhodo.upload.path}")
    private String uploadPath;

    private void removeFiles(List<String> files){
        for(String fileName : files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();
                if(contentType.startsWith("image")){
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" +fileName);

                    thumbnailFile.delete();
                }
            }catch (Exception e){
                log.error(e.getMessage()+"e랑 e.getMessage차이 확인해보자");
            }

        }
    }




}
