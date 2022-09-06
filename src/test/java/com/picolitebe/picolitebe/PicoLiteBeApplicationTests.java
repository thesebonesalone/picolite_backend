package com.picolitebe.picolitebe;


import com.picolitebe.picolitebe.repository.ArticleRepository;
import com.picolitebe.picolitebe.repository.CommentRepository;
import com.picolitebe.picolitebe.service.ArticleService;
import com.picolitebe.picolitebe.service.CommentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
class PicoLiteBeApplicationTests extends AbstractTestNGSpringContextTests {


    @Mock
    CommentRepository commentRepository;

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    CommentService commentService;

    @InjectMocks
    ArticleService articleService;

    @BeforeClass
    public void init()
    {
        MockitoAnnotations.openMocks(this);
    }

    @AfterClass
    public void close() throws Exception
    {
        MockitoAnnotations.openMocks(this).close();
    }

    void contextLoads() {
    }

}
