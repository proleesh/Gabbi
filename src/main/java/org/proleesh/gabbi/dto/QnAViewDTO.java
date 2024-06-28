package org.proleesh.gabbi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.proleesh.gabbi.entity.BaseTimeEntity;
import org.proleesh.gabbi.entity.QnA;

@Getter
@NoArgsConstructor(force = true)
public class QnAViewDTO extends BaseTimeEntity {
    private final Long id;
    private final String qnaTitle;
    private final String qnaContent;

    public QnAViewDTO(QnA qna) {
        this.id = qna.getId();
        this.qnaTitle = qna.getQnaTitle();
        this.qnaContent = qna.getQnaContent();
        setRegisterTime(qna.getRegisterTime());
    }
}
