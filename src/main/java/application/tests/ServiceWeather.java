package application.tests;

import application.tests.bd.WeatherRate;
import application.tests.bd.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

import static application.tests.bd.Utils.FormatData;
import static application.tests.bd.Utils.convertToUnix;
import static application.tests.bd.Utils.getDoubleFromString;

@Component
public class ServiceWeather {

  @Autowired
  private WeatherRepository repWeather;

  ServiceWeather() {

  }

  private String getTemperatures(String data) {
    String unixTime = convertToUnix(data);
    String url = apiForecast + hash + location + unixTime + flag;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return response.getBody();
  }

  Double getTemperatureDate(String data) throws ParseException {

    WeatherRate weatherRate = repWeather.findByData(FormatData(data));
    if (weatherRate != null) {
      return weatherRate.getPercentage();
    }

    String request = getTemperatures(data);
    return request.length() + 0.0;
    /*

    TreeMap<Double, Double> temperatures =  parseTemperatures(request);
    double sumTemp = 0;

    for (Map.Entry<Double, Double> tempr : temperatures.entrySet()) {
      sumTemp += tempr.getValue();
    }

    double averageRate = sumTemp / temperatures.size();

    WeatherRate weatherRateNew = new WeatherRate(data, averageRate);
    repWeather.save(weatherRateNew);

    return averageRate;
    */
  }


  private TreeMap<Double, Double> parseTemperatures(String request) {
    String[] lines = request.split("apparentTemperature");
    TreeMap<Double, Double> temperatures= new TreeMap<>();
    int timeIndex = 0;
    for (String line : lines) {
      int temperatureIndex = line.lastIndexOf("\"temperature\"");
      if (temperatureIndex == -1) {
        continue;
      } else {
        timeIndex = line.lastIndexOf("\"time\"");
        if (timeIndex == -1) {
          continue;
        }
      }


      temperatures.put(getDoubleFromString(line, timeIndex), getDoubleFromString(line, temperatureIndex));
    }
    return temperatures;
  }

  private static final String apiForecast = "https://api.darksky.net/forecast/";
  private static final String location = "/40.7127,-74.0059,";
  private static final String flag = "?exclude=currently,flag";
  private static final String hash = "6880e28eeb567a5ffda54af015126cf6";

  //Там юзается unix time. У нас в день 24*60*60 = 86400 секунд. На 01 января 01 декабря 2019 года приходится 1546300800.
}
