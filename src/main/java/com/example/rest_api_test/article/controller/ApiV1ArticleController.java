package com.example.rest_api_test.article.controller;

import com.example.rest_api_test.article.dto.ArticleDTO;
import com.example.rest_api_test.article.entity.Article;
import com.example.rest_api_test.article.request.ArticleCreateRequest;
import com.example.rest_api_test.article.request.ArticleModifyRequest;
import com.example.rest_api_test.article.response.ArticleCreateResponse;
import com.example.rest_api_test.article.response.ArticleModifyResponse;
import com.example.rest_api_test.article.response.ArticleResponse;
import com.example.rest_api_test.article.response.ArticlesResponse;
import com.example.rest_api_test.article.service.ArticleService;
import com.example.rest_api_test.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @GetMapping("")
    public RsData<ArticlesResponse> list() {
        List<ArticleDTO> articleList = this.articleService.getList();

        return RsData.of("200", "게시글 목록 조회", new ArticlesResponse(articleList));
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        ArticleDTO articleDTO = new ArticleDTO(article);

        return RsData.of("200", "게시글 단건 조회", new ArticleResponse(articleDTO));
    }

    @PostMapping("")
    public RsData<ArticleCreateResponse> create(@Valid @RequestBody ArticleCreateRequest articleCreateRequest) {
        Article article = this.articleService.write(articleCreateRequest.getSubject(), articleCreateRequest.getContent());
        System.out.println(articleCreateRequest.getSubject());
        System.out.println(articleCreateRequest.getContent());
        return RsData.of("200", "등록성공", new ArticleCreateResponse(article));
    }

    @PatchMapping("/{id}")
    public RsData<ArticleModifyResponse> modify(@PathVariable("id") Long id, @Valid @RequestBody ArticleModifyRequest articleModifyRequest) {
        Article article = this.articleService.getArticle(id);

        if (article == null) return RsData.of(
                "500",
                "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                null
        );
        article = this.articleService.update(article, articleModifyRequest.getSubject(), articleModifyRequest.getContent());
        return RsData.of("200", "수정성공", new ArticleModifyResponse(article));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        System.out.println(id);

        return "삭제완료";
    }
}