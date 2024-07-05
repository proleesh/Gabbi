package org.proleesh.gabbi.controller;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.dto.QnAListViewDTO;
import org.proleesh.gabbi.dto.QnAViewDTO;
import org.proleesh.gabbi.entity.Comment;
import org.proleesh.gabbi.entity.QnA;
import org.proleesh.gabbi.service.CommentService;
import org.proleesh.gabbi.service.QnAService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QnAController {
    private final QnAService qnAService;
    private final CommentService commentService;

    @GetMapping("/qna-all")
    public String getQnAAll(Model model) {
        List<QnAListViewDTO> qna_all = qnAService.findAll()
                .stream()
                .map(QnAListViewDTO::new)
                .toList();
        model.addAttribute("qna_all", qna_all);
        return "others/qnaList";
    }

    @GetMapping("/qna-all/{id}")
    public String getQnA(@PathVariable Long id, Model model) {
        QnA qna = qnAService.findById(id);
        List<Comment> comments = commentService.getCommentsByQnaId(id);
        model.addAttribute("qna", new QnAViewDTO(qna));
        model.addAttribute("comments", comments);
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


}
