package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.models.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public class BaseService<M extends BaseModel, R extends JpaRepository<M, Long>> {

    @Autowired
    protected R repo;

    public Optional<M> get(Long id) {
        return repo.findById(id);
    }

    public void save(M m) {
        repo.save(m);
    }

    public Collection<M> all() {
        return repo.findAll();
    }

    public Long count() {
        return repo.count();
    }

    public void delete(M m) {
        repo.delete(m);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public boolean exists(Long id) {
        return repo.existsById(id);
    }

}
