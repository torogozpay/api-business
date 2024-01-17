package com.torogoz.pagos.lightning;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/torogozpay/api/v1")
public class LightningController {
    @GetMapping("/ping")
    public ResponseEntity<?> hello(){
        return ResponseEntity.status(HttpStatus.OK).body("Servicios TogorozPay corriendo!");
    }
}
