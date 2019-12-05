package application.tests;

import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;


import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ServiceTest {

  private Double expected = 63.7946;
  private String testBody = "USD000000TOD\t2019-07-05\t63.4425\t63.8\t63.3975\t63.79\t849731000\t63.5605\n" +
          "USD000000TOD\t2019-07-08\t63.9\t63.9875\t63.5675\t63.6375\t575008000\t63.7946\n" +
          "USD000000TOD\t2019-07-09\t63.7\t63.905\t63.6725\t63.88\t621468000\t63.7913\n" +
          "USD000000TOD\t2019-07-10\t63.86\t63.9025\t63.225\t63.27\t762383000\t63.611\n";

  @Autowired
  private MockMvc mockMvc;

/*
  @Test
  public void testRequestLocalHost() throws Exception {
    ServiceRBC serviceMock = Mockito.mock(ServiceRBC.class);
    Mockito.when(serviceMock.getData()).thenReturn(testBody);


    ControllerRBC controllerMock = Mockito.mock(ControllerRBC.class);
    Mockito.when(controllerMock.getMaxCourse()).thenReturn(expected);

    this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString(Double.toString(expected))));
  }
*/


}
