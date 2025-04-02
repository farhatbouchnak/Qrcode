package com.generate.controller;


import com.generate.model.PassGenResponse;
import com.generate.model.PasseGenRequest;
import com.generate.service.KafkaProducerService;
import com.google.zxing.WriterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path="/api")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody List<PasseGenRequest> passeGenRequests) {
            kafkaProducerService.sendMessage(passeGenRequests);
        return "Passes generation in Kafka successfully";
    }


    @GetMapping(value = "/search/pass", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassGenResponse> searchPass(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName")  String lastName,
                                                      @RequestParam(value = "birthDate") String birthDate)  throws ParseException {
        PassGenResponse result = kafkaProducerService.getByCriteria(firstName, lastName, birthDate);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PassGenResponse>> searchAll() {
        List<PassGenResponse> result = kafkaProducerService.searchAll();
        return ResponseEntity.ok().body(result);
    }

}
