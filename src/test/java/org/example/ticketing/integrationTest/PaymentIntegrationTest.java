package org.example.ticketing.integrationTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"payment"})
public class PaymentIntegrationTest {

//    @Autowired
//    private PaymentUseCase paymentUseCase;
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    private BlockingQueue<ConsumerRecord<String, String>> records = new LinkedBlockingQueue<>();
//
//    @KafkaListener(topics = "payment")
//    public void listen(ConsumerRecord<String, String> record) {
//        records.add(record);
//    }
//
//    @Test
//    public void testExecute() throws InterruptedException {
//        // Arrange
//        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(1L, 1L);
//
//        // Act
//        PaymentResponseDTO result = paymentUseCase.execute(paymentRequestDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("결제 완료", result.message());
//
//        // Kafka 메시지 검증
//        ConsumerRecord<String, String> receivedRecord = records.poll(10, TimeUnit.SECONDS);
//        assertNotNull(receivedRecord);
//        assertEquals(1L, receivedRecord.partition());
//    }
}
