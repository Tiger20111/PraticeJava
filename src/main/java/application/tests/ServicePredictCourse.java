package application.tests;

import application.tests.bd.DollarRate;
import application.tests.bd.DollarRepository;
import application.tests.bd.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static application.tests.bd.Utils.convertDateToString;

@Component
public class ServicePredictCourse {
  ServicePredictCourse() {

  }

  Double makePrediction(String data, DollarRepository dollarRepository, WeatherRepository weatherRepository, ServiceRBC serviceRBC, ServiceWeather serviceWeather) throws ParseException {
    if (dollarRepository.count() == 0) {
      String body = serviceRBC.getData();
      if (body == null) {
        throw new RuntimeException("404");
      }
      serviceRBC.saveMonthDollars(body, dollarRepository);
    }
    if (dollarRepository.count() == 0) {
      return -1.0;
    }

    ArrayList<DependencyDollarWeather> dependencyDollarWeathers = new ArrayList<>();

    Iterable<DollarRate> dollarRates = dollarRepository.findAll();
    for (DollarRate dollarRate: dollarRates) {
      Double rateDollar = dollarRate.getPercentage();
      Date dateDollar = dollarRate.getData();
      String convertedDate = convertDateToString(dateDollar);
      Double percentageWeather = serviceWeather.getTemperatureDate(convertedDate, weatherRepository);
      DependencyDollarWeather dependencyDollarWeather = new DependencyDollarWeather(convertedDate, rateDollar, percentageWeather);
      dependencyDollarWeathers.add(dependencyDollarWeather);
    }
    return analyseByDate(data, dependencyDollarWeathers);
  }

  private Double analyseByDate(String date, ArrayList<DependencyDollarWeather> dependencyDollarWeathers) {
    return 0.0;
  }

}
