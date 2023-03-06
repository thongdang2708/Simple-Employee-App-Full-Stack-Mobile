package com.ltp.backend.message;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsernameResponse {

    @Size(min = 4, message = "Username must be at least 4 characters!")
    private String username;
}
