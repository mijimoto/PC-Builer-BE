
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.UnitsDTO;
import com.project.PCBuilder.rest.services.UnitsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/units", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnitsRestController {

    private static final Logger logger = LoggerFactory.getLogger(UnitsRestController.class);

    private final UnitsService service;

    @Autowired
    public UnitsRestController(UnitsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UnitsDTO>> findAll() {
        logger.debug("GET - findAll");
        List<UnitsDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{unitid}")
    public ResponseEntity<UnitsDTO> findById(@PathVariable Integer unitid) {
        logger.debug("GET - findById");
        UnitsDTO dto = service.findById(unitid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody UnitsDTO unitsDTO) {
        logger.debug("POST - create");
        if (service.create(unitsDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{unitid}")
    public ResponseEntity<Void> save(@PathVariable Integer unitid, @RequestBody UnitsDTO unitsDTO) {
        logger.debug("PUT - save");
        service.save(unitid, unitsDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UnitsDTO unitsDTO) {
        logger.debug("PUT - update");
        if (service.update(unitsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{unitid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer unitid, @RequestBody UnitsDTO unitsDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(unitid, unitsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{unitid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer unitid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(unitid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
