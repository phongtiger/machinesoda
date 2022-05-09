package com.soda.machine.controller;

import com.soda.machine.Enum.Category;
import com.soda.machine.model.Order;
import com.soda.machine.payload.ValidOrderResponse;
import com.soda.machine.service.SodaMachineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.util.Objects;

@Controller
public class SodaMachineController {

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        setData(modelAndView);
        modelAndView.addObject("order", new Order());
        modelAndView.addObject("message", " ");
        return modelAndView;
    }

    @PostMapping("/order")
    public ModelAndView order(@ModelAttribute Order order) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("order", order);
        boolean isSoda = Category.Soda.SODA.name().equals(order.getType());
        boolean isPepsi = Category.Soda.PEPSI.name().equals(order.getType());
        boolean isCoka = Category.Soda.COKA.name().equals(order.getType());
        int quantity = order.getQuantity();
        ValidOrderResponse validOrderResponse = validatedOrder(order, modelAndView, isCoka, isPepsi, isSoda, quantity);
        if (validOrderResponse.isValid()) {
            double numberOfBottleOrder = Double.parseDouble(order.getMoney()) / (Objects.requireNonNull(Category.Soda.getValue(order.getType()))).doubleValue();
            if (numberOfBottleOrder < quantity) {
                modelAndView.addObject("message", "The amount is not enough!");
                setData(modelAndView);
                return modelAndView;
            }
            if (numberOfBottleOrder == quantity) {
                if (isSoda) {
                    SodaMachineService.NUMBERS_OF_SODA -= quantity;
                }
                if (isPepsi) {
                    SodaMachineService.NUMBERS_OF_PEPSI -= quantity;
                }
                if (isCoka) {
                    SodaMachineService.NUMBERS_OF_COKA -= quantity;
                }
                SodaMachineService.totalMoney = SodaMachineService.totalMoney.add(new BigInteger(order.getMoney()));
                System.out.println("Total money: " + SodaMachineService.totalMoney);
                modelAndView.addObject("message", "You have received: " + quantity + " bottle!");
                setData(modelAndView);
            }
            if (numberOfBottleOrder > quantity) {
                BigInteger change = new BigInteger("0");
                if (isSoda) {
                    SodaMachineService.NUMBERS_OF_SODA -= quantity;
                    change = new BigInteger(order.getMoney()).subtract(Objects.requireNonNull(Category.Soda.getValue(Category.Soda.SODA.name())).multiply(BigInteger.valueOf(quantity)));
                    SodaMachineService.totalMoney = SodaMachineService.totalMoney.add(new BigInteger(Category.Soda.SODA.val).multiply(BigInteger.valueOf(quantity)));
                }
                if (isCoka) {
                    SodaMachineService.NUMBERS_OF_COKA -= quantity;
                    change = new BigInteger(order.getMoney()).subtract(Objects.requireNonNull(Category.Soda.getValue(Category.Soda.COKA.name())).multiply(BigInteger.valueOf(quantity)));
                    SodaMachineService.totalMoney = SodaMachineService.totalMoney.add(new BigInteger(Category.Soda.COKA.val).multiply(BigInteger.valueOf(quantity)));
                }
                if (isPepsi) {
                    SodaMachineService.NUMBERS_OF_PEPSI -= quantity;
                    change = new BigInteger(order.getMoney()).subtract(Objects.requireNonNull(Category.Soda.getValue(Category.Soda.PEPSI.name())).multiply(BigInteger.valueOf(quantity)));
                    SodaMachineService.totalMoney = SodaMachineService.totalMoney.add(new BigInteger(Category.Soda.PEPSI.val).multiply(BigInteger.valueOf(quantity)));
                }

                System.out.println("Total money: " + SodaMachineService.totalMoney);
                modelAndView.addObject("message", "You will receive: " + quantity + " bottle and your change is: " + change + " VND!");
                setData(modelAndView);
            }
            return modelAndView;
        } else {
            return validOrderResponse.getModelAndView();
        }
    }

    private ValidOrderResponse validatedOrder(Order order, ModelAndView modelAndView, boolean isCoka, boolean isPepsi,
                                              boolean isSoda, Integer quantity) {
        ValidOrderResponse validOrderResponse = new ValidOrderResponse();
        setData(modelAndView);
        validOrderResponse.setModelAndView(modelAndView);
        validOrderResponse.setValid(true);
        String moneyValid = Category.VND.getName(order.getMoney());
        if (moneyValid == null) {
            modelAndView.addObject("message", "Only denominations allowed: " + Category.VND.getAllValue() + " VND");
            validOrderResponse.setValid(false);
        }
        if (Objects.isNull(quantity) || quantity < 0) {
            modelAndView.addObject("message", "Please enter the correct quantity!");
            setData(modelAndView);
            validOrderResponse.setValid(false);
        }
        if (isCoka) {
            if (SodaMachineService.NUMBERS_OF_COKA <= 0) {
                modelAndView.addObject("message", "Sold out " + Category.Soda.COKA.name());
                validOrderResponse.setValid(false);
            }
            if (SodaMachineService.NUMBERS_OF_COKA < quantity) {
                modelAndView.addObject("message", "Insufficient inventory for your requirements!");
                validOrderResponse.setValid(false);
            }
        }
        if (isPepsi) {
            if (SodaMachineService.NUMBERS_OF_PEPSI <= 0) {
                modelAndView.addObject("message", "Sold out " + Category.Soda.PEPSI.name());
                validOrderResponse.setValid(false);
            }
            if (SodaMachineService.NUMBERS_OF_PEPSI < quantity) {
                modelAndView.addObject("message", "Insufficient inventory for your requirements!");
                validOrderResponse.setValid(false);
            }
        }
        if (isSoda) {
            if (SodaMachineService.NUMBERS_OF_SODA <= 0) {
                modelAndView.addObject("message", "Sold out " + Category.Soda.SODA.name());
                validOrderResponse.setValid(false);
            }
            if (SodaMachineService.NUMBERS_OF_SODA < quantity) {
                modelAndView.addObject("message", "Insufficient inventory for your requirements!");
                validOrderResponse.setValid(false);
            }
        }
        return validOrderResponse;
    }

    private void setData(ModelAndView modelAndView) {
        modelAndView.addObject("coka", SodaMachineService.NUMBERS_OF_COKA);
        modelAndView.addObject("pepsi", SodaMachineService.NUMBERS_OF_PEPSI);
        modelAndView.addObject("soda", SodaMachineService.NUMBERS_OF_SODA);
        modelAndView.addObject("type", Category.Soda.values());
    }

}
