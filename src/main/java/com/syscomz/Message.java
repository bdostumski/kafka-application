package com.syscomz;

import java.time.LocalDateTime;

public record Message(String message, LocalDateTime created) {
}
