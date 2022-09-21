package com.picolitebe.picolitebe.service;


import com.picolitebe.picolitebe.models.Article;
import com.picolitebe.picolitebe.repository.ArticleRepository;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    public ArticleRepository articleRepository;

    public List<Article> getAll()
    {
        List<Article> list = articleRepository.findAll();

        return list;
    }

    public Optional<Article> getArticleById(Long id)
    {
        return articleRepository.findById(id);
    }

    public boolean deleteArticle(Long id)
    {
            articleRepository.deleteById(id);
            return true;
    }

    public Article createOrUpdateArticle(Article a)
    {
        Article updated = null;
        if (a.getTitle() != null && a.getContent() != null) {
            updated = articleRepository.save(a);
        }
        return updated;
    }
}
