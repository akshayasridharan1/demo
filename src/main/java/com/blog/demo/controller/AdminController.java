package com.blog.demo.controller;

import com.blog.demo.model.Post;
import com.blog.demo.model.User;
import com.blog.demo.model.UserPost;
import com.blog.demo.service.PostsDetailsService;
import com.blog.demo.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("blogApp/admin")
public class AdminController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PostsDetailsService postsDetailsService;

    @GetMapping("/getAllUsersPosts")
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize)
    {

        Optional<List<User>> users = Optional.ofNullable(userDetailsService.getAllUsers());

        Optional<List<User>> usersWithPagination = Optional.ofNullable(userDetailsService.getAllUsersWithPagination(users, pageNo, pageSize));

        Optional<List<Post>> posts = Optional.ofNullable(postsDetailsService.getAllPosts());

        Optional<List<UserPost>> userPosts = Optional.ofNullable(userDetailsService.getAllUserDetailsForAllPosts(usersWithPagination,posts));

        Map<String, Object> response = new HashMap<>();
        response.put("userPosts", userPosts);
        response.put("totalItems", users.get().size());
        response.put("currentItems", pageSize);
        response.put("currentPage", pageNo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}