package com.daelim.daelim_hackathon.author.repo;

import com.daelim.daelim_hackathon.author.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
