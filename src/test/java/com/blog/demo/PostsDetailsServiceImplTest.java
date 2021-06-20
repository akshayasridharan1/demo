package com.blog.demo;

import com.blog.demo.model.Post;
import com.blog.demo.service.PostsDetailsServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.blog.demo.utility.Util.API_URI_GEL_ALL_POSTS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class PostsDetailsServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private final PostsDetailsServiceImpl postsDetailsService = new PostsDetailsServiceImpl();

    List<Post> posts;

    @Before
    public void setupAll()
    {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        posts = new ArrayList<>();
        posts.add(new Post("1", "1001", "sunt aut facere repellat ", "provident occaecati excepturi optio reprehenderi"));
        posts.add(new Post("2", "1002", "sunt aut facere repellat ", "provident occaecati excepturi optio reprehenderi"));
        posts.add(new Post("3", "1003", "sunt aut facere repellat ", "provident occaecati excepturi optio reprehenderi"));
        posts.add(new Post("4", "1004", "sunt aut facere repellat ", "provident occaecati excepturi optio reprehenderi"));
        posts.add(new Post("5", "1005", "sunt aut facere repellat ", "provident occaecati excepturi optio reprehenderi"));
    }

    @Test
    public void getAllPostsRestTemplateCallToGetPostsApiTest()
    {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.exchange(API_URI_GEL_ALL_POSTS, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getAllPostsMockitoTest()
    {
        Mockito.when(restTemplate.exchange(API_URI_GEL_ALL_POSTS, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                })).thenReturn(new ResponseEntity<>(posts , HttpStatus.OK));
        List<Post> postList = postsDetailsService.getAllPosts();
        Assertions.assertNotNull(postList);
        Assertions.assertEquals(posts.size(), postList.size());
    }
}
