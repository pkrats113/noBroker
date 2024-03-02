package com.blog.Repository;

import com.blog.Entity.Comment;
import com.blog.Payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findCommentByPostId(long postId);
}
