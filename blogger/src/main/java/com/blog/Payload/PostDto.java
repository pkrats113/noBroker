package com.blog.Payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min=2,message="title cannot be less than 4 character")
    private String title;
    @NotEmpty
    @Size(min=2,message="content cannot be less than 4 character")
    private String content;
    @NotEmpty
    @Size(min=2,message="description cannot be less than 4 character")
    private String description;

    private String message;

}
