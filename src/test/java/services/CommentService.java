package services;

import client.ApiClient;
import io.restassured.response.Response;
import models.Comment;

import java.util.List;

public class CommentService {

    private final ApiClient apiClient;

    public CommentService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response getComments() {
        return apiClient.get("/comments");
    }

    public static List<Comment> getCommentsByPost(int postId) {
        Response res = ApiClient.get("/comments?postId=" + postId);
        return res.jsonPath().getList("", Comment.class);
    }
}