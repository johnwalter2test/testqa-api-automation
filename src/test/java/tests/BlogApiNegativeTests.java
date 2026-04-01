package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.CommentService;
import services.PostService;
import services.UserService;
import utils.EmailValidator;

import java.util.List;

@Epic("Blog API - Negative Tests")
@Feature("Edge cases and failures")
public class BlogApiNegativeTests {

    @Test
    @Story("Username not found should throw")
    @Severity(SeverityLevel.NORMAL)
    public void usernameNotFound() {
        try {
            User user = UserService.getUserByUsername("NonExistingUser_12345");
            Assert.fail("Expected RuntimeException for missing user, got: " + user);
        } catch (RuntimeException e) {
            Assert.assertTrue(e.getMessage().contains("User not found"));
        }
    }

    @Test
    @Story("User with no posts returns empty list")
    @Severity(SeverityLevel.MINOR)
    public void userHasNoPosts() {
        // Use a hypothetical user id that likely has no posts (e.g., 9999)
        List posts = PostService.getPostsByUser(9999);
        Assert.assertTrue(posts.isEmpty() || posts.size() == 0);
    }

    @Test
    @Story("Post with no comments returns empty list")
    @Severity(SeverityLevel.MINOR)
    public void postHasNoComments() {
        // Use a hypothetical post id that likely has no comments
        List comments = CommentService.getCommentsByPost(999999);
        Assert.assertTrue(comments.isEmpty() || comments.size() == 0);
    }

    @Test
    @Story("Email validation edge cases")
    @Severity(SeverityLevel.CRITICAL)
    public void emailValidationEdgeCases() {
        Assert.assertFalse(EmailValidator.isValid(null));
        Assert.assertFalse(EmailValidator.isValid(""));
        Assert.assertFalse(EmailValidator.isValid("abc.com"));
        Assert.assertFalse(EmailValidator.isValid("@mail.com"));
        Assert.assertFalse(EmailValidator.isValid("test@.com"));
    }
}