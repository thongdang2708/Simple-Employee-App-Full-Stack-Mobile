package com.ltp.backend.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInformationResponse {

    private String email;

    private String username;

    private Long id;

    private Long managerId;

}
