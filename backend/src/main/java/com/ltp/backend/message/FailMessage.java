package com.ltp.backend.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class FailMessage extends CommonSuccessfulMessage {

    public FailMessage(String message) {
        super(message);
    }

}
