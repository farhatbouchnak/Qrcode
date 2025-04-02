package com.generate.service;


import com.generate.handler.WebSocketHandler;
import com.generate.model.PassGenResponse;
import com.generate.model.PasseGenRequest;
import com.generate.repo.PassGenRepository;
import com.generate.utils.Constants;
import com.generate.utils.QRCodeUtils;
import com.google.zxing.WriterException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service
public class KafkaConsumerService {

    private final Map<Long, PassGenResponse> passStore = new ConcurrentHashMap<>();

    private final BlockingQueue<PasseGenRequest> queue = new LinkedBlockingQueue<>();

    private final WebSocketHandler webSocketHandler;

    private final PassGenRepository passGenRepository;

    public KafkaConsumerService(WebSocketHandler webSocketHandler, PassGenRepository passGenRepository) {
        this.webSocketHandler = webSocketHandler;
        this.passGenRepository = passGenRepository;
        startProcessing();
    }

    @KafkaListener(topics = {"VIP_PASS_REQUESTS", "REGULAR_PASS_REQUESTS"}, groupId = "group_id")
    public void receivePassRequest(PasseGenRequest request) {
        queue.offer(request);
    }

    private void startProcessing() {
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                try {
                    List<PasseGenRequest> batch = new ArrayList<>();
                    queue.drainTo(batch, 10); // Collect up to 10 requests at a time
                    batch.sort(Comparator.comparing((PasseGenRequest r) -> !r.isVip())); // Sort VIPs first
                    for (PasseGenRequest request : batch) {
                        processPassRequest(request);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void processPassRequest(PasseGenRequest request) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        PassGenResponse regularPassResponse = new PassGenResponse(request.getFirstName(),
                request.getLastName(), formatter.parse(request.getBirthDate()), request.isVip(),
                request.getRequestTime(), new Date(), null);
        try {
            regularPassResponse.setState(Constants.CREATED);
            regularPassResponse.setQrCode(QRCodeUtils.generateQRCode(request.getFirstName() + request.getLastName()));
            passGenRepository.save(regularPassResponse);
            passStore.put(regularPassResponse.getId(), regularPassResponse);
            webSocketHandler.sendUpdate(regularPassResponse);
        } catch (IOException | WriterException e) {
            regularPassResponse.setState(Constants.ECHOUE);
            passGenRepository.save(regularPassResponse);
            e.printStackTrace();
        }
    }

}
