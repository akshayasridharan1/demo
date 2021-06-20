package com.blog.demo.service;

import com.blog.demo.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostsDetailsService {

    public List<Post> getAllPosts();

}
