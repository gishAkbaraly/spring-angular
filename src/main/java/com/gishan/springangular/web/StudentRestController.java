package com.gishan.springangular.web;

import com.gishan.springangular.dto.NewPaymentDto;
import com.gishan.springangular.entities.Payment;
import com.gishan.springangular.entities.PaymentStatus;
import com.gishan.springangular.entities.PaymentType;
import com.gishan.springangular.entities.Student;
import com.gishan.springangular.repository.PaymentRepository;
import com.gishan.springangular.repository.StudentRepository;
import com.gishan.springangular.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class StudentRestController {
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;


    public StudentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService, AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping(path = "/login")
    public Map<String, String> login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Instant now= Instant.now();
        String scope= authentication.getAuthorities()
                .stream().map(auth->auth.getAuthority())
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet= JwtClaimsSet.builder()
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .claim("scope",scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
        return Map.of("access-token",jwt.getTokenValue());

    }

    @GetMapping(path = "/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }
    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping(path = "/studentsByProgram")
    public List<Student> studentsByProgram(@RequestParam String programId){
        return studentRepository.findByProgramId(programId);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/payments")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }
    @GetMapping("/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).get();
    }
    @GetMapping("/students/{code}/payments")
    public List<Payment> paymentsByStudentCode(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping("/paymentsByStatus")
    public List<Payment> paymentsByStaus(@RequestParam PaymentStatus status){
        return paymentRepository.findByPaymentStatus(status);
    }

    @PutMapping("/payments/{paymentId}/updateStatus")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long paymentId){
        return paymentService.updatePaymentStatus(status,paymentId);
    }

    @PostMapping(path="/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file") MultipartFile file, NewPaymentDto newPaymentDto) throws IOException {
        return paymentService.savePayment(file, newPaymentDto);

    }

    @GetMapping(path="payments/{id}/file",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long id) throws IOException {
        return paymentService.getPaymentFile(id);
    }
}
