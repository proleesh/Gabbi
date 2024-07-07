package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.proleesh.gabbi.entity.BaseEntity;
import org.proleesh.gabbi.entity.QnA;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
public class QnAViewDTO extends BaseEntity {
    private final Long id;
    private final String qnaTitle;
    private final String qnaContent;
    private final String qnaAuthor;
    private final LocalDateTime qnaRegTime;

    public QnAViewDTO(QnA qna) {
        this.id = qna.getId();
        this.qnaTitle = qna.getQnaTitle();
        this.qnaContent = qna.getQnaContent();
        this.qnaAuthor = qna.getCreatedBy();
        this.qnaRegTime = qna.getRegisterTime();
    }
}
