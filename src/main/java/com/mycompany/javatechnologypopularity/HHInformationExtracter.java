/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javatechnologypopularity;

import com.mycompany.javatechnologypopularity.text.TextAnalyzeHelper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Java
 */
public class HHInformationExtracter implements InformationExtracter {

    private static final String HH_PAGE_URL
            = "https://nn.hh.ru/search/vacancy?text=java&clusters=true&enable_snippets=true&";
    private static final int PAGE_TO_ANALYZE = 100;
    private static final String HTML_VACANCY_BLOCK_CLASS_NAME = "search-result-item__response";
    private static final String HTML_VACANCY_DESCRIPTION_BLOCK_CLASS_NAME = "b-vacancy-desc-wrapper";
    private static long analyzed = 0;

    @Override
    public void extractInformationTo(File file) throws IOException {
        for (int i = 0; i < PAGE_TO_ANALYZE; i++) {
            String pageUrl = getHeadHunterPageUrlWithNumber(i);
            Document pageDocument = Jsoup
                    .connect(pageUrl)
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(10000).get();

            Elements vacancyBlocks
                    = pageDocument.getElementsByClass(HTML_VACANCY_BLOCK_CLASS_NAME);

            for (Element vacancyBlock : vacancyBlocks) {
                Element linkElement = vacancyBlock.select("a").first();
                String html = linkElement.outerHtml();

                int index = html.indexOf("vacancyId=");
                String text = html.substring(index + "vacancyId=".length());
                String vacancyId = text.substring(0, text.indexOf("\""));
                String description = getVacancyDescription(vacancyId);
                System.out.println("Analyzed " + (++analyzed));
                FileUtils.writeStringToFile(file, description, true);
                try {
                    TimeUnit.MILLISECONDS.sleep(150);
                } catch (InterruptedException ex) {
                }
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
            }
        }
    }

    private static String getHeadHunterPageUrlWithNumber(int number) {
        return HH_PAGE_URL + "page=" + number;
    }

    private static String getHeadHunterVacancyPageUrlWithId(String vacancyId) {
        return "https://nn.hh.ru/vacancy/" + vacancyId + "?query=java";
    }

    private String getVacancyDescription(String vacancyId) throws IOException {
        String vacancyPageUrl = getHeadHunterVacancyPageUrlWithId(vacancyId);
        Document pageDocument = Jsoup.connect(vacancyPageUrl)
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .get();

        Element descriptionEl = pageDocument
                .getElementsByClass(HTML_VACANCY_DESCRIPTION_BLOCK_CLASS_NAME)
                .first();
        return descriptionEl.text();
    }

}
