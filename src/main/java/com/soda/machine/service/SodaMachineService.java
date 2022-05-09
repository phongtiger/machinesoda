package com.soda.machine.service;

import com.soda.machine.Enum.Category;
import com.soda.machine.model.Soda;
import com.soda.machine.model.SodaMachine;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@Data
public class SodaMachineService {
    public static BigInteger totalMoney = new BigInteger("0");
    public static int NUMBERS_OF_COKA = 1000;
    public static int NUMBERS_OF_PEPSI = 1000;
    public static int NUMBERS_OF_SODA = 1000;
    public static SodaMachine sodaMachine;
    static {
        sodaMachine = new SodaMachine();
        Soda soda = new Soda();
        soda.setName(Category.Soda.SODA.name());
        soda.setPrice(Category.Soda.getValue(Category.Soda.SODA.name()));
        Soda coka = new Soda();
        coka.setName(Category.Soda.COKA.name());
        soda.setPrice(Category.Soda.getValue(Category.Soda.COKA.name()));
        Soda pepsi = new Soda();
        pepsi.setName(Category.Soda.PEPSI.name());
        pepsi.setPrice(Category.Soda.getValue(Category.Soda.PEPSI.name()));
        sodaMachine.setSoda(soda);
        sodaMachine.setCoka(coka);
        sodaMachine.setPepsi(pepsi);
    }
}
