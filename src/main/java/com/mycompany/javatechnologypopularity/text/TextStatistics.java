/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javatechnologypopularity.text;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Java
 */
public class TextStatistics {

    private String text;

    public TextStatistics(String text) {
        this.text = text;
    }

    public List<String> getPopularWords(int limit, List<String> whiteWords) {
        Map<String, Integer> freqMap = new HashMap<>();
        text = text.replace(',', ' ');
        String[] words = text.split("\\s+");
        Arrays.asList(words).forEach(s -> freqMap.merge(s.toLowerCase(), 1, (a, b) -> a + b));

        return freqMap.entrySet().stream()
                .sorted(descendingFrequencyOrder())
                .limit(limit)
                .filter((s) -> whiteWords.isEmpty() ? true : whiteWords.contains(s.getKey()))
                .map((s) -> s.getKey() + " " + s.getValue())
                .collect(Collectors.toList());
    }

    public List<String> getPopularWords(int limit) {
        return getPopularWords(limit, Collections.EMPTY_LIST);
    }

    // Создание Comparator'а вынесено в отдельный метод, чтобы не загромождать метод main.
    private static Comparator<Map.Entry<String, Integer>> descendingFrequencyOrder() {
        // Нам нужен Comparator, который сначала упорядочивает пары частоте (по убыванию),
        // а затем по слову (в алфавитном порядке). Так и напишем:
        return Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey);
    }
}
