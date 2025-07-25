
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.PartunitDTO;
import com.project.PCBuilder.rest.services.PartunitService;


@RestController
@RequestMapping(value = "/api/v1/partunit", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartunitRestController {

    private static final Logger logger = LoggerFactory.getLogger(PartunitRestController.class);

    private final PartunitService service;

    @Autowired
    public PartunitRestController(PartunitService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PartunitDTO>> findAll() {
        logger.debug("GET - findAll");
        List<PartunitDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}/{unitid}")
    public ResponseEntity<PartunitDTO> findById(@PathVariable Integer partid, @PathVariable Integer unitid) {
        logger.debug("GET - findById");
        PartunitDTO dto = service.findById(partid, unitid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody PartunitDTO partunitDTO) {
        logger.debug("POST - create");
        if (service.create(partunitDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}/{unitid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @PathVariable Integer unitid, @RequestBody PartunitDTO partunitDTO) {
        logger.debug("PUT - save");
        service.save(partid, unitid, partunitDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PartunitDTO partunitDTO) {
        logger.debug("PUT - update");
        if (service.update(partunitDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}/{unitid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @PathVariable Integer unitid, @RequestBody PartunitDTO partunitDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, unitid, partunitDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{partid}/{unitid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer partid, @PathVariable Integer unitid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(partid, unitid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
