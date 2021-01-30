package com.guavapay.ms.auth.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class JwtToken implements Serializable {

    private String accessToken;
}
