package com.mortgage.calc.service;

import com.mortgage.calc.model.MortgageCalcRequest;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.mortgage.calc.constant.Constants.*;

@Service
public class MortgageCalcService {


    public double calculateMortgage(MortgageCalcRequest request) throws Exception {
        validateRequest(request);

        double principle = request.getPrice() - request.getDown();
        int schedule = request.getScheduleType().getValue();
        double r = (request.getRate() / 100) / schedule;
        int payment = request.getAmortization() * schedule;
        double m = (principle * r) / (1 - Math.pow((1 + r), (-1 * payment)));
        BigDecimal bd = BigDecimal.valueOf(m);
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd.doubleValue();
    }

    private void validateRequest(MortgageCalcRequest request) throws ValidationException {
        if (request != null) {
            if (request.getPrice() < MIN_TEN_THOUSAND || request.getPrice() > MAX_PRICE) {
                throw new ValidationException("Property price must be between 10000 - 999999999");
            }

            if (request.getDown() < MIN_TEN_THOUSAND || request.getDown() > MAX_DOWN) {
                throw new ValidationException("Down payment must be between 1000 - 99999999");
            }

            if (request.getRate() < MIN_RATE || request.getRate() > MAX_RATE) {
                throw new ValidationException("Rate must be between 0.1 - 99.99");
            }

            if (request.getAmortization() < MIN_AMORTIZATION || request.getAmortization() > MAX_AMORTIZATION) {
                throw new ValidationException("Amortization must be between 5 - 30");
            }

            if (request.getScheduleType() == null) {
                throw new ValidationException("Schedule type must not be null");
            }

            if (request.getPrice() <= request.getDown()) {
                throw new ValidationException("Property price should not be less than or equal to down payment");
            }

        } else {
            throw new ValidationException("Empty request");
        }

    }


}
