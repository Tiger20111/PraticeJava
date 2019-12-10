package application.tests;

import application.tests.bd.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
public class ControllerWeather {
  @Autowired
  private WeatherRepository repWeather;



  public ControllerWeather() {
    this.service = new ServiceWeather();
  }

  @RequestMapping(value = "/weather/{data}")
  public Double getWeather(@PathVariable("data") String data) throws ParseException {
    return service.getTemperatureDate(data, repWeather);
  }

  private final ServiceWeather service;
}
