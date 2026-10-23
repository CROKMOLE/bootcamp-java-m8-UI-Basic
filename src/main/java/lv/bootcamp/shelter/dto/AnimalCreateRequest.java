package lv.bootcamp.shelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * JSON request body for creating a new animal via the REST API.
 * Status is not included; all new animals start as AVAILABLE.
 */
public record AnimalCreateRequest(

        @NotBlank(message = "Name is required")
        @Schema(description = "Name that received an animal")
        String name,

        @NotNull(message = "Type is required")
        @Schema(description = "Species of the animal")
        AnimalType type,

        @Schema(description = "The bread of the species of current animal")
        String breed,

        @Min(value = 0, message = "Age cannot be negative")
        @Schema(description = "The age of the animal")
        Integer age,

        @Schema(description = "Some text that describes current animal")
        String description,

        @Schema(description = "The path to the image which is placed on the server")
        String imageUrl
) {}