package com.aidictionary.ai_dictionary.repositories;

import com.aidictionary.ai_dictionary.models.EnglishWord;
import com.aidictionary.ai_dictionary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
