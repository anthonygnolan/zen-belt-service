package com.coderdojo.zen.belt;

import com.coderdojo.zen.belt.model.Belt;
import com.coderdojo.zen.belt.model.Category;
import com.coderdojo.zen.belt.exceptions.BeltNotFoundException;
import com.coderdojo.zen.belt.repositories.BeltRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
import java.util.stream.StreamSupport;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeltTests {

    @Autowired
    private BeltRepository beltRepository;

    // JUnit test for saveBelt
    @Test
    @Order(1)
    @Rollback(value = false)
    void saveBeltTest(){

        Belt belt = new Belt(1L, "Scratch", "MacBook Pro", Category.PROGRAMMING);

        beltRepository.save(belt);

        Assertions.assertThat(belt.getId()).isPositive();
    }

    @Test
    @Order(2)
    void getBeltTest(){

        Belt belt = beltRepository.findById(1L).orElseThrow(() -> new BeltNotFoundException(1L));

        Assertions.assertThat(belt.getId()).isEqualTo(1L);

    }

    @Test
    @Order(3)
    void getListOfBeltsTest(){

        Iterable<Belt> belts = beltRepository.findAll();

        Assertions.assertThat(StreamSupport.stream(belts.spliterator(), false).count()).isPositive();

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void updateBeltTest(){

        Belt belt = beltRepository.findById(1L).orElseThrow(() -> new BeltNotFoundException(1L));

        belt.setDescription("New Description");
        belt.setCategory(Category.PROGRAMMING);

        Belt beltUpdated =  beltRepository.save(belt);

        Assertions.assertThat(beltUpdated.getDescription()).isEqualTo("New Description");
        Assertions.assertThat(beltUpdated.getCategory()).isEqualTo(Category.PROGRAMMING);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    void deleteBeltTest(){

        Belt belt = beltRepository.findById(1L).orElseThrow(() -> new BeltNotFoundException(1L));

        beltRepository.delete(belt);

        //beltRepository.deleteById(1L);

        Belt belt1 = null;

        Optional<Belt> optionalBelt = beltRepository.findById(1L);

        if(optionalBelt.isPresent()){
            belt1 = optionalBelt.get();
        }

        Assertions.assertThat(belt1).isNull();
    }
}


