package com.example.demo.services;

import java.util.Map;
import java.util.TreeMap;

public class NextChars {

    private final boolean isWord;
    private final Map<Character, NextChars> nextChars;

    public NextChars(boolean isWord) {
        this.isWord = isWord;
        this.nextChars = new TreeMap<>();
    }

    public boolean isWord() {
        return isWord;
    }

    public Map<Character, NextChars> getNextChars() {
        return nextChars;
    }

    public NextChars getNextChar(char nextChar) {
        return nextChars.get(nextChar);
    }
}
