package com.example.demo.controllers;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.SuggestionsService;

@RestController
public class SuggestionsController {

    private final int LOWER_BOUND = 1;  // config?
    private final int UPPER_BOUND = 20; // config?
    private final Pattern correctQuery = Pattern.compile("^[A-Za-z]+$"); // config?

    @Autowired
    private SuggestionsService suggestions;

    @GetMapping("/suggestions")
    public ResponseEntity<?> getSuggestions(@RequestParam(required = true) String query, @RequestParam(defaultValue = "10") int limit) {

        if (isInvalid(query)) {
            return new ResponseEntity<>(String.format("Query must match pattern /%s/, but got = %s", correctQuery.pattern(),  query), HttpStatus.BAD_REQUEST);
        }

        if (inNotRange(limit, LOWER_BOUND, UPPER_BOUND)) {
            return new ResponseEntity<>(String.format("Expecting limit to be in range [%d, %d], but got = %d", LOWER_BOUND, UPPER_BOUND, limit), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(suggestions.getSuggestions(query, limit), HttpStatus.OK);
    }

    private boolean isInvalid(String query) {
        return !correctQuery.matcher(query).matches();
    }

    private boolean inNotRange(int limit, int lower, int upper) {
        return limit < lower || limit > upper;
    }
}
