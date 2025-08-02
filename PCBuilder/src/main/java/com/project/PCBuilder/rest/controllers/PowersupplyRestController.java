
package com.project.PCBuilder.rest.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.PowersupplyDTO;
import com.project.PCBuilder.rest.services.PowersupplyService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/powersupply", produces = MediaType.APPLICATION_JSON_VALUE)
public class PowersupplyRestController {

    private static final Logger logger = LoggerFactory.getLogger(PowersupplyRestController.class);

    private final PowersupplyService service;

    @Autowired
    public PowersupplyRestController(PowersupplyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PowersupplyDTO>> findAll(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "PartName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        logger.debug("GET - findAll with pagination & filters");
        List<PowersupplyDTO> list = service.findAll(pageNumber, pageSize, searchName, minPrice, maxPrice, sortBy, sortDirection);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}")
    public ResponseEntity<PowersupplyDTO> findById(@PathVariable Integer partid) {
        logger.debug("GET - findById");
        PowersupplyDTO dto = service.findById(partid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody PowersupplyDTO powersupplyDTO) {
        logger.debug("POST - create");
        if (service.create(powersupplyDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @RequestBody PowersupplyDTO powersupplyDTO) {
        logger.debug("PUT - save");
        service.save(partid, powersupplyDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PowersupplyDTO powersupplyDTO) {
        logger.debug("PUT - update");
        if (service.update(powersupplyDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @RequestBody PowersupplyDTO powersupplyDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, powersupplyDTO)) {
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