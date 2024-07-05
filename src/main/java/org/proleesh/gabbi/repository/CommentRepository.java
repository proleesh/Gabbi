package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByQnaId(Long qnaId);
}
