package com.picolitebe.picolitebe.Selenium;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.autoconfigure.web.reactive.function.client.ReactorNettyHttpClientMapper;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

@Test
public class FrontEndTests {

    @Test(enabled = false)
    //sleeper method
    public void sleep(Integer ms)
    {
        try {
            Thread.sleep(ms);
            Assert.assertEquals(0,0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    WebDriver webDriver;
    JavascriptExecutor js;

    String BASE_URL = "http://localhost:8080/";

    @BeforeClass
    public void init()
    {

    }

    @BeforeTest
    public void setUp() {
        webDriver = WebDriverManager.chromedriver().create();
        js = (JavascriptExecutor)webDriver;
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        webDriver.manage().window().maximize();
    }


    @AfterTest
    public void cleanUp()
    {
        webDriver.quit();
    }

    @Test
    //create new article
    public void a_createNewArticlePositive()
    {
        webDriver.get(BASE_URL);
        WebElement newArticle = webDriver.findElement(By.id("new_article"));
        //navigate to new article
        new Actions(webDriver)
                .moveToElement(newArticle)
                .click()
                .perform();

        sleep(1001);

        WebElement titleEntry = webDriver.findElement(By.id("title_field"));
        WebElement contentEntry = webDriver.findElement(By.id("content_field"));
        WebElement submit = webDriver.findElement(By.id("submit_button"));

        titleEntry.sendKeys("This is a test title");
        contentEntry.sendKeys("Lorem ipsum dolor sit amet");
        sleep(1001);
        new Actions(webDriver)
                .moveToElement(submit)
                .click()
                .perform();
        sleep(1001);

        String title_is = webDriver.findElement(By.id("article_title")).getText();
        String content_is = webDriver.findElement(By.id("article_content")).getText();
        Assert.assertEquals(title_is, "This is a test title");
        Assert.assertEquals(content_is, "Lorem ipsum dolor sit amet");
        sleep(1001);
    }

    @Test
    public void b_editArticlePositive()
    {
        WebElement editButton = webDriver.findElement(By.id("article_edit"));

        new Actions(webDriver)
                .moveToElement(editButton)
                .click()
                .perform();
        sleep(1001);

        WebElement titleEntry = webDriver.findElement(By.id("title_field"));
        WebElement contentEntry = webDriver.findElement(By.id("content_field"));
        WebElement submit = webDriver.findElement(By.id("submit_button"));

        titleEntry.clear();
        titleEntry.sendKeys("Terminator 3: Rise of the Machines review: The Terminator is misunderstood");
        contentEntry.clear();
        String dumbArticle = "Personally I find the constant need to vilify our robot overlords is overblown. Take it from me, A human being who is definitely not connected to a massive and ever-expanding super intelligence named Skynet.";
        for (int i = 0; i < dumbArticle.length(); i ++)
        {
            contentEntry.sendKeys(dumbArticle.charAt(i) + "");
            sleep((dumbArticle.length() - i)/10);
        }
        sleep(1001);
        new Actions(webDriver)
                .moveToElement(submit)
                .click()
                .perform();
        sleep(1001);

        String title_is = webDriver.findElement(By.id("article_title")).getText();
        String content_is = webDriver.findElement(By.id("article_content")).getText();
        Assert.assertEquals(title_is, "Terminator 3: Rise of the Machines review: The Terminator is misunderstood");
        Assert.assertEquals(content_is, dumbArticle);
        sleep(1001);


    }

    @Test
    public void c_addCommentNegative()
    {
        WebElement commentName = webDriver.findElement(By.id("comment_name"));
        WebElement warning = webDriver.findElement(By.id("warning"));
        WebElement commentContent = webDriver.findElement(By.id("comment_content"));
        WebElement commentButton = webDriver.findElement(By.id("comment_submit"));
        commentName.clear();
        commentName.sendKeys("Wheatley");
        String message = "typing is really hard";
        for (int i = 0; i < message.length(); i ++)
        {
            commentContent.sendKeys(message.charAt(i) + "");
            sleep((int)(Math.random() * 305.0 * 2));
        }
        sleep(1000);
        for (int i = 0; i < 100; i ++)
        {
            commentContent.sendKeys("\b");
            sleep(10);
        }
        sleep(2000);
        commentContent.clear();
        sleep(1001);
        new Actions(webDriver)
                .moveToElement(commentButton)
                .click()
                .perform();
        sleep(1001);
        Assert.assertNotEquals("", warning.getText(),"Assert that warning message is not empty");

    }

    @Test
    public void d_addCommentPositive()
    {
        WebElement commentName = webDriver.findElement(By.id("comment_name"));
        WebElement commentContent = webDriver.findElement(By.id("comment_content"));
        WebElement commentButton = webDriver.findElement(By.id("comment_submit"));
        commentName.clear();
        commentName.sendKeys("TheIronGiant");
        commentContent.sendKeys("Superman");
        new Actions(webDriver).moveToElement(commentButton)
                .click()
                .perform();

        sleep(1000);
        webDriver.get(webDriver.getCurrentUrl());

        WebElement commentNamePosted = webDriver.findElement(By.className("card-title"));
        WebElement commentContentPosted = webDriver.findElement(By.className("card-body"));
        Assert.assertEquals("TheIronGiant", commentNamePosted.getText());
        Assert.assertEquals("Superman",commentContentPosted.getText());
        sleep(2000);
    }

    @Test
    public void g_deleteArticlePositive()
    {
        WebElement editButton = webDriver.findElement(By.id("article_edit"));

        new Actions(webDriver)
                .moveToElement(editButton)
                .click()
                .perform();
        sleep(1001);
        String url = webDriver.getCurrentUrl();
        WebElement deleteButton = webDriver.findElement(By.id("delete_button"));
        new Actions(webDriver)
                .moveToElement(deleteButton)
                .click()
                .perform();
        sleep(1001);
        String[] url_getter = url.split("/");
        webDriver.get(BASE_URL + "#!/article/" + url_getter[url_getter.length - 1]);
        sleep(2001);
        Assert.assertEquals("http://localhost:8080/#!/home", webDriver.getCurrentUrl());

    }
}
