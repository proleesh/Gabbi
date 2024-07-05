package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoCommentRepository extends JpaRepository<VideoComment, Long> {
    List<VideoComment> findByVideoName(String videoName);
}
