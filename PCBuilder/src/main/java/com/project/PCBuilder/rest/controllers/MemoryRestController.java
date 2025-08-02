
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

import com.project.PCBuilder.rest.dto.MemoryDTO;
import com.project.PCBuilder.rest.services.MemoryService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/memory", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemoryRestController {

    private static final Logger logger = LoggerFactory.getLogger(MemoryRestController.class);

    private final MemoryService service;

    @Autowired
    public MemoryRestController(MemoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MemoryDTO>> findAll(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "PartName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        logger.debug("GET - findAll with pagination & filters");
        List<MemoryDTO> list = service.findAll(pageNumber, pageSize, searchName, minPrice, maxPrice, sortBy, sortDirection);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}")
    public ResponseEntity<MemoryDTO> findById(@PathVariable Integer partid) {
        logger.debug("GET - findById");
        MemoryDTO dto = service.findById(partid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody MemoryDTO memoryDTO) {
        logger.debug("POST - create");
        if (service.create(memoryDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @RequestBody MemoryDTO memoryDTO) {
        logger.debug("PUT - save");
        service.save(partid, memoryDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody MemoryDTO memoryDTO) {
        logger.debug("PUT - update");
        if (service.update(memoryDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @RequestBody MemoryDTO memoryDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, memoryDTO)) {
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