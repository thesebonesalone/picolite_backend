package com.picolitebe.picolitebe.service;


import com.picolitebe.picolitebe.models.Article;
import com.picolitebe.picolitebe.repository.ArticleRepository;
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
        Article a = articleRepository.findById(id).get();
        return Optional.ofNullable(a);
    }

    public boolean deleteArticle(Long id)
    {
        try {
            articleRepository.deleteById(id);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    public Article createOrUpdateArticle(Article a)
    {
        Article updated = articleRepository.save(a);

        return updated;
    }
}
