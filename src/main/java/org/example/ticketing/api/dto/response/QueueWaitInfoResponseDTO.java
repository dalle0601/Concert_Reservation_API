package org.example.ticketing.api.dto.response;

public record QueueWaitInfoResponseDTO (
        Long onGoing,
        Long onWait
){ }
