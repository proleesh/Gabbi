package org.proleesh.gabbi.controller;

import org.proleesh.gabbi.entity.Video;
import org.proleesh.gabbi.repository.VideoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class D3RestController {

    private final VideoRepository videoRepository;

    public D3RestController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @GetMapping("/api/videos")
    public List<Video> getVideos() {
        return videoRepository.findAll();
    }

}
