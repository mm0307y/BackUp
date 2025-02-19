package com.example.demo.model0206;

import lombok.Data;

@Data
public class OAuthToken0213 {
    public String access_token;
    public String token_type;
    public String refresh_token;
    public int expires_in;
    public String scope;
    public int refresh_token_expires_in;
    public String id_token;
}
