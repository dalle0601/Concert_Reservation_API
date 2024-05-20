package org.example.ticketing.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private final PaymentEventProducer paymentEventProducer;

    // 트랜잭션이 커밋된 이후에 메세지를 비동기로 발행하도록 추가
    @TransactionalEventListener
    @Async
    public void handlePaymentCompletedEvent(PaymentCompletedEvent event) {
        paymentEventProducer.send("payment", event.reservationId());
    }



}
