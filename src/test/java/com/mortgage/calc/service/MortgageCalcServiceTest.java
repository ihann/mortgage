package com.mortgage.calc.service;

import com.mortgage.calc.constant.ScheduleType;
import com.mortgage.calc.model.MortgageCalcRequest;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MortgageCalcServiceTest {

    @Autowired
    public MortgageCalcService mortgageCalcService;
    public MortgageCalcRequest request;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void mortgageCalcServiceTest() throws Exception {
        //given
        givenValidMonthlyPaymentInput();

        //when
        double result = mortgageCalcService.calculateMortgage(request);

        //then
        assertEquals(4009.28, result);
    }

    @Test
    public void mortgageCalcServiceSmallPropertyPriceExceptionTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Property price must be between 10000 - 999999999");

        //given
        givenInvalidPriceMonthlyPaymentInput();

        //when
        double result = mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServiceSmallRateExceptionTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Rate must be between 0.1 - 99.99");

        //given
        givenInvalidSmallRateInput();

        //when
        double result = mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServiceLargeRateExceptionTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Rate must be between 0.1 - 99.99");

        //given
        givenInvalidLargeRateInput();

        //when
        double result = mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServicePropertyPriceLessThanDownPaymentTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Property price should not be less than or equal to down payment");
        //given
        givenPriceLessThanDownPaymentMonthlyPaymentInput();

        //when
        double result = mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServiceRequestNullTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Empty request");
        //given
        givenRequestNullInput();

        //when
        double result = mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServiceRequestNullScheduleTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Schedule type must not be null");

        //given
        givenRequestNullScheduleInput();

        //when
        mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServiceAmortizationLowInputTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Amortization must be between 5 - 30");
        //given
        givenRequestAmortizationLowInput();

        //when
        mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServiceAmortizationHighInputTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Amortization must be between 5 - 30");
        //given
        givenRequestAmortizationHighInput();

        //when
        mortgageCalcService.calculateMortgage(request);
    }

    @Test
    public void mortgageCalcServiceDownHighInputTest() throws Exception {
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Down payment must be between 1000 - 99999999");
        //given
        givenRequestDownHighInput();

        //when
        mortgageCalcService.calculateMortgage(request);
    }

    private void givenRequestNullInput() {
        request = null;
    }

    private void givenInvalidPriceMonthlyPaymentInput() {
        request = new MortgageCalcRequest(9999d, 2000d, 1.9, 20, ScheduleType.MONTHLY);
    }

    private void givenValidMonthlyPaymentInput() {
        request = new MortgageCalcRequest(1000000d, 200000d, 1.9, 20, ScheduleType.MONTHLY);
    }

    private void givenPriceLessThanDownPaymentMonthlyPaymentInput() {
        request = new MortgageCalcRequest(100000d, 200000d, 1.9, 20, ScheduleType.MONTHLY);
    }

    private void givenRequestNullScheduleInput() {
        request = new MortgageCalcRequest(100000d, 200000d, 1.9, 20, null);

    }

    private void givenRequestAmortizationLowInput() {
        request = new MortgageCalcRequest(1000000d, 200000d, 1.9, 4, ScheduleType.MONTHLY);

    }

    private void givenRequestAmortizationHighInput() {
        request = new MortgageCalcRequest(1000000d, 200000d, 1.9, 31, ScheduleType.MONTHLY);

    }

    private void givenRequestDownHighInput() {
        request = new MortgageCalcRequest(999999999d, 100000000d, 1.9, 30, ScheduleType.MONTHLY);

    }

    private void givenInvalidSmallRateInput() {
        request = new MortgageCalcRequest(1000000d, 500000d, 0.01, 30, ScheduleType.MONTHLY);

    }

    private void givenInvalidLargeRateInput() {
        request = new MortgageCalcRequest(1000000d, 500000d, 100d, 30, ScheduleType.MONTHLY);

    }


}