package org.proleesh.gabbi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateQnARequestDTO {
    private String qnaTitle;
    private String qnaContent;
}
