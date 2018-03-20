package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.News;
import ba.etf.unsa.nwt.cinemaservice.repositories.NewsRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsService extends BaseService<News, NewsRepository> {
}
