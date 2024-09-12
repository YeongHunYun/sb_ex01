package org.suhodo.sb_ex01.service;

import org.suhodo.sb_ex01.domain.Board;
import org.suhodo.sb_ex01.dto.*;

public interface BoardService {
    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long bno);
    void modify(BoardDTO boardDTO);
    void remove(Long bno);
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);




    //////////////////////////////////질문
//    default Board dtoTOEntity(BoardDTO boardDTO) {
//        Board board = Board.builder()
//                .bno(boardDTO.getBno())
//                .title(boardDTO.getTitle())
//                .content(boardDTO.getContent())
//                .writer(boardDTO.getWriter())
//                .build();
//
//        if(boardDTO.getFileName() != null){
//            boardDTO.getFileName().forEach(fileName -> {
//                String[] arr =fileName.split("_");
//                board.addImage(arr[0], arr[1]);
//            });
//        }
//        return board;
//    }


}
