package com.shopme.repository;

import com.shopme.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByEmail(String existingEmail);

    @Query("SELECT u FROM User u  where CONCAT(u.firstName, ' ', u.lastName, ' ') like %?1% ")
    Page<User> findAll(String keyword, Pageable pageable);


}
