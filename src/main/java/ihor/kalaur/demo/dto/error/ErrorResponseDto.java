package ihor.kalaur.demo.dto.error;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorResponseDto(
        LocalDateTime timestamp,
        HttpStatus status,
        String message
) {}

