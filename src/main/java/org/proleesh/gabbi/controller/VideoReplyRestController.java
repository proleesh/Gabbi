package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.VideoReplyDTO;
import org.proleesh.gabbi.service.VideoReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sung-hyuklee
 * date: 2024.7.5
 * 답글 rest api
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowedHeaders = "*")
public class VideoReplyRestController {

    private final VideoReplyService videoReplyService;

    @PostMapping("/api/videoReplies")
    public ResponseEntity<VideoReplyDTO> addReply(@RequestBody VideoReplyDTO videoReplyDTO,
                                                  @AuthenticationPrincipal User user) {
        videoReplyService.addReply(videoReplyDTO, user);
        return ResponseEntity.ok().body(videoReplyDTO);
    }
}
