package org.proleesh.gabbi.dto;

import lombok.Getter;
import org.proleesh.gabbi.entity.BaseTimeEntity;
import org.proleesh.gabbi.entity.QnA;

@Getter
public class QnADTO extends BaseTimeEntity {
    private final Long id;
    private final String qnaTitle;
    private final String qnaContent;

    public QnADTO(QnA qna) {
        this.id = qna.getId();
        this.qnaTitle = qna.getQnaTitle();
        this.qnaContent = qna.getQnaContent();
        setRegisterTime(qna.getRegisterTime());
        setUpdateTime(qna.getUpdateTime());
    }
}
