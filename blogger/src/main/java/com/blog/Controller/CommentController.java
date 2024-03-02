package com.blog.Controller;

import com.blog.Payload.CommentDto;
import com.blog.Service.CommentService;
import com.blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @PostMapping
    public ResponseEntity<CommentDto> createComments(@RequestParam long postId, @RequestBody CommentDto commentDto){
        CommentDto dto=commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId){
        String message=commentService.deleteComment(commentId);
        return new ResponseEntity<>(message,HttpStatus.OK);

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentByPost(@PathVariable long postId){
        List<CommentDto>dto=commentService.getCommentsByPost(postId);
        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto>dto=commentService.getAllComments();
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
