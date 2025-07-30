
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.ComponentsDTO;
import com.project.PCBuilder.rest.services.ComponentsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/components", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComponentsRestController {

    private static final Logger logger = LoggerFactory.getLogger(ComponentsRestController.class);

    private final ComponentsService service;

    @Autowired
    public ComponentsRestController(ComponentsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ComponentsDTO>> findAll() {
        logger.debug("GET - findAll");
        List<ComponentsDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{componentid}")
    public ResponseEntity<ComponentsDTO> findById(@PathVariable Integer componentid) {
        logger.debug("GET - findById");
        ComponentsDTO dto = service.findById(componentid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ComponentsDTO componentsDTO) {
        logger.debug("POST - create");
        if (service.create(componentsDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{componentid}")
    public ResponseEntity<Void> save(@PathVariable Integer componentid, @RequestBody ComponentsDTO componentsDTO) {
        logger.debug("PUT - save");
        service.save(componentid, componentsDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ComponentsDTO componentsDTO) {
        logger.debug("PUT - update");
        if (service.update(componentsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{componentid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer componentid, @RequestBody ComponentsDTO componentsDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(componentid, componentsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{componentid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer componentid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(componentid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
