package com.blog.demo.service;

import com.blog.demo.model.Post;
import com.blog.demo.model.User;
import com.blog.demo.model.UserPost;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserDetailsService {


    public List<UserPost> getAllUserDetailsForAllPosts(Optional<List<User>> users, Optional<List<Post>> posts);

    public List<User> getAllUsers();

    public List<User> getAllUsersWithPagination(Optional<List<User>> users, Integer pageNo, Integer pageSize);

}
