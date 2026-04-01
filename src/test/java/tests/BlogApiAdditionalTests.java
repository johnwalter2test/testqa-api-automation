package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import models.Comment;
import models.Post;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.CommentService;
import services.PostService;
import services.UserService;
import utils.EmailValidator;

import java.util.List;

@Epic("Blog API - Additional Coverage")
@Feature("Additional validations for posts and comments")
public class BlogApiAdditionalTests {

    @Test
    @Story("Posts belong to the requested user")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPostsBelongToUser() {
        User user = UserService.getUserByUsername("Delphine");
        List<Post> posts = PostService.getPostsByUser(user.getId());

        for (Post post : posts) {
            Assert.assertEquals(post.getUserId(), user.getId(), "Post userId does not match expected user id");
        }
    }

    @Test
    @Story("Comments have non-null emails and valid format")
    @Severity(SeverityLevel.CRITICAL)
    public void commentsHaveEmailsAndValidFormat() {
        User user = UserService.getUserByUsername("Delphine");
        List<Post> posts = PostService.getPostsByUser(user.getId());

        for (Post post : posts) {
            List<Comment> comments = CommentService.getCommentsByPost(post.getId());
            for (Comment comment : comments) {
                String email = comment.getEmail();
                Assert.assertNotNull(email, "Comment email is null for postId=" + post.getId() + " commentId=" + comment.getId());
                Assert.assertFalse(email.trim().isEmpty(), "Comment email is empty for postId=" + post.getId() + " commentId=" + comment.getId());
                Assert.assertTrue(EmailValidator.isValid(email), "Comment email is invalid: " + email + " (postId=" + post.getId() + ")");
            }
        }
    }
}
