package com.blog.Service.ServiceImpl;

import com.blog.Entity.Comment;
import com.blog.Entity.Post;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.CommentDto;
import com.blog.Repository.CommentRepository;
import com.blog.Repository.PostRepository;
import com.blog.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId)
        );
        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto=new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(commentDto.getName());
        dto.setEmail(commentDto.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }

    @Override
    public String deleteComment(long commentId) {
        commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment not found with id: "+commentId)
        );
        commentRepository.deleteById(commentId);
        return "comment is deleted";
    }

    @Override
    public List<CommentDto> getCommentsByPost(long postId) {
        List<Comment> comments = commentRepository.findCommentByPostId(postId);
        List<CommentDto> dto = comments.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> all = commentRepository.findAll();
        List<CommentDto> collect = all.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return collect;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto=new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}
