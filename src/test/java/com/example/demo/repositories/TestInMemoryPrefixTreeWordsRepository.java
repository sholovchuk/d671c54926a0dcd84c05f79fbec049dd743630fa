package com.example.demo.repositories;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestInMemoryPrefixTreeWordsRepository {

    private static InMemoryPrefixTreeWordsRepository emptyWordsRepository;
    private static InMemoryPrefixTreeWordsRepository singleWordWordsRepository;
    private static InMemoryPrefixTreeWordsRepository wordsTestSetWordsRepository;

    @BeforeAll
    private static void beaforeall() throws IOException {
        emptyWordsRepository = new InMemoryPrefixTreeWordsRepository(Stream.of());
        singleWordWordsRepository = new InMemoryPrefixTreeWordsRepository(Stream.of("abc"));
        wordsTestSetWordsRepository = new InMemoryPrefixTreeWordsRepository(Files.lines(Paths.get(System.getProperty("user.dir") + "/src/test/resources/fixtures/ordered-words-test-set")));
    }

    @Test
    public void sourceStreamShouldNotBeNull() {
        assertThrows(NullPointerException.class, () -> new InMemoryPrefixTreeWordsRepository(null));
    }

    @Test
    public void queryShouldNotBeNull() {
        assertThrows(NullPointerException.class, () -> emptyWordsRepository.getWords(null, 0));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionOnNegativeLimit() {
        assertThrows(IllegalArgumentException.class, () -> emptyWordsRepository.getWords("a", -1));
    }

    @Test
    public void retrunEmptyListOnLimitEqualToZero() {
        assertThrows(IllegalArgumentException.class, () -> emptyWordsRepository.getWords("a", 0));
    }

    @Test
    public void retrunEmptyListOnEmptyQuery() {
        assertEquals(emptyList(), singleWordWordsRepository.getWords("", 1));
    }

    @Test
    public void retrunListOfSuggestionWords() throws IOException {
        List<String> expected = List.of("lab", "label", "labor", "laboratory", "lack");
        assertEquals(expected, wordsTestSetWordsRepository.getWords("la", 5));
    }

    @Test
    public void returnListOfSuggesionsWordWhenQueryIsAPartOfTheseWords() {
        List<String> expected = List.of("any", "anybody", "anymore", "anyone", "anything", "anyway", "anywhere");
        assertEquals(expected, wordsTestSetWordsRepository.getWords("any", 10));
    }
}
