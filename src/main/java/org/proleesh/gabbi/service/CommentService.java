package org.proleesh.gabbi.service;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.CommentDTO;
import org.proleesh.gabbi.entity.Comment;
import org.proleesh.gabbi.entity.Member;
import org.proleesh.gabbi.repository.CommentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author sung-hyuklee
 * date: 2024.7.5
 * 댓글 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void addComment(CommentDTO commentDTO, User user) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setQnaId(commentDTO.getQnaId());
        comment.setAuthor(user.getUsername());
        comment.setRegisterTime(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByQnaId(Long qnaId){
        return commentRepository.findByQnaId(qnaId);
    }

}
