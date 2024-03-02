package com.blog.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    private long id;
    private String name;
    private String email;
    private String password;
    private String username;
}
