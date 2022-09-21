package com.picolitebe.picolitebe.BackendTests;

import com.picolitebe.picolitebe.models.Article;
import com.picolitebe.picolitebe.models.Comment;
import com.picolitebe.picolitebe.repository.ArticleRepository;
import com.picolitebe.picolitebe.repository.CommentRepository;
import com.picolitebe.picolitebe.service.ArticleService;
import com.picolitebe.picolitebe.service.CommentService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@Test
public class PicoLiteBeApplicationTests extends AbstractTestNGSpringContextTests {

    @Mock
    CommentRepository commentRepository;

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    @InjectMocks
    CommentService commentService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void tearDown() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }


    //setups

    private Article mockArticle() {
        Article article = new Article();

        article.setContent("This is a mock test");
        article.setId(1L);
        article.setTitle("This is a test title");

        return article;
    }

    private Article mockArticle2() {
        Article article = new Article();

        article.setContent("This is a mock test 2");
        article.setId(2L);
        article.setTitle("This is a test title");

        return article;
    }

    private Comment mockComment() {
        Comment comment = new Comment();
        comment.setContent("This is a test comment");
        comment.setArticle(mockArticle());
        comment.setUsername("Test");
        comment.setId(1L);

        return comment;
    }

    //positives
    @Test
    public void _1articleSaves() {

        Article article = mockArticle();
        Article article2 = mockArticle2();

        when(articleRepository.save(any())).thenReturn(article);

        Article returnedArticle = articleService.createOrUpdateArticle(article);
        Article returnedArticle2 = articleService.createOrUpdateArticle(article2);
        verify(articleRepository).save(article);
        verify(articleRepository).save(article2);
        assertEquals(article, returnedArticle);
    }

    @Test
    public void _2getAllArticlesReturnsListOfArticles() {
        List<Article> articles = articleService.getAll();
        when(articleRepository.findAll()).thenReturn(articles);
        verify(articleRepository).findAll();

    }

    @Test
    public void _3commentSaves() {
        Comment comment = mockComment();

        when(commentRepository.save(any())).thenReturn(comment);

        Comment returnedComment = commentService.saveOrUpdate(comment);
        verify(commentRepository).save(comment);

        assertEquals(comment, returnedComment);
    }

    //updates
    @Test
    public void _4articleUpdates() {
        Article article = mockArticle();
        article.setTitle("This is a new test");
        Article remock = mockArticle();

        when(articleRepository.save(any())).thenReturn(article);

        Article returned = articleService.createOrUpdateArticle(article);
        verify(articleRepository).save(article);

        assertNotEquals(returned,remock);
        assertEquals(article.getId(), returned.getId());
    }

    public void _5commentUpdates() {
        Comment comment = mockComment();
        comment.setContent("This is new content");
        Comment remock = mockComment();

        when(commentRepository.save(any())).thenReturn(comment);

        Comment returned = commentService.saveOrUpdate(comment);
        verify(commentRepository).save(comment);

        assertNotEquals(returned,remock);
        assertEquals(comment.getId(), returned.getId());
    }

    //positive deletes

    @Test
    public void _6commentDeletes() {
        Comment comment = mockComment();
        doNothing().when(commentRepository).deleteById(anyLong());

        commentService.delete(comment.getId());
        verify(commentRepository).deleteById(comment.getId());
    }

    @Test
    public void _7articleDeletes() {
        Article article = mockArticle();

        doNothing().when(articleRepository).deleteById(anyLong());

        articleService.deleteArticle(article.getId());
        verify(articleRepository).deleteById(article.getId());
    }

    @Test
    public void _8articleGetReturnsNullWhenNoArticle() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Article> article = articleService.getArticleById(1L);
        assertEquals(article.isEmpty(), true);
    }

    @Test
    public void _9commentGetReturnsNullWhenNoComment() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Comment> comment = commentService.getById(1L);
        assertEquals(comment.isEmpty(), true);
    }



}
