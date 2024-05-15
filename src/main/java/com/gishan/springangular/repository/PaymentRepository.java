package com.gishan.springangular.repository;

import com.gishan.springangular.entities.Payment;
import com.gishan.springangular.entities.PaymentStatus;
import com.gishan.springangular.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String code);

    List<Payment> findByPaymentStatus(PaymentStatus status);

    List<Payment> findByPaymentType(PaymentType type);

}
