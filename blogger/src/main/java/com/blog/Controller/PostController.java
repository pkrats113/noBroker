package com.blog.Controller;

import com.blog.Payload.PostDto;
import com.blog.Service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto=postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId){
        String message=postService.deletePost(postId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @PutMapping("/view")
    public ResponseEntity<PostDto> updatePost(@RequestParam long postId,@RequestBody PostDto postDto){
        PostDto dto=postService.updatePost(postId,postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost(
            @RequestParam(name="pageNo",defaultValue = "0",required = false)int pageNo,
            @RequestParam(name="pageSize",defaultValue = "3",required = false)int pageSize,
            @RequestParam(name="sortBy",defaultValue = "id",required = false)String sortBy,
            @RequestParam(name="sortDir",defaultValue = "ASC",required = false)String sortDir
    ){
        List<PostDto> dto=postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


}
