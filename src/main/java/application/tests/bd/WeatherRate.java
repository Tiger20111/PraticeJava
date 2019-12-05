package application.tests.bd;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static application.tests.bd.Utils.FormatData;

@Entity
@Table(name = "WEATHER")
public class WeatherRate {


  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private Date data;
  private Double percentage;


  protected WeatherRate() {}

  public WeatherRate(String data, Double percentage) throws ParseException {
    this.data = FormatData(data); // принимает в формате "yyyy/MM/dd"
    this.percentage = percentage;
  }


  public Double getPercentage() {
    return percentage;
  }


  @Override
  public String toString() {
    return String.format(
            "WeatherRate[id=%d, data='%s', percentage='%s']",
            id, data, percentage);
  }
}