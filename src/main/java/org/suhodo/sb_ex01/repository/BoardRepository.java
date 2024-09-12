package org.suhodo.sb_ex01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.suhodo.sb_ex01.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);
}
