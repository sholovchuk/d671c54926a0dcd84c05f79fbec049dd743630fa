package com.example.demo.context;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.repositories.InMemoryPrefixTreeWordsRepository;
import com.example.demo.repositories.WordsRepository;

@Component
public class WordsRepositoryFactoryBean implements FactoryBean<WordsRepository> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${WORDS_FILE_PATH:src/test/resources/fixtures/ordered-words-test-set}")
    private  String filePath;

    @Override
    public WordsRepository getObject() throws Exception {

        Path wordFilePath = Paths.get(filePath);

        if(wordFilePath.toFile().exists()) {
            log.info("Loading file '{}'", filePath);
        }
        else {
            log.error("File '{}' not found", filePath);
            throw new FileNotFoundException(filePath);
        }

        return new InMemoryPrefixTreeWordsRepository(Files.lines(Paths.get(filePath)));
    }

    @Override
    public Class<?> getObjectType() {
        return WordsRepository.class;
    }
}
