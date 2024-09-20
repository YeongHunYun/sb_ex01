package org.suhodo.sb_ex01.service;

import org.suhodo.sb_ex01.dto.PageRequestDTO;
import org.suhodo.sb_ex01.dto.PageResponseDTO;
import org.suhodo.sb_ex01.dto.ReplyDTO;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);

}
