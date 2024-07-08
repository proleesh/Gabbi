package org.proleesh.gabbi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnA extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "qna_title", nullable = false)
    private String qnaTitle;

    @Column(name = "qna_content", nullable = false, length=655555555)
    private String qnaContent;

    public void update(String qnaTitle, String qnaContent) {
        this.qnaTitle = qnaTitle;
        this.qnaContent = qnaContent;
    }
}
