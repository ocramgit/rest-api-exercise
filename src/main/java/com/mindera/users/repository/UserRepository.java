package com.mindera.users.repository;

import com.mindera.users.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Cacheable("users")
    List<User> findByName(String name);
    boolean existsByName(String name);
    List<User> findByPassword(String password);
}
