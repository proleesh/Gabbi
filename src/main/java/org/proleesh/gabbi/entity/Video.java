package org.proleesh.gabbi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Video extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String title;
    private String author;
    private Long views;

    public Video(String fileName, String title, String author, Long views){
        this.fileName = fileName;
        this.title = title;
        this.author = author;
        this.views = views;
    }
}
