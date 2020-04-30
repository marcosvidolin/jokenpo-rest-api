package com.marcosvidolin.jokenpo.rest.api.controller;

import com.marcosvidolin.jokenpo.domain.Item;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemRestController {

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(Item.values());
    }

}
