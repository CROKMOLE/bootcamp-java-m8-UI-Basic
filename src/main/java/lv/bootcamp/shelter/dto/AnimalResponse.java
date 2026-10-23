package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * Response body for a single animal returned by the API.
 * {@code adoptionNote} is only populated for ADMIN callers (see AnimalService#toResponse) —
 * everyone else just sees the plain {@code status}.
 */
public record AnimalResponse(

        @Schema(description = "The unique id that is placed to the animal")
        Long id,

        @Schema(description = "Name that received an animal")
        String name,

        @Schema(description = "Species of the animal")
        AnimalType type,

        @Schema(description = "The bread of the species of current animal")
        String breed,

        @Schema(description = "The age of the animal")
        Integer age,

        @Schema(description = "Some text that describes current animal")
        String description,

        @Schema(description = "The state of the animal (AVAILABLE, ADOPTED etc.)")
        AnimalStatus status,

        @Schema(description = "The path to the image which is placed on the server")
        String imageUrl,

        @Schema(description = "The note that describes the adoption")
        String adoptionNote
) {}
