package lv.bootcamp.shelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lv.bootcamp.shelter.dto.AnimalCreateRequest;
import lv.bootcamp.shelter.dto.AnimalResponse;
import lv.bootcamp.shelter.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for shelter animal endpoints.
 * Returns JSON — does not render HTML pages.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/animals")
@Tag(name = "Items", description = "Manage the item list")
public class AnimalApiController {

    private final AnimalService animalService;

    @GetMapping
    @Operation(summary = "List all items", description = "Returns all items currently in memory.")
    @ApiResponse(responseCode = "200", description = "Items returned successfully")
    public List<AnimalResponse> findAll() {
        return animalService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get selected animal", description = "Returns item which has the provided id")
    @ApiResponse(responseCode = "200", description = "Item returned successfully")
    @ApiResponse(responseCode = "404", description = "Item not found")
    public ResponseEntity<AnimalResponse> findById(
            @Parameter(description = "Unique id of the animal", example = "1")
            @PathVariable Long id) {
        return animalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lists adopted animals.
     */
    @GetMapping("/adopted")
    @Operation(summary = "List adopted animals", description = "Returns the list adopted animals")
    @ApiResponse(responseCode = "200", description = "Items returned successfully")
    public List<AnimalResponse> findAdopted() {
        return animalService.findAdopted();
    }

    /**
     * Creates a new animal.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create item", description = "Creates the new animal")
    @ApiResponse(responseCode = "200", description = "Item created successfully")
    public AnimalResponse create(@RequestBody @Valid AnimalCreateRequest request) {
        return animalService.create(request);
    }

    /**
     * Adopts an animal
     */
    @PostMapping("/{id}/adopt")
    @Operation(summary = "Change to adopted", description = "Adopt animal which has the provided id")
    @ApiResponse(responseCode = "200", description = "Item adopted successfully")
    @ApiResponse(responseCode = "404", description = "Item not found")
    @ApiResponse(responseCode = "409", description = "Item already adopted")
    public ResponseEntity<AnimalResponse> adopt(
            @Parameter(description = "Unique id of the animal", example = "1")
            @PathVariable Long id) {
        return animalService.adopt(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleAlreadyAdopted(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
