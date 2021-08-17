package com.mortgage.calc.api;

import com.mortgage.calc.model.MortgageCalcRequest;
import com.mortgage.calc.service.MortgageCalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/*
Property Price
Down Payment - has to be large enough like 5%
Annual interest rate
amortization period (5 years, increment between 5 and 30 years)
payment schedule (accelerated-bi-weekly, bi-weekly, monthly)

output
payment per payment schedule
error
 */
@RestController
@RequestMapping("/calculator")
@Validated
@CrossOrigin
public class MortgageController {

    @Autowired
    MortgageCalcService mortgageCalcService;

    @PostMapping(value = "/mortgage")
    public ResponseEntity<Double> getMortgageCalculator(@Valid @RequestBody MortgageCalcRequest request) throws Exception {
        return ResponseEntity.ok(mortgageCalcService.calculateMortgage(request));
    }


}
