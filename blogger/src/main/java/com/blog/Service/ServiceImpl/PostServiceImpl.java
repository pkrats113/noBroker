package com.blog.Service.ServiceImpl;

import com.blog.Entity.Post;
import com.blog.Exception.ResourceNotFoundException;
import com.blog.Payload.PostDto;
import com.blog.Repository.PostRepository;
import com.blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToPost(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        dto.setMessage("post is created");
        return dto;

    }

    @Override
    public String deletePost( long postId) {
        postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post not found with id: "+postId)
        );
        postRepository.deleteById(postId);
        return "post is deleted with id: "+postId;
    }

    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + postId)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        dto.setMessage("post is updated in id: "+postId);

        return dto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String dir) {
       Sort sort=dir.equalsIgnoreCase(Sort.Direction.ASC.name())?
               Sort.by(sortBy).ascending():
               Sort.by(sortBy).descending();
       Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> content = all.getContent();
        List<PostDto> dto = content.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        return dto;
    }


    Post mapToPost(PostDto postDto){
        Post post =new Post();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }

    PostDto mapToDto(Post post){
        PostDto dto=new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        return dto;
    }
}
