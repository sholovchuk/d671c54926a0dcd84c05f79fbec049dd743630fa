package com.example.demo.services;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repositories.WordsRepository;

@Service
public class InMemorySuggestionsService implements SuggestionsService {

    private final WordsRepository wordsRepository;

    public InMemorySuggestionsService(WordsRepository wordsRepository) {
        this.wordsRepository = requireNonNull(wordsRepository);
    }

    @Override
    public List<String> getSuggestions(String query, int limit) {
        requireNonNull(query);
        return wordsRepository.getWords(query, limit);
    }
}
