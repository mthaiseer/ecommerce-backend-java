package com.shopme.request;


import com.shopme.entity.Role;
import com.shopme.entity.User;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdateRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String photo;
    private boolean enabled;
    List<Long> roles;

    public User to(){

        User user = User.builder()
                .email(this.email)
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
