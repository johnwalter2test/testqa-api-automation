package services;

import client.ApiClient;
import io.restassured.response.Response;
import models.Post;

import java.util.List;

public class PostService {

    private final ApiClient apiClient;

    public PostService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response getPosts() {
        return apiClient.get("/posts");
    }

    public static List<Post> getPostsByUser(int userId) {
        Response res = ApiClient.get("/posts?userId=" + userId);
        return res.jsonPath().getList("", Post.class);
    }
}