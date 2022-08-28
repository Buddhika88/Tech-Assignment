package com.payconiq.qa.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthTokenRequestDTO {

    private String username;
    private String password;
}
