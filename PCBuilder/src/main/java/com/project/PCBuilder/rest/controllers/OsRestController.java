
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.OsDTO;
import com.project.PCBuilder.rest.services.OsService;


@RestController
@RequestMapping(value = "/api/v1/os", produces = MediaType.APPLICATION_JSON_VALUE)
public class OsRestController {

    private static final Logger logger = LoggerFactory.getLogger(OsRestController.class);

    private final OsService service;

    @Autowired
    public OsRestController(OsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OsDTO>> findAll() {
        logger.debug("GET - findAll");
        List<OsDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}")
    public ResponseEntity<OsDTO> findById(@PathVariable Integer partid) {
        logger.debug("GET - findById");
        OsDTO dto = service.findById(partid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody OsDTO osDTO) {
        logger.debug("POST - create");
        if (service.create(osDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @RequestBody OsDTO osDTO) {
        logger.debug("PUT - save");
        service.save(partid, osDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody OsDTO osDTO) {
        logger.debug("PUT - update");
        if (service.update(osDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @RequestBody OsDTO osDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, osDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{partid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer partid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(partid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
