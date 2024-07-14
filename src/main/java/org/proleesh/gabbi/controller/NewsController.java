package org.proleesh.gabbi.controller;

import lombok.AllArgsConstructor;
import org.proleesh.gabbi.dto.NewsArticle;
import org.proleesh.gabbi.service.NaverNewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
/**
 * @author sung-hyuklee
 * date: 2024.7.13
 * News Controller
 */
@Controller
@AllArgsConstructor
public class NewsController {
    private final NaverNewsService naverNewsService;
    private final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @GetMapping("/news")
    public String getNews(@RequestParam(value = "query", required = false) String query, Model model) {
        if(query == null || query.isEmpty()) {
           model.addAttribute("newsArticles", Collections.emptyList());
        }else {
            logger.info("getNews query: {}", query);
            List<NewsArticle> newsArticles = naverNewsService.getNews(query);
            model.addAttribute("newsArticles", newsArticles);
        }
        return "news/news";
    }
}
