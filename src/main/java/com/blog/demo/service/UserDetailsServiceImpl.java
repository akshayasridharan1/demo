package com.blog.demo.service;

import com.blog.demo.exception.UserPostsNotFoundException;
import com.blog.demo.model.Post;
import com.blog.demo.model.User;
import com.blog.demo.model.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.blog.demo.utility.Util.API_URI_GET_ALL_USERS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<User> getAllUsers() {
        ResponseEntity<List<User>> userDetailsResponse =
                restTemplate.exchange(API_URI_GET_ALL_USERS, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        Optional<List<User>> users = Optional.ofNullable(userDetailsResponse.getBody());

        if(users.isEmpty()) {
            throw new UserPostsNotFoundException("No users and no posts are available");
        }
        return users.get();
    }

    @Override
    public List<User> getAllUsersWithPagination(Optional<List<User>> users, Integer pageNo, Integer pageSize) {
        if(users.isPresent()) {
            if (pageNo + pageSize > users.get().size())
                throw new UserPostsNotFoundException(" pageNo and pageSize together cannot exceed the available  list : " + users.get().size());
        }
        else
        {
            throw new UserPostsNotFoundException("users not found exception");
        }
        return users.get().subList(pageNo, pageNo + pageSize);
    }

    @Override
        public List<UserPost> getAllUserDetailsForAllPosts(Optional<List<User>> users, Optional<List<Post>> posts)
    {
        List<UserPost> userPosts = new ArrayList<>();
        if(users.isPresent()) {
            for (User user : users.get()) {
                UserPost userPost = new UserPost();
                userPost.setUser(user);
                    posts.ifPresent(postDetails -> userPost.setPosts(postDetails.stream()
                            .filter(p -> p.getUserId().equals(user.getId()))
                            .collect(Collectors.toList())));
                    if(Objects.nonNull(userPost.getPosts()))
                    userPost.setNumOfPosts(userPost.getPosts().size());
                    userPosts.add(userPost);

            }
        }
        return userPosts;
    }

}
