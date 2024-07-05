package org.proleesh.gabbi.service;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.VideoReplyDTO;
import org.proleesh.gabbi.entity.VideoReply;
import org.proleesh.gabbi.repository.VideoReplyRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class VideoReplyService {
    private final VideoReplyRepository videoReplyRepository;

    public void addReply(VideoReplyDTO replyDTO, User user) {
        VideoReply videoReply = new VideoReply();
        videoReply.setContent(replyDTO.getContent());
        videoReply.setCommentId(replyDTO.getCommentId());
        videoReply.setAuthor(user.getUsername());
        videoReply.setRegisterTime(LocalDateTime.now());
        videoReplyRepository.save(videoReply);
    }

}
