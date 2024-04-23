package org.example.ticketing.integrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentIntegrationTest {
    private Long userId;

    @BeforeEach
    public void setUp() {
        userId = 1L;
    }

    @Test
    public void payment_Success() {
        // 예약을 확인한다

        // 결제를 진행한다
    }
}
