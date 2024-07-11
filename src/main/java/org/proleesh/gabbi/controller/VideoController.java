package org.proleesh.gabbi.controller;

import org.proleesh.gabbi.entity.Video;
import org.proleesh.gabbi.entity.VideoComment;
import org.proleesh.gabbi.repository.VideoRepository;
import org.proleesh.gabbi.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sung-hyuklee
 * data: 2024.6.30
 * update: 2024.7.8
 * 비디오 Controller
 */
@Controller
public class VideoController {

    private final Path videoLocation;
    private final VideoRepository videoRepository;
    private final VideoCommentService videoCommentService;

    public VideoController(@Value("${file.upload-dir}") String uploadDir, VideoRepository videoRepository, VideoCommentService videoCommentService) {
        this.videoLocation = Paths.get(uploadDir);
        this.videoRepository = videoRepository;
        try {
            Files.createDirectories(this.videoLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
        this.videoCommentService = videoCommentService;
    }

    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename) {
        try {
            Path file = videoLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .header(HttpHeaders.CONTENT_TYPE, "video/mkv")
                        .header(HttpHeaders.CONTENT_TYPE, "video/webm")
                        .header(HttpHeaders.CONTENT_TYPE, "video/flv")
                        .header(HttpHeaders.CONTENT_TYPE, "video/avi")
                        .header(HttpHeaders.CONTENT_TYPE, "video/wmv")
                        .header(HttpHeaders.CONTENT_TYPE, "video/m3u8")
                        .body(resource);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/watch")
    public String watchVideo(Model model, @RequestParam(value = "message", required = false) String message) {

        List<Video> videos = videoRepository.findAll();
        // 등록 순서 변환, 최신 Top
        videos = videos.stream()
                .sorted(Comparator.comparing(Video::getRegisterTime)
                        .reversed())
                .collect(Collectors.toList());
        model.addAttribute("videos", videos);
        model.addAttribute("message", message);
        return "watch/watch";
    }

    @GetMapping("/watch/{filename}")
    public String watchVideo(@PathVariable("filename") String filename, Model model) {
        Video video = videoRepository.findAll()
                .stream()
                .filter(v -> v.getFileName().equals(filename))
                .findFirst()
                .orElse(null);

        if (video != null) {
            video.setViews(video.getViews() + 1);
            videoRepository.save(video);
        List<VideoComment> videoComments = videoCommentService.getCommentsByVideoName(video.getTitle());
            model.addAttribute("videoUrl", "/video/" + filename);
            model.addAttribute("videoTitle", video.getTitle());
            model.addAttribute("videoAuthor", video.getAuthor());
            model.addAttribute("videoDate", video.getRegisterTime());
            model.addAttribute("videoId", video.getId());
            model.addAttribute("videoCreateBy", video.getCreatedBy());
            model.addAttribute("videoComments", videoComments);
            model.addAttribute("videoViews", video.getViews());
            return "watch/watchVideo";
        } else {
            return "redirect:/watch";
        }

    }

    @GetMapping("/watch/upload")
    public String showUploadForm() {
        return "upload/upload";
    }

    @PostMapping("/watch/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "업로드할 파일을 선택하세요!");
                return "redirect:/watch/upload";
            }
            String originalFilename = file.getOriginalFilename();

            Path destinationFile = videoLocation.resolve(originalFilename).normalize().toAbsolutePath();

            file.transferTo(destinationFile.toFile());

            // 이름 특징 뽑내기
            int underscoreIndex = originalFilename.lastIndexOf('_');
            int dotIndex = originalFilename.lastIndexOf('.');
            int leftBracketIndex = originalFilename.lastIndexOf('[');
            int rightBracketIndex = originalFilename.lastIndexOf(']');
            int leftIntendBracketIndex = originalFilename.lastIndexOf('《');
            int rightIntendBracketIndex = originalFilename.lastIndexOf('》');

            if (underscoreIndex == -1 || dotIndex == -1 || underscoreIndex >= dotIndex) {
                redirectAttributes.addFlashAttribute("message", "동영상 파일 이름은 'author_title.*' 형식이어야 합니다.");
                return "redirect:/watch/upload";
            }
            if (leftBracketIndex != -1 || rightBracketIndex != -1 || leftIntendBracketIndex != -1 || rightIntendBracketIndex != -1) {
                if (leftBracketIndex == -1 || rightBracketIndex == -1 || leftBracketIndex >= rightBracketIndex) {
                    redirectAttributes.addFlashAttribute("message", "파일 이름에 잘못된 괄호가 포함되어 있습니다.");
                    return "redirect:/watch/upload";
                }
                if (leftIntendBracketIndex == -1 || rightIntendBracketIndex == -1 || leftIntendBracketIndex >= rightIntendBracketIndex) {
                    redirectAttributes.addFlashAttribute("message", "파일 이름에 잘못된 꺾쇠괄호가 포함되어 있습니다.");
                    return "redirect:/watch/upload";
                }
            }


            String author = originalFilename.substring(0, underscoreIndex);
            String title = originalFilename.substring(underscoreIndex + 1, dotIndex);


            // 동영상을 DB에 저장하기
            Video video = new Video(file.getOriginalFilename(),
                    title,
                    author, 0L);
            videoRepository.save(video);

            String message = "당신은 성공적으로 동영상 " + file.getOriginalFilename() + "를 업로드 했습니다.";
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/watch";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '" + file.getOriginalFilename() + "' due to " + e.getMessage());
            return "redirect:/watch/upload";
        }
    }

    @PostMapping("/watch/delete/{id}")
    public String deleteVideo(@PathVariable Long id) {
        videoRepository.deleteById(id);
        return "redirect:/watch";
    }


}
