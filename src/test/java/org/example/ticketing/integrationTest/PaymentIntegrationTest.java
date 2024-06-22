package org.example.ticketing.integrationTest;

import org.example.ticketing.api.dto.point.reqeust.PaymentRequestDTO;
import org.example.ticketing.api.dto.point.response.PaymentResponseDTO;
import org.example.ticketing.api.usecase.point.PaymentUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"payment"}, brokerProperties = {"log.dir=tmp/kafka-logs"})
public class PaymentIntegrationTest {

    @Autowired
    private PaymentUseCase paymentUseCase;

    private static BlockingQueue<Long> records = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "payment")
    public void listen(Long reservationId) {
        records.add(reservationId);
    }

    @Test
    public void testExecute() throws InterruptedException {
        // Arrange
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1L, 1L);

        // Act
        PaymentResponseDTO result = paymentUseCase.execute(paymentRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("결제 완료", result.message());

        // Kafka 메시지 검증
        Long receivedReservationId = records.poll(10, TimeUnit.SECONDS);
        assertNotNull(receivedReservationId);
        assertEquals(1L, receivedReservationId);
    }
}
