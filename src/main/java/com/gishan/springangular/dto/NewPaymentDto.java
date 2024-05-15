package com.gishan.springangular.dto;

import com.gishan.springangular.entities.PaymentType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NewPaymentDto {
    private double amount;

    private LocalDate date;

    private PaymentType paymentType;

    private String studentCode;
}
