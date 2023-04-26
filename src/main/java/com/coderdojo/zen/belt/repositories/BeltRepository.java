package com.coderdojo.zen.belt.repositories;

import com.coderdojo.zen.belt.model.Belt;
import com.coderdojo.zen.belt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeltRepository extends JpaRepository<Belt, Long> {
    List<Belt> findByCategory(Category category);
}