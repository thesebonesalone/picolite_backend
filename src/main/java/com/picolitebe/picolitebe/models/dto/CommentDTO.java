package com.picolitebe.picolitebe.models.dto;


import com.picolitebe.picolitebe.models.Comment;
import com.picolitebe.picolitebe.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    private String username;

    private String content;

    private Long article_id;

    public Comment convert(ArticleService articleService)
    {
        Comment c = new Comment();
        c.setContent(content);
        c.setUsername(username);
        c.setId(id);
        try {
            c.setArticle(articleService.getArticleById(article_id).get());
        } catch (Exception e)
        {
            log.info("Cannot convert to Comment. Article is null");
        }
        return c;
    }
}
