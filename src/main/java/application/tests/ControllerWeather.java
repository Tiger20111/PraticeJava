package application.tests;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
public class ControllerWeather {



  public ControllerWeather() {
    this.service = new ServiceWeather();
  }

  @RequestMapping(value = "/weather/{data}")
  public Double getWeather(@PathVariable("data") String data) throws ParseException {
    return service.getTemperatureDate(data);
  }

  private final ServiceWeather service;
}
