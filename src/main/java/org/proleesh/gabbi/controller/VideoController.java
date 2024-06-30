package org.proleesh.gabbi.controller;

import org.proleesh.gabbi.entity.Video;
import org.proleesh.gabbi.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author sung-hyuklee
 * data: 2024.6.30
 * 비디오 Controller
 */
@Controller
public class VideoController {

    private final Path videoLocation;
    private final VideoRepository videoRepository;

    public VideoController(@Value("${file.upload-dir}") String uploadDir, VideoRepository videoRepository) {
        this.videoLocation = Paths.get(uploadDir);
        this.videoRepository = videoRepository;
    }

    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename) {
        try {
            Path file = videoLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
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
            model.addAttribute("videoUrl", "/video/" + filename);
            model.addAttribute("videoTitle", video.getTitle());
            model.addAttribute("videoAuthor", video.getAuthor());
            model.addAttribute("videoDate", video.getRegisterTime());
            return "watch/watchVideo";
        } else {
            return "redirect:/watch";
        }
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload/upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "업로드할 파일을 선택하세요!");
                return "redirect:/upload";
            }
            String originalFilename = file.getOriginalFilename();

            Path destinationFile = videoLocation.resolve(originalFilename).normalize().toAbsolutePath();

            file.transferTo(destinationFile.toFile());

            // 이름 특징 뽑내기
            int underscoreIndex = originalFilename.lastIndexOf('_');
            int dotIndex = originalFilename.lastIndexOf('.');

            if (underscoreIndex == -1 || dotIndex == -1 || underscoreIndex >= dotIndex) {
                redirectAttributes.addFlashAttribute("message", "동영상 파일 이름은 'author_title.*' 형식이어야 합니다.");
                return "redirect:/upload";
            }

            String author = originalFilename.substring(0, underscoreIndex);
            String title = originalFilename.substring(underscoreIndex + 1, dotIndex);


            // 동영상을 DB에 저장하기
            Video video = new Video(file.getOriginalFilename(),
                    title,
                    author);
            videoRepository.save(video);

            String message = "당신은 성공적으로 동영상 " + file.getOriginalFilename() + "를 업로드 했습니다.";
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/watch";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "Failed to upload '" + file.getOriginalFilename() + "' due to " + e.getMessage());
            return "redirect:/upload";
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }
}
