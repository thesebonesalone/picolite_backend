package com.picolitebe.picolitebe.service;

import com.picolitebe.picolitebe.models.Comment;
import com.picolitebe.picolitebe.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Optional<Comment> getById(Long id)
    {
        return commentRepository.findById(id);
    }

    public Comment saveOrUpdate(Comment c)
    {
        return commentRepository.save(c);
    }

    public boolean delete(Long id)
    {

        commentRepository.deleteById(id);
        return true;
    }
}
