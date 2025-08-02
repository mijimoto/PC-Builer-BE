
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

import com.project.PCBuilder.rest.dto.InternalharddriveDTO;
import com.project.PCBuilder.rest.services.InternalharddriveService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/internalharddrive", produces = MediaType.APPLICATION_JSON_VALUE)
public class InternalharddriveRestController {

    private static final Logger logger = LoggerFactory.getLogger(InternalharddriveRestController.class);

    private final InternalharddriveService service;

    @Autowired
    public InternalharddriveRestController(InternalharddriveService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InternalharddriveDTO>> findAll(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "PartName") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        logger.debug("GET - findAll with pagination & filters");
        List<InternalharddriveDTO> list = service.findAll(pageNumber, pageSize, searchName, minPrice, maxPrice, sortBy, sortDirection);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{partid}")
    public ResponseEntity<InternalharddriveDTO> findById(@PathVariable Integer partid) {
        logger.debug("GET - findById");
        InternalharddriveDTO dto = service.findById(partid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody InternalharddriveDTO internalharddriveDTO) {
        logger.debug("POST - create");
        if (service.create(internalharddriveDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{partid}")
    public ResponseEntity<Void> save(@PathVariable Integer partid, @RequestBody InternalharddriveDTO internalharddriveDTO) {
        logger.debug("PUT - save");
        service.save(partid, internalharddriveDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody InternalharddriveDTO internalharddriveDTO) {
        logger.debug("PUT - update");
        if (service.update(internalharddriveDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{partid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer partid, @RequestBody InternalharddriveDTO internalharddriveDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(partid, internalharddriveDTO)) {
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