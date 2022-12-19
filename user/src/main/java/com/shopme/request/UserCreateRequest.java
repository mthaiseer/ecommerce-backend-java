package com.shopme.request;


import com.shopme.entity.Role;
import lombok.*;
 import com.shopme.entity.User;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateRequest {

    private String email;
    private  String password;
    private String firstName;
    private String lastName;
    private String photo;
    private boolean enabled;
    List<Long> roles;

    public User to(){

        User user = User.builder()
                .email(this.email)
                .password(this.password)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .photo(this.photo)
                .enabled(this.enabled)
                .build();
        this.roles.forEach(it-> {

            user.addRole(
                    Role.builder()
                            .id(it)
                            .build());

                });

          return  user;
    }




}
