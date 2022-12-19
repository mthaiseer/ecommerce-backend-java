package com.shopme;

import com.shopme.entity.Role;
import com.shopme.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole(){


        Role role = Role.builder()
                .name("Admin")
                .description("manage everything")
                .build();
        roleRepository.save(role);
        assertThat(role.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateOtherRole(){

        Role salesPerson = Role.builder()
                .name("Salesperson")
                .description("Manage product price, customer, shipping, orders and sales report")
                .build();

        Role editor = Role.builder()
                .name("Editor")
                .description("Manage categories, brands, products, articles and menus")
                .build();

        Role shipper = Role.builder()
                .name("Shipper")
                .description("View products, view orders and update order status")
                .build();

        Role assistant = Role.builder()
                .name("Assistant")
                .description("Manage questions and reviews")
                .build();

        roleRepository.saveAll(List.of(salesPerson, editor,shipper,  assistant));

    }


}
