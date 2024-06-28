package org.proleesh.gabbi.service;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.AddQnARequestDTO;
import org.proleesh.gabbi.dto.UpdateQnARequestDTO;
import org.proleesh.gabbi.entity.QnA;
import org.proleesh.gabbi.repository.QnARepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnARepository qnARepository;

    public List<QnA> findAll(){
        return qnARepository.findAll();
    }

    public QnA findById(long id) {
        return qnARepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("당신의 id: " + id + "를 찾지못했습니다."));
    }

    @Transactional
    public QnA save(AddQnARequestDTO addQnARequestDTO) {
        return qnARepository.save(addQnARequestDTO.toEntity());
    }

    @Transactional
    public QnA update(long id, UpdateQnARequestDTO updateQnARequestDTO) {
        QnA qna = qnARepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id: " + id + "를 찾지못함!"));
        qna.update(updateQnARequestDTO.getQnaTitle(), updateQnARequestDTO.getQnaContent());
        return qna;
    }

    @Transactional
    public void delete(long id) {
        qnARepository.deleteById(id);
    }
}
