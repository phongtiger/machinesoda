package com.soda.machine.model;

import lombok.Data;

@Data
public class Order {
    private String type;
    private Integer quantity;
    private String money;
}
