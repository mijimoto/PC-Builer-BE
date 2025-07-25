
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.MotherboardDTO;
import com.project.PCBuilder.rest.services.MotherboardService;


@RestController
@RequestMapping(value = "/api/v1/motherboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class MotherboardRestController {

    private static final Logger logger = LoggerFactory.getLogger(MotherboardRestController.class);

    private final MotherboardService service;

    @Autowired
    public MotherboardRestController(MotherboardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MotherboardDTO>> findAll() {
        logger.debug("GET - findAll");
        List<MotherboardDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}")
    public ResponseEntity<MotherboardDTO> findById(@PathVariable Integer partid) {
        logger.debug("GET - findById");
        MotherboardDTO dto = service.findById(partid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody MotherboardDTO motherboardDTO) {
        logger.debug("POST - create");
        if (service.create(motherboardDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @RequestBody MotherboardDTO motherboardDTO) {
        logger.debug("PUT - save");
        service.save(partid, motherboardDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody MotherboardDTO motherboardDTO) {
        logger.debug("PUT - update");
        if (service.update(motherboardDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @RequestBody MotherboardDTO motherboardDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, motherboardDTO)) {
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
