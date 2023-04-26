package com.coderdojo.zen.belt.exceptions;

import com.coderdojo.zen.belt.controllers.BeltController;
import com.coderdojo.zen.belt.model.Belt;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BeltModelAssembler implements RepresentationModelAssembler<Belt, EntityModel<Belt>> {

    @Override
    public EntityModel<Belt> toModel(Belt belt) {

        // Unconditional links to single-item resource and aggregate root

        return EntityModel.of(belt,
                linkTo(methodOn(BeltController.class).getBelt(belt.getId())).withSelfRel(),
                linkTo(methodOn(BeltController.class).getBelts(null)).withRel("belts"));
    }
}