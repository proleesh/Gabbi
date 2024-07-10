package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.AddQnARequestDTO;
import org.proleesh.gabbi.dto.QnADTO;
import org.proleesh.gabbi.dto.UpdateQnARequestDTO;
import org.proleesh.gabbi.entity.QnA;
import org.proleesh.gabbi.service.QnAService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost", "null"})
public class QnARestController {
    private final QnAService qnAService;

    @GetMapping("/api/qna-all")
    public ResponseEntity<List<QnADTO>> findAllQnA() {
        List<QnADTO> qna = qnAService.findAll()
                .stream()
                .map(QnADTO::new)
                .toList();
        return ResponseEntity.ok().body(qna);
    }

    @GetMapping("/api/qna-all/{id}")
    public ResponseEntity<QnADTO> findQnA(@PathVariable Long id) {
        QnA qna = qnAService.findById(id);
        return ResponseEntity.ok().body(new QnADTO(qna));
    }

    @PostMapping("/api/qna-all")
    public ResponseEntity<QnA> addQnA(@RequestBody AddQnARequestDTO addQnARequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(qnAService.save(addQnARequestDTO));
    }

    @PutMapping("/api/qna-all/{id}")
    public ResponseEntity<QnA> updateQnA(@PathVariable long id,
                                         @RequestBody UpdateQnARequestDTO updateQnARequestDTO) {
        if(!hasPermission(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            QnA updatedQnA = qnAService.update(id, updateQnARequestDTO);
            return ResponseEntity.ok().body(updatedQnA);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/api/qna-all/{id}")
    public ResponseEntity<Void> deleteQnA(@PathVariable Long id) {
        if(!hasPermission(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        try {
            qnAService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean hasPermission(Long qnaId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            String currentUsername = getCurrentUsername(authentication);

            QnA qna = qnAService.findById(qnaId);
            if(qna != null && qna.getCreatedBy().equals(currentUsername)){
                return true;
            }

            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                return true;
            }
        }
        return false;
    }

    private String getCurrentUsername(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }

}
