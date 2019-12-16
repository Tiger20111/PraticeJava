package application.tests.bd;


import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;

import static application.tests.bd.Utils.FormatData;

@Entity
@Table(name = "DOLLAR")
public class DollarRate {


  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private Date data;
  private Double course;


  protected DollarRate() {}

  public DollarRate(String data, Double course) throws Exception {
    this.data = FormatData(data); // принимает в формате "yyyy/MM/dd"
    this.course = course;
  }


  public Double getPercentage() {
    return course;
  }
  public Date getData() {
    return data;
  }

  @Override
  public String toString() {
    return String.format(
            "DollarRate[id=%d, data='%s', course='%s']",
            id, data, course);
  }
}
