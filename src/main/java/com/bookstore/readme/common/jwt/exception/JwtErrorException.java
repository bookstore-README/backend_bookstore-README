package com.bookstore.readme.common.jwt.exception;

import com.bookstore.readme.common.jwt.JwtErrorStatus;

public class JwtErrorException extends RuntimeException {

    private final JwtErrorStatus jwtErrorStatus;

    public JwtErrorException(JwtErrorStatus jwtErrorStatus) {
        this.jwtErrorStatus = jwtErrorStatus;
    }

    public int getStatus() {
        return jwtErrorStatus.getStatus();
    }

    public String getMessage() {
        return jwtErrorStatus.getMessage();
    }
}
