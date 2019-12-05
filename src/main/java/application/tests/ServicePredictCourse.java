package application.tests;

import application.tests.bd.DollarRepository;
import application.tests.bd.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ServicePredictCourse {
  @Autowired
  private DollarRepository dollarRepository;

  @Autowired
  private WeatherRepository repWeather;

}
