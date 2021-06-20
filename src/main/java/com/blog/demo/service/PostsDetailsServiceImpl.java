package com.blog.demo.service;

import com.blog.demo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.blog.demo.utility.Util.API_URI_GEL_ALL_POSTS;

@Service
public class PostsDetailsServiceImpl implements PostsDetailsService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Post> getAllPosts() {

        ResponseEntity<List<Post>> postDetailsResponse =
                restTemplate.exchange(API_URI_GEL_ALL_POSTS, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        return postDetailsResponse.getBody();
    }
}
