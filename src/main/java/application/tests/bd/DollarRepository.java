package application.tests.bd;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DollarRepository extends CrudRepository<DollarRate, Long> {
  DollarRate findByData(Date data);
  Optional<DollarRate> findById(long primaryKey);
  DollarRate save(DollarRate entity);
  void delete(DollarRate employees);
}

