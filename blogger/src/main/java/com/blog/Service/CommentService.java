package com.blog.Service;

import com.blog.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    String deleteComment(long commentId);

    List<CommentDto> getCommentsByPost(long postId);

    List<CommentDto> getAllComments();
}
