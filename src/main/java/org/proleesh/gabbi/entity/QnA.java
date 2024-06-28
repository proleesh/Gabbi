package org.proleesh.gabbi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnA extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "qna_title", nullable = false)
    private String qnaTitle;

    @Column(name = "qna_content", nullable = false)
    private String qnaContent;

    public void update(String qnaTitle, String qnaContent) {
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;
    }
}
