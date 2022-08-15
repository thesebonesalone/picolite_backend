package com.picolitebe.picolitebe.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "game_url")
    private String game_url;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Comment> comments;
}
