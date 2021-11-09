package com.example.test1.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BillForReport implements Serializable {

    private Integer serialNumber;
    private Integer orderNumber;
    private String productName;
    private String paymentUserId;
    private String handler;
    private String transactionAmount;
}
