package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.QnAListViewDTO;
import org.proleesh.gabbi.dto.QnAViewDTO;
import org.proleesh.gabbi.entity.Comment;
import org.proleesh.gabbi.entity.QnA;
import org.proleesh.gabbi.service.CommentService;
import org.proleesh.gabbi.service.QnAService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
/**
 * @author sung-hyuklee
 * date: 2024.6,28
 * QnA Controller
 */
@Controller
@RequiredArgsConstructor
public class QnAController {
    private final QnAService qnAService;
    private final CommentService commentService;

    @GetMapping("/qna-all")
    public String getQnAAll(Model model) {
        List<QnAListViewDTO> qna_all = qnAService.findAll()
                .stream()
                .sorted(Comparator.comparing(QnA::getRegisterTime).reversed())
                .map(QnAListViewDTO::new)
                .toList();
        model.addAttribute("qna_all", qna_all);
        return "others/qnaList";
    }

    @GetMapping("/qna-all/{id}")
    public String getQnA(@PathVariable Long id, Model model) {
        // id를 통해 QnA객체 검색
        QnA qna = qnAService.findById(id);
        // QnA관한 댓글 얻기
        List<Comment> comments = commentService.getCommentsByQnaId(id);
        // QnA객체 & 댓글을 model 에 추가하기
        model.addAttribute("qna", new QnAViewDTO(qna));
        model.addAttribute("comments", comments);
        // 사용자가 수정 또는 삭제 권한 있는지 확인
        boolean canEdit = qna.getCreatedBy().equals(getCurrentUsername());
        model.addAttribute("canEdit", canEdit);

        return "others/qna";
    }

    @GetMapping("/new-qna")
    public String newQnA(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("qna", new QnAViewDTO());
        } else {
            QnA qna = qnAService.findById(id);
            model.addAttribute("qna", new QnAViewDTO(qna));
        }

        return "others/newQnA";
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }


}
