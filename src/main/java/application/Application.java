package application;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    return args -> {
      System.out.println("Boot application has started");
      printNamesBeans(ctx);
    };
  }

  private void printNamesBeans(ApplicationContext ctx) {
    String[] nameBeans = ctx.getBeanDefinitionNames();
    Arrays.sort(nameBeans);
    for (String nameBean : nameBeans) {
      System.out.println(nameBean);
    }
  }

}