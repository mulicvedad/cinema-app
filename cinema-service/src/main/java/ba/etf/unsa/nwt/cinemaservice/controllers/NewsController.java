package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.models.News;
import ba.etf.unsa.nwt.cinemaservice.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@PathVariable @NotNull Long id) {
        Optional<News> news = newsService.get(id);
        if (news.isPresent())
            return ResponseEntity.ok().body(news.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllNews() {
        return ResponseEntity.ok().body(newsService.all());
    }

    @GetMapping("/page/{pageNum}")
    public ResponseEntity getAllNews(@PathVariable Integer pageNum) {
        Pageable pageRequest = PageRequest.of(pageNum, 20);
        return ResponseEntity.ok().body(newsService.getAllByPage(pageRequest));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid News news) {
        newsService.save(news);
        return ResponseEntity.ok().build();
    }
}
