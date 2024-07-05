package org.proleesh.gabbi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Long qnaId;
    private String author;

    @OneToMany(mappedBy = "commentId")
    private List<Reply> replies;

}
