
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.PcsDTO;
import com.project.PCBuilder.rest.services.PcsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/pcs", produces = MediaType.APPLICATION_JSON_VALUE)
public class PcsRestController {

    private static final Logger logger = LoggerFactory.getLogger(PcsRestController.class);

    private final PcsService service;

    @Autowired
    public PcsRestController(PcsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PcsDTO>> findAll() {
        logger.debug("GET - findAll");
        List<PcsDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{pcid}")
    public ResponseEntity<PcsDTO> findById(@PathVariable Integer pcid) {
        logger.debug("GET - findById");
        PcsDTO dto = service.findById(pcid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody PcsDTO pcsDTO) {
        logger.debug("POST - create");
        if (service.create(pcsDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{pcid}")
    public ResponseEntity<Void> save(@PathVariable Integer pcid, @RequestBody PcsDTO pcsDTO) {
        logger.debug("PUT - save");
        service.save(pcid, pcsDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PcsDTO pcsDTO) {
        logger.debug("PUT - update");
        if (service.update(pcsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{pcid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer pcid, @RequestBody PcsDTO pcsDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(pcid, pcsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{pcid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer pcid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(pcid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
