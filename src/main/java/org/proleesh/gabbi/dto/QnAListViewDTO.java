package org.proleesh.gabbi.dto;

import lombok.Getter;
import org.proleesh.gabbi.entity.QnA;

@Getter
public class QnAListViewDTO {
    private final Long id;
    private final String qnaTitle;
    private final String qnaContent;

    public QnAListViewDTO(QnA qna) {
        this.id = qna.getId();
        this.qnaTitle = qna.getQnaTitle();
        this.qnaContent = qna.getQnaContent();
    }
}
