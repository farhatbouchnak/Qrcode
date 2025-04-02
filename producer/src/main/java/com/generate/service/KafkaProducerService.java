package com.generate.service;

import com.generate.model.PassGenResponse;
import com.generate.model.PasseGenRequest;
import com.generate.repo.PassGenRepository;
import com.generate.utils.Constants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, PasseGenRequest> kafkaTemplate;

    private final PassGenRepository passGenRepository;

    public KafkaProducerService(KafkaTemplate<String, PasseGenRequest> kafkaTemplate, PassGenRepository passGenRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.passGenRepository = passGenRepository;
    }

    public void sendMessage(List<PasseGenRequest> passeGenRequests) {
        for (PasseGenRequest passeGenRequest : passeGenRequests) {
            passeGenRequest.setRequestTime(new Date());
            passeGenRequest.setState(Constants.ENCOURS);
            String topic = passeGenRequest.isVip() ? "VIP_PASS_REQUESTS" : "REGULAR_PASS_REQUESTS";
            kafkaTemplate.send(topic, passeGenRequest);
        }
        System.out.println(passeGenRequests.size() + " Messages sent: ");
    }

    public PassGenResponse getByCriteria(String firstName, String lastName, String birthDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        return passGenRepository.getByCriteria(firstName, lastName, format.parse(birthDate)).orElseThrow(() -> new IllegalArgumentException("Pass was not generated"));
    }

    public List<PassGenResponse> searchAll() {
        return passGenRepository.findAll();
    }
}
