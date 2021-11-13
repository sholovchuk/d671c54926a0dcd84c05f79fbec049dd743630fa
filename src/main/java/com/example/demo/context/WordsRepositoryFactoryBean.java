package com.example.demo.context;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.InMemoryPrefixTreeWordsRepository;
import com.example.demo.repositories.WordsRepository;

@Component
public class WordsRepositoryFactoryBean implements FactoryBean<WordsRepository> {

    @Value("${WORDS_FILE_PATH:src/test/resources/fixtures/ordered-words-test-set}")
    private  String filePath;

    @Override
    public WordsRepository getObject() throws Exception {

        System.out.println(filePath); // file not found?

        return new InMemoryPrefixTreeWordsRepository(Files.lines(Paths.get(filePath)));
    }

    @Override
    public Class<?> getObjectType() {
        return WordsRepository.class;
    }
}
