
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.OpticaldriveDTO;
import com.project.PCBuilder.rest.services.OpticaldriveService;


@RestController
@RequestMapping(value = "/api/v1/opticaldrive", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpticaldriveRestController {

    private static final Logger logger = LoggerFactory.getLogger(OpticaldriveRestController.class);

    private final OpticaldriveService service;

    @Autowired
    public OpticaldriveRestController(OpticaldriveService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OpticaldriveDTO>> findAll() {
        logger.debug("GET - findAll");
        List<OpticaldriveDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}")
    public ResponseEntity<OpticaldriveDTO> findById(@PathVariable Integer partid) {
        logger.debug("GET - findById");
        OpticaldriveDTO dto = service.findById(partid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody OpticaldriveDTO opticaldriveDTO) {
        logger.debug("POST - create");
        if (service.create(opticaldriveDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @RequestBody OpticaldriveDTO opticaldriveDTO) {
        logger.debug("PUT - save");
        service.save(partid, opticaldriveDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody OpticaldriveDTO opticaldriveDTO) {
        logger.debug("PUT - update");
        if (service.update(opticaldriveDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @RequestBody OpticaldriveDTO opticaldriveDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, opticaldriveDTO)) {
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
