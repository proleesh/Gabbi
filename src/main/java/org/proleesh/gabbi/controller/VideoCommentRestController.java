package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.VideoCommentDTO;
import org.proleesh.gabbi.entity.VideoComment;
import org.proleesh.gabbi.service.VideoCommentService;
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
@RequestMapping(value = "/api/videoComments")
@RequiredArgsConstructor
public class VideoCommentRestController {

    private final VideoCommentService videoCommentService;

    @PostMapping
    public ResponseEntity<VideoCommentDTO> addComment(@RequestBody VideoCommentDTO videoCommentDTO,
                                                 @AuthenticationPrincipal User user) {
        videoCommentService.addComment(videoCommentDTO, user);
        return ResponseEntity.ok().body(videoCommentDTO);
    }

    @GetMapping("/api/{videoName}")
    public ResponseEntity<List<VideoComment>> getComments(@PathVariable(required = false) String videoName){
        List<VideoComment> comments = videoCommentService.getCommentsByVideoName(videoName);
        return ResponseEntity.ok().body(comments);
    }

}
