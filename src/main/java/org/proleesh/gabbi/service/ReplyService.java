package org.proleesh.gabbi.service;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.ReplyDTO;
import org.proleesh.gabbi.entity.Reply;
import org.proleesh.gabbi.repository.ReplyRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public void addReply(ReplyDTO replyDTO, User user) {
        Reply reply = new Reply();
        reply.setContent(replyDTO.getContent());
        reply.setCommentId(replyDTO.getCommentId());
        reply.setAuthor(user.getUsername());
        reply.setRegisterTime(LocalDateTime.now());
        replyRepository.save(reply);
    }

}
