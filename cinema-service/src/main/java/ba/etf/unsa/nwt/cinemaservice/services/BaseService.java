package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseService<M extends BaseModel, R extends JpaRepository<M, Long>> {
    @Autowired
    protected R repo;

    public M get(Long id) {
        M model = repo.getOne(id);
        return model;
    }

    public void save(M m) {
        repo.save(m);
    }

    public Iterable<M> all() {
        return repo.findAll();
    }

    public Long count() {
        return repo.count();
    }
}
