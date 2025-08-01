

package com.project.PCBuilder.rest.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.project.PCBuilder.rest.dto.AccountsDTO;
import com.project.PCBuilder.rest.dto.LoginRequest;
import com.project.PCBuilder.rest.services.AccountsService;
import com.project.PCBuilder.security.CustomUserDetailsService;
import com.project.PCBuilder.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping(value = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsRestController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsRestController.class);

    private final AccountsService service;

@Autowired
private BCryptPasswordEncoder encoder;
@Autowired
private JavaMailSender mailSender;

private final JwtUtil jwtUtil;            // ← add
private final CustomUserDetailsService uds; // ← add if you need it here


@Autowired
public AccountsRestController(AccountsService service,
                              JwtUtil jwtUtil,
                              CustomUserDetailsService uds) {
    this.service = service;
    this.jwtUtil = jwtUtil; // ✅ Use the injected bean — don't call constructor manually
    this.uds = uds;         // ✅ Use the injected bean — don't assign null
}

    @GetMapping
    public ResponseEntity<List<AccountsDTO>> findAll() {
        logger.debug("GET - findAll");
        List<AccountsDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{accountid}")
    public ResponseEntity<AccountsDTO> findById(@PathVariable Integer accountid) {
        logger.debug("GET - findById");
        AccountsDTO dto = service.findById(accountid);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody AccountsDTO accountsDTO) {
        logger.debug("POST - create");
        if (service.create(accountsDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/reset-password/request")
    public ResponseEntity<String> requestReset(@RequestParam String email) {
        if (!service.requestPasswordReset(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found or account not verified");
        }

        String token = service.getTokenByEmail(email);
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        // Send email
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Password Reset Request");
        msg.setText("Click this link to reset your password: " + resetLink);
        mailSender.send(msg);

        return ResponseEntity.ok("Reset link sent to email");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
        @RequestParam String token,
        @RequestParam String newPassword
    ) {
        if (service.resetPassword(token, newPassword)) {
            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }

    @PutMapping(value = "/{accountid}")
    public ResponseEntity<Void> save(@PathVariable Integer accountid, @RequestBody AccountsDTO accountsDTO) {
        logger.debug("PUT - save");
        service.save(accountid, accountsDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AccountsDTO accountsDTO) {
        logger.debug("PUT - update");
        if (service.update(accountsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{accountid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> partialUpdate(@PathVariable Integer accountid, @RequestBody AccountsDTO accountsDTO) {
        logger.debug("PATCH - partialUpdate");
        if (service.partialUpdate(accountid, accountsDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{accountid}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer accountid) {
        logger.debug("DELETE - deleteById");
        if (service.deleteById(accountid)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 // 1) Sign up
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AccountsDTO dto){
      if (!service.register(dto))
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
      // send verification email
      String link = "http://localhost:8080/api/v1/accounts/verify/" + dto.getToken();
      SimpleMailMessage msg = new SimpleMailMessage();
      msg.setTo(dto.getEmail());
      msg.setSubject("Please verify your email");
      msg.setText("Click to verify: " + link);
      mailSender.send(msg);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 2) Verify
    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verify(@PathVariable String token) {
      return service.verifyEmail(token)
        ? ResponseEntity.ok("Verified!")
        : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        AccountsDTO account = service.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid email or password"));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            account.getEmail(), account.getPasswordhased(), List.of());
        String token = jwtUtil.generateToken(userDetails); // ✅ generate token

        // ✅ return token so Flutter can save it
        return ResponseEntity.ok(Map.of(
            "token", token,
            "accountId", account.getAccountid(),
            "email", account.getEmail()
        ));
    }



    // 4) Log out
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest req) throws ServletException {
      req.logout();
      return ResponseEntity.noContent().build();
    }
}
