package com.mortgage.calc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortgage.calc.constant.ScheduleType;
import com.mortgage.calc.model.MortgageCalcRequest;
import com.mortgage.calc.service.MortgageCalcService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(MortgageController.class)
public class MortgageControllerTest {

    @MockBean
    private MortgageCalcService mortgageCalcService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<MortgageCalcRequest> jsonRequest;
    private JacksonTester<Double> jsonResponse;
    private MortgageCalcRequest mortgageCalcRequest;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getMortgageCalculatorValidTest() throws Exception {
        givenValidMortgageRequest();

        MockHttpServletResponse response = mvc.perform(post("/calculator/mortgage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.write(mortgageCalcRequest).getJson()))
                .andReturn()
                .getResponse();

        assertThat(response.getContentAsString()).isEqualTo(
                jsonResponse.write(2000d).getJson());
    }

    private void givenValidMortgageRequest() throws Exception {
        mortgageCalcRequest = new MortgageCalcRequest(1000000d, 20000d, 1.9, 20, ScheduleType.MONTHLY);
        given(mortgageCalcService.calculateMortgage(any(MortgageCalcRequest.class))).willReturn(2000d);
    }

}