package com.aidictionary.ai_dictionary.repositories;

import com.aidictionary.ai_dictionary.models.Role;
import com.aidictionary.ai_dictionary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
