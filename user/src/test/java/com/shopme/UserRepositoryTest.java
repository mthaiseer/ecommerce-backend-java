package com.shopme;

import com.shopme.entity.Role;
import com.shopme.entity.User;
import com.shopme.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){

        Role role = entityManager.find(Role.class, 1L);
        User user = User.builder()
                 .email("mohamed@gmail.com")
                 .firstName("Mohamed")
                 .lastName("Thaiseer")
                 .password("test123")
                 .build();
        user.addRole(role);
        userRepository.save(user);
        assertThat(user.getId()).isGreaterThan(0);

    }

    @Test
    public void addTwoRolesToUser(){
        Role adminRole = entityManager.find(Role.class, 1L);
        Role editorRole = entityManager.find(Role.class, 3L);
        User user = User.builder()
                .email("Safa@gmail.com")
                .firstName("safa")
                .lastName("Thaiseer")
                .password("test123")
                .build();
        user.addRole(adminRole);
        user.addRole(editorRole);
        userRepository.save(user);
        assertThat(user.getId()).isGreaterThan(0);

    }

    @Test
    public void testGetAllUsers(){
        Iterable<User> allUsers = userRepository.findAll();
        List<User> users = new ArrayList<>();

        allUsers.forEach(it->{
            users.add(it);
        });
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void getOneUser(){
        User user = userRepository.findById(1L).get();
        assertNotNull(user);
        assertThat(user.getFirstName()).isEqualTo("Mohamed");
    }

    @Test
    public void testUpdateUser(){
        User user = userRepository.findById(5L).get();
        user.setFirstName("Muhammed");
        user.setEnabled(true);
        userRepository.save(user);
        assertThat(user.isEnabled()).isEqualTo(true);

    }

    @Test
    public void testUserRole(){
        User user = userRepository.findById(2L).get();
        Role adminRole = entityManager.find(Role.class, 1L);
        user.getRoles().remove(adminRole);
        userRepository.save(user);
        assertThat(user.getRoles().size()).isEqualTo(1);

    }

    @Test
    public void checkEmailUniqueness(){
        String existingEmail = "mohamed@gmail.com";
        User user  =  userRepository.findByEmail(existingEmail);
        assertThat(user).isNotNull();
    }

    @Test
    public void testPagingData(){

        int pageNumber = 1;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<User> allUsers = userRepository.findAll(pageable);
        List<User> users = allUsers.getContent();
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(pageSize);


    }

    @Test
    public void testSortingData(){

        int pageNumber = 1;
        int pageSize = 4;

        Sort sort = Sort.by("firstName");
        sort.ascending();

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<User> allUsers = userRepository.findAll(pageable);
        List<User> users = allUsers.getContent();

        users.forEach(it->{
            System.out.println(it.getFirstName());
        });

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(pageSize);
        assertThat(users.get(0).getEmail()).isEqualTo("nashwa@gmail.com");


    }

    @Test
    public void testFilteringData(){

        int pageNumber = 1;
        int pageSize = 4;

        String keyword = "safa thaiseer";

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<User> allUsers = userRepository.findAll(keyword, pageable);
        List<User> users = allUsers.getContent();

        users.forEach(it->{
            System.out.println(it);
        });

        assertThat(users).isNotNull();
        assertThat(users.size()).isGreaterThan(0);



    }





}
