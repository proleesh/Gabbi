package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.CommentDTO;
import org.proleesh.gabbi.entity.Comment;
import org.proleesh.gabbi.entity.Member;
import org.proleesh.gabbi.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sung-hyuklee
 * date: 2024.7.5
 * 댓글 rest api
 */
@RestController
@RequestMapping(value = "/api/comments", method = RequestMethod.POST)
@RequiredArgsConstructor
public class CommentRestController {

    private final  CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO,
                                           @AuthenticationPrincipal User user) {
        commentService.addComment(commentDTO, user);
        return ResponseEntity.ok().body(commentDTO);
    }

    @GetMapping("/api/{qnaId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long qnaId){
        List<Comment> comments = commentService.getCommentsByQnaId(qnaId);
        return ResponseEntity.ok().body(comments);
    }

}
