package com.gishan.springangular.services;

import com.gishan.springangular.dto.NewPaymentDto;
import com.gishan.springangular.entities.Payment;
import com.gishan.springangular.entities.PaymentStatus;
import com.gishan.springangular.entities.PaymentType;
import com.gishan.springangular.entities.Student;
import com.gishan.springangular.repository.PaymentRepository;
import com.gishan.springangular.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public Payment savePayment(MultipartFile file, NewPaymentDto newPaymentDto) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"),"springangular-students","payments");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"springangular-students","payments",fileName+".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = studentRepository.findByCode(newPaymentDto.getStudentCode());
        Payment payment=Payment.builder()
                .paymentType(newPaymentDto.getPaymentType())
                .paymentStatus(PaymentStatus.CREATED)
                .date(newPaymentDto.getDate())
                .student(student)
                .amount(newPaymentDto.getAmount())
                .file(filePath.toUri().toString())
                .build();
        return paymentRepository.save(payment);

    }

    public byte[] getPaymentFile(Long id) throws IOException {
        Payment payment = paymentRepository.findById(id).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));

    }

    public Payment updatePaymentStatus(PaymentStatus status, Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).get();
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }
}
