package com.gishan.springangular.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private double amount;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    private String file;

    @ManyToOne
    private Student student;

}
