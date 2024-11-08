package com.example.rest_api_test.article.service;

import com.example.rest_api_test.article.dto.ArticleDTO;
import com.example.rest_api_test.article.entity.Article;
import com.example.rest_api_test.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<ArticleDTO> getList() {
        List<Article> articleList = this.articleRepository.findAll();

        List<ArticleDTO> articleDTOList = articleList.stream()
                .map(article -> new ArticleDTO(article))
                .collect(Collectors.toList());

        return articleDTOList;
    }

    public Article getArticle(Long id) {
        Optional<Article> optionalArticle = this.articleRepository.findById(id);

        return optionalArticle.orElse(null);
    }

    public Article write(String content, String subject) {
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .build();
        this.articleRepository.save(article);
        return article;
    }

    public Article update(Article article, String content, String subject) {
        article.setSubject(subject);
        article.setContent(content);
        this.articleRepository.save(article);
        return article;
    }
}