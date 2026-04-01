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

@Epic("Blog API")
@Feature("Email validation for comments")
public class BlogApiTest {

    @Test
    @Story("Validate emails for a specific user's posts")
    @Severity(SeverityLevel.CRITICAL)
    public void validateEmailsForUserPosts() {

        // Step 1: Get user
        User user = UserService.getUserByUsername("Delphine");

        // Step 2: Get posts
        List<Post> posts = PostService.getPostsByUser(user.getId());

        for (Post post : posts) {

            // Step 3: Get comments
            List<Comment> comments = CommentService.getCommentsByPost(post.getId());

            for (Comment comment : comments) {

                // Step 4: Validate email
                Assert.assertTrue(
                        EmailValidator.isValid(comment.getEmail()),
                        "Invalid email: " + comment.getEmail()
                );
            }
        }
    }
}