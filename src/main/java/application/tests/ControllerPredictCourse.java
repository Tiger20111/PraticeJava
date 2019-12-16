package application.tests;

import application.tests.bd.DollarRate;
import application.tests.bd.DollarRepository;
import application.tests.bd.WeatherRate;
import application.tests.bd.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static application.tests.bd.Utils.FormatData;

@RestController
public class ControllerPredictCourse {
  public ControllerPredictCourse() {
    this.service = new ServicePredictCourse();
    this.serviceRBC = new ServiceRBC();
    this.serviceWeather = new ServiceWeather();
  }
  public ControllerPredictCourse(ServicePredictCourse service) {
    this.service = service;
  }

  @RequestMapping(value = "/predict/{data}")
  public Double predictDollar(@PathVariable("data") String data) throws Exception {
    DollarRate dollarRate = dollarRepository.findByData(FormatData(data));
    if (dollarRate != null) {
      return dollarRate.getPercentage();
    }

    return service.makePrediction(data, dollarRepository, weatherRepository, serviceRBC, serviceWeather);
  }

  @Autowired
  private DollarRepository dollarRepository;

  @Autowired
  private WeatherRepository weatherRepository;

  private ServiceRBC serviceRBC;
  private ServiceWeather serviceWeather;
  private final ServicePredictCourse service;

}
