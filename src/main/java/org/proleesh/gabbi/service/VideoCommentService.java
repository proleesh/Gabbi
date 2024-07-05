package org.proleesh.gabbi.service;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.VideoCommentDTO;
import org.proleesh.gabbi.entity.Comment;
import org.proleesh.gabbi.entity.VideoComment;
import org.proleesh.gabbi.repository.CommentRepository;
import org.proleesh.gabbi.repository.VideoCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
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
public class VideoCommentService {
    private final VideoCommentRepository videoCommentRepository;
    private final static Logger logger = LoggerFactory.getLogger(VideoCommentService.class);

    public void addComment(VideoCommentDTO videoCommentDTO, User user) {
        VideoComment videoComment = new VideoComment();
        videoComment.setContent(videoCommentDTO.getContent());
        videoComment.setVideoName(videoCommentDTO.getVideoName());
        videoComment.setAuthor(user.getUsername());
        videoComment.setRegisterTime(LocalDateTime.now());
        videoCommentRepository.save(videoComment);
    }

    public List<VideoComment> getCommentsByVideoName(String videoName){
        List<VideoComment> comments = videoCommentRepository.findByVideoName(videoName);
        comments.forEach(comment -> logger.info(comment.getContent()));
        return comments;
    }



}
