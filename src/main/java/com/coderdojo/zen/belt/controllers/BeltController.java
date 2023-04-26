package com.coderdojo.zen.belt.controllers;

import com.coderdojo.zen.belt.dto.BeltDTO;
import com.coderdojo.zen.belt.model.Belt;
import com.coderdojo.zen.belt.exceptions.BeltModelAssembler;
import com.coderdojo.zen.belt.exceptions.BeltNotFoundException;
import com.coderdojo.zen.belt.model.Category;
import com.coderdojo.zen.belt.repositories.BeltRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
/**
 * Returns an Image object that can then be painted on the screen.
 * The url argument must specify an absolute. The name
 * argument is a specifier that is relative to the url argument.
 * <p>
 * This method always returns immediately, whether the
 * image exists. When this applet attempts to draw the image on
 * the screen, the data will be loaded. The graphics primitives
 * that draw the image will incrementally paint on the screen.
 *
 */
@RestController
@RequestMapping("/api/v1")
public class BeltController {

    private final BeltRepository beltRepository;

    /**
     * The public name of a hero that is common knowledge
     */
    private final BeltModelAssembler beltAssembler;

    BeltController(BeltRepository beltRepository, BeltModelAssembler assembler) {

        this.beltRepository = beltRepository;
        this.beltAssembler = assembler;
    }

    /**
     * Hero is the main entity we'll be using to . . .
     * Please see the class for true identity
     * @author Captain America
     * @param category the amount of incoming damage
     * @return EntityModel
     */
    @GetMapping("/belts")
    public CollectionModel<EntityModel<Belt>> getBelts(@RequestParam(required = false, name = "category")Category category) {

        List<EntityModel<Belt>> belts;
        if (category != null) {
            belts = beltRepository.findByCategory(category).stream() //
                    .map(beltAssembler::toModel) //
                    .toList();
        } else {
            belts = beltRepository.findAll().stream() //
                    .map(beltAssembler::toModel) //
                    .toList();
        }

        return CollectionModel.of(belts, linkTo(methodOn(BeltController.class).getBelts(null)).withSelfRel());
    }

    @PostMapping("/belts")
    ResponseEntity<EntityModel<Belt>> createBelt(@RequestBody BeltDTO beltDTO) {

        EntityModel<Belt> entityModel = beltAssembler.toModel(beltRepository.save(
                new Belt(null, beltDTO.getName(), beltDTO.getDescription(), beltDTO.getCategory())
        ));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
    /**
     * <p>This is a simple description of the method. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a>
     * </p>
     * @param id the amount of incoming damage
     * @return the amount of health hero has after attack
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */
    @GetMapping("/belts/{id}")
    public EntityModel<Belt> getBelt(@PathVariable Long id) {

        Belt belt = beltRepository.findById(id) //
                .orElseThrow(() -> new BeltNotFoundException(id));

        return beltAssembler.toModel(belt);
    }

    @PutMapping("/belts/{id}")
    ResponseEntity<Object> updateBelt(@PathVariable Long id, @RequestBody BeltDTO beltDTO) {

        Belt belt = beltRepository.findById(id)
                .orElseThrow(() -> new BeltNotFoundException(id));

        belt.setName(beltDTO.getName());
        belt.setDescription(beltDTO.getDescription());
        belt.setCategory(beltDTO.getCategory());

        return ResponseEntity.ok(beltAssembler.toModel(beltRepository.save(belt)));
    }

    @DeleteMapping("/belts/{id}")
    ResponseEntity<Void> deleteBelt(@PathVariable Long id) {

        beltRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}