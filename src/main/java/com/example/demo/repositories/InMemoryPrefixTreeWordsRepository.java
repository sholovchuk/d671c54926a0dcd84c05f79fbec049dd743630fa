package com.example.demo.repositories;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.example.demo.services.NextChars;

public class InMemoryPrefixTreeWordsRepository implements WordsRepository {

    private Map<Character, NextChars> rootChars = new TreeMap<>();;

    public InMemoryPrefixTreeWordsRepository(Stream<String> words) {
        requireNonNull(words);
        words.map(String::toLowerCase).forEach(word -> {
            Map<Character, NextChars> nextChars = rootChars;
            for(int i = 0; i < word.length(); ++i) {
                char symbol = word.charAt(i);
                NextChars subTree = nextChars.get(symbol);
                if (subTree == null) {
                    subTree = new NextChars(word.length() == i + 1);
                    nextChars.put(symbol, subTree);
                }
                nextChars = subTree.getNextChars();
            }
        });
    }

    @Override
    public List<String> getWords(String query, int limit) {
        requireNonNull(query);

        query = query.toLowerCase();

        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive. But was " + limit);
        }

        if (query.isEmpty()) {
            return emptyList();
        }

        NextChars subTree = findSubTree(query);

        if (subTree == null) {
            return emptyList();
        }

        List<String> words = new ArrayList<>();
        recursivelyCollectWords(new StringBuilder(query), subTree, words, limit);

        return words;
    }

    private NextChars findSubTree(String query) {

        NextChars nextChar = null;
        Map<Character, NextChars> nextChars = rootChars;

        for(char ch : query.toCharArray()) {
            nextChar = nextChars.get(ch);
            if (nextChar == null) {
                break;
            }
            nextChars = nextChar.getNextChars();
        }
        return nextChar;
    }

    private void recursivelyCollectWords(StringBuilder symbolPath, NextChars nextChar, List<String> words, int limit) {

        if(words.size() == limit) {
            return;
        }

        if (nextChar.isWord()) {
            words.add(symbolPath.toString());
        }

        for(char symbol : nextChar.getNextChars().keySet()) {
            recursivelyCollectWords(symbolPath.append(symbol), nextChar.getNextChar(symbol), words, limit);
        }
        symbolPath.deleteCharAt(symbolPath.length() - 1);
    }
}
