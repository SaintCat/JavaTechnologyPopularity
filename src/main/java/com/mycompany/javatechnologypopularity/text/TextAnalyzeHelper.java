/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javatechnologypopularity.text;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Java
 */
public class TextAnalyzeHelper {

    /**
     * Получить частотную характеристику текста в заданном файле.
     *
     * @param fileWithText файл,текст которого нужно проанализировать
     * @return статистику по тексту
     * @throws IOException при ошибке чтения данных из файла
     */
    public static TextStatistics getTextFreqInfo(File fileWithText) throws IOException {
        return new TextStatistics(FileUtils.readFileToString(fileWithText));
    }
}
