
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.PcbuildDTO;
import com.project.PCBuilder.rest.services.PcbuildService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/pcbuild", produces = MediaType.APPLICATION_JSON_VALUE)
public class PcbuildRestController {

    private static final Logger logger = LoggerFactory.getLogger(PcbuildRestController.class);

    private final PcbuildService service;

    @Autowired
    public PcbuildRestController(PcbuildService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PcbuildDTO>> findAll() {
        logger.debug("GET - findAll");
        List<PcbuildDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{pcid}/{componentid}")
    public ResponseEntity<PcbuildDTO> findById(@PathVariable Integer pcid, @PathVariable Integer componentid) {
        logger.debug("GET - findById");
        PcbuildDTO dto = service.findById(pcid, componentid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody PcbuildDTO pcbuildDTO) {
        logger.debug("POST - create");
        if (service.create(pcbuildDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{pcid}/{componentid}")
    public ResponseEntity<Void> save(@PathVariable Integer pcid, @PathVariable Integer componentid, @RequestBody PcbuildDTO pcbuildDTO) {
        logger.debug("PUT - save");
        service.save(pcid, componentid, pcbuildDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PcbuildDTO pcbuildDTO) {
        logger.debug("PUT - update");
        if (service.update(pcbuildDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{pcid}/{componentid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer pcid, @PathVariable Integer componentid, @RequestBody PcbuildDTO pcbuildDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(pcid, componentid, pcbuildDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{pcid}/{componentid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer pcid, @PathVariable Integer componentid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(pcid, componentid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
