package ba.etf.unsa.nwt.cinemaservice.services;

import ba.etf.unsa.nwt.cinemaservice.exceptions.ServiceException;
import ba.etf.unsa.nwt.cinemaservice.models.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public class BaseService<M extends BaseModel, R extends JpaRepository<M, Long>> {

    @Autowired
    protected R repo;

    public Optional<M> get(Long id) {
        return repo.findById(id);
    }

    public M save(M m) {
        M temp = repo.save(m);
        return temp;
    }

    public Collection<M> all() {
        return repo.findAll();
    }

    public Long count() {
        return repo.count();
    }

    public void delete(M m) {
        try {
            repo.delete(m);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete object (probably due to its dependencies in other tables.");
        }
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public boolean exists(Long id) {
        return repo.existsById(id);
    }

    public Page<M> getAllByPage(Pageable p) {
        return repo.findAll(p);
    }

}
