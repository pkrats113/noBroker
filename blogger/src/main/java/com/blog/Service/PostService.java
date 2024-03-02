package com.blog.Service;

import com.blog.Payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    String deletePost(long postId);

    PostDto updatePost(long postId, PostDto postDto);

    List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String dir);
}
