package com.stfalcon.chatkit.sample.api.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageAPI {
    private String content;
    private LocalDateTime created;
}
