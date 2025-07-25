
package com.project.PCBuilder.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.AccountsDTO;
import com.project.PCBuilder.rest.services.AccountsService;


@RestController
@RequestMapping(value = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsRestController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsRestController.class);

    private final AccountsService service;

    @Autowired
    public AccountsRestController(AccountsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccountsDTO>> findAll() {
        logger.debug("GET - findAll");
        List<AccountsDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{accountid}")
    public ResponseEntity<AccountsDTO> findById(@PathVariable Integer accountid) {
        logger.debug("GET - findById");
        AccountsDTO dto = service.findById(accountid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody AccountsDTO accountsDTO) {
        logger.debug("POST - create");
        if (service.create(accountsDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping(value = "/{accountid}")
    public ResponseEntity<Void> save(@PathVariable Integer accountid, @RequestBody AccountsDTO accountsDTO) {
        logger.debug("PUT - save");
        service.save(accountid, accountsDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AccountsDTO accountsDTO) {
        logger.debug("PUT - update");
        if (service.update(accountsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{accountid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer accountid, @RequestBody AccountsDTO accountsDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(accountid, accountsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{accountid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer accountid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(accountid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
