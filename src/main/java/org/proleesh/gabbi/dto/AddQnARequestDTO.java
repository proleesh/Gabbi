package org.proleesh.gabbi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.proleesh.gabbi.entity.QnA;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddQnARequestDTO {
    private String qnaTitle;
    private String qnaContent;

    public QnA toEntity(){
        return QnA.builder()
                .qnaTitle(qnaTitle)
                .qnaContent(qnaContent)
                .build();
    }
}
