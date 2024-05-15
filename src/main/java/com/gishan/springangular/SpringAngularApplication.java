package com.gishan.springangular;

import com.gishan.springangular.entities.Payment;
import com.gishan.springangular.entities.PaymentStatus;
import com.gishan.springangular.entities.PaymentType;
import com.gishan.springangular.entities.Student;
import com.gishan.springangular.repository.PaymentRepository;
import com.gishan.springangular.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SpringAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("John")
                    .lastName("Jones")
                    .code("111112")
                    .programId("CS50")
                    .build());

			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Jonny")
					.lastName("Dhill")
					.code("111113")
					.programId("CS50")
					.build());


			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Sarah")
					.lastName("Moore")
					.code("111114")
					.programId("CS51")
					.build());

			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Yasmina")
					.lastName("Mariz")
					.code("111115")
					.programId("CS52")
					.build());

			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();

			studentRepository.findAll().forEach(student -> {
				for(int i =0; i <10; i++) {
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*2000))
							.paymentType(paymentTypes[index])
							.paymentStatus(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(student)
							.build();
					paymentRepository.save(payment);
				}
			});
        };
    }
}
