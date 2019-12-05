package application.tests.bd;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface WeatherRepository extends CrudRepository<WeatherRate, Long> {
  WeatherRate findByData(Date data);
  Optional<WeatherRate> findById(ID primaryKey);
  WeatherRate save(WeatherRate entity);
  void delete(WeatherRate employees);
}
