package com.picolitebe.picolitebe.controllers;


import com.picolitebe.picolitebe.models.Article;
import com.picolitebe.picolitebe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @CrossOrigin
    @RequestMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticle(@PathVariable("id") Long articleId)
    {
        try {
            Article a = articleService.getArticleById(articleId).get();
            return new ResponseEntity<>(a, HttpStatus.OK);
        } catch (NoSuchElementException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity deleteArticle(@PathVariable("id") Long articleId)
    {
        if (articleService.deleteArticle(articleId))
        {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<Article>> allArticles()
    {
        List<Article> list = articleService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Article> updateArticle(@RequestBody Article a)
    {
        Article updated = articleService.createOrUpdateArticle(a);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }



}
