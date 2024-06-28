package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.AddQnARequestDTO;
import org.proleesh.gabbi.dto.QnADTO;
import org.proleesh.gabbi.dto.UpdateQnARequestDTO;
import org.proleesh.gabbi.entity.QnA;
import org.proleesh.gabbi.service.QnAService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"htp://localhost", "null"})
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
        QnA updatedQnA = qnAService.update(id, updateQnARequestDTO);
        return ResponseEntity.ok().body(updatedQnA);
    }

    @DeleteMapping("/api/qna-all/{id}")
    public ResponseEntity<Void> deleteQnA(@PathVariable Long id) {
        qnAService.delete(id);
        return ResponseEntity.ok().build();
    }

}
