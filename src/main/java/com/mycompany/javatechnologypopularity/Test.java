/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javatechnologypopularity;

import com.mycompany.javatechnologypopularity.text.TextAnalyzeHelper;
import com.mycompany.javatechnologypopularity.text.TextStatistics;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Java
 */
public class Test {

    public static void main(String[] args) throws IOException {

        File vacancyDescriptionsFile = getFileWithVacancyDescriptions(
                "vacancy_descriptions.txt");

//        InformationExtracter extracter = new HHInformationExtracter();
//        extracter.extractInformationTo(vacancyDescriptionsFile);
        TextStatistics statictics
                = TextAnalyzeHelper.getTextFreqInfo(vacancyDescriptionsFile);
        List<String> words = statictics.getPopularWords(1000,
                Arrays.asList("spring",
                        "hibernate",
                        "gwt",
                        "javascript",
                        "html",
                        "css",
                        "ee", 
                        "angularjs", 
                        "jquery", "vaadin", "glassfish", "wildfly", "oracle", "jboss", "jsf", "ejb", 
                        "rest", "mvc", "servlet", "maven", "ant", "git", "svn", ""));
        words.forEach(System.out::println);
    }

    private static File getFileWithVacancyDescriptions(String fileName) throws IOException {
        File vacancyDescriptionsFile = new File(fileName);
        if (!vacancyDescriptionsFile.exists()) {
            vacancyDescriptionsFile.createNewFile();
        }
        return vacancyDescriptionsFile;
    }

}
