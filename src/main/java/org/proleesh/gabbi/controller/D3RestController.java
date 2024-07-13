package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.entity.Member;
import org.proleesh.gabbi.entity.Video;
import org.proleesh.gabbi.repository.MemberRepository;
import org.proleesh.gabbi.repository.VideoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class D3RestController {

    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;



    @GetMapping("/api/videos")
    public List<Video> getVideos() {
        return videoRepository.findAll();
    }


}
