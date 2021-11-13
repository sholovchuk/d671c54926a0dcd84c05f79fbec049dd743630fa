package com.example.demo.repositories;

import java.util.List;

public interface WordsRepository {
    List<String> getWords(String query, int limit);
}
