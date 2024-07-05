package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.ReplyDTO;
import org.proleesh.gabbi.entity.Member;
import org.proleesh.gabbi.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author sung-hyuklee
 * date: 2024.7.5
 * 답글 rest api
 */
@RestController
@RequiredArgsConstructor
public class ReplyRestController {

    private final ReplyService replyService;

    @PostMapping("/api/replies")
    public ResponseEntity<ReplyDTO> addReply(@RequestBody ReplyDTO replyDTO,
                                         @AuthenticationPrincipal User user) {
        replyService.addReply(replyDTO, user);
        return ResponseEntity.ok().body(replyDTO);
    }
}
