package org.example.ticketing.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Long reservationId) {
        kafkaTemplate.send(topic, reservationId.toString());
    }

}
