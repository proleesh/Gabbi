package org.proleesh.gabbi.repository;

import org.proleesh.gabbi.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long>{
    List<Video> findByTitleContaining(String title);
}
