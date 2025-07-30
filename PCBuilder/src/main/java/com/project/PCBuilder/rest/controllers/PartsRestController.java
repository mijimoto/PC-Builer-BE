
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.PartsDTO;
import com.project.PCBuilder.rest.services.PartsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/parts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartsRestController {

    private static final Logger logger = LoggerFactory.getLogger(PartsRestController.class);

    private final PartsService service;

    @Autowired
    public PartsRestController(PartsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PartsDTO>> findAll() {
        logger.debug("GET - findAll");
        List<PartsDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}")
    public ResponseEntity<PartsDTO> findById(@PathVariable Integer partid) {
        logger.debug("GET - findById");
        PartsDTO dto = service.findById(partid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody PartsDTO partsDTO) {
        logger.debug("POST - create");
        if (service.create(partsDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @RequestBody PartsDTO partsDTO) {
        logger.debug("PUT - save");
        service.save(partid, partsDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PartsDTO partsDTO) {
        logger.debug("PUT - update");
        if (service.update(partsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @RequestBody PartsDTO partsDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, partsDTO)) {
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
