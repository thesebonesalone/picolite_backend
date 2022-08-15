package com.picolitebe.picolitebe.controllers;

import com.picolitebe.picolitebe.models.Comment;
import com.picolitebe.picolitebe.models.dto.CommentDTO;
import com.picolitebe.picolitebe.service.ArticleService;
import com.picolitebe.picolitebe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;


    @CrossOrigin
    @RequestMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getComment(@PathVariable("id") Long id)
    {
        Comment c = commentService.getById(id).get();

        if (c == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(c, HttpStatus.OK);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteComment(@PathVariable("id") Long id)
    {
        Boolean b = commentService.delete(id);
        return new ResponseEntity<>(b, b ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @RequestMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Comment> saveOrUpdateComment(@RequestBody CommentDTO c)
    {
        System.out.println(c.getArticle_id());
        Comment update = commentService.saveOrUpdate(c.convert(articleService));
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
}
