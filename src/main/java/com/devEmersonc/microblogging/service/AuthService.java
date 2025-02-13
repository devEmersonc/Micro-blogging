package com.devEmersonc.microblogging.service;

import com.devEmersonc.microblogging.dto.AuthRequest;
import com.devEmersonc.microblogging.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
