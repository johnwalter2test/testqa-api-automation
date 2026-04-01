package services;

import client.ApiClient;
import io.restassured.response.Response;
import models.User;

import java.util.List;

public class UserService {

    private final ApiClient apiClient;

    public UserService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response getUsers() {
        return apiClient.get("/users");
    }

    public static User getUserByUsername(String username) {
        Response res = ApiClient.get("/users");

        List<User> users = res.jsonPath().getList("", User.class);
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}