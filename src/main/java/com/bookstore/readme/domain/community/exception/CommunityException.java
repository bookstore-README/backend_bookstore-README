package com.bookstore.readme.domain.community.exception;

public class CommunityException extends RuntimeException {
    private final CommunityStatus communityStatus;

    public CommunityException(CommunityStatus communityStatus) {
        this.communityStatus = communityStatus;
    }

    public int getStatus() {
        return communityStatus.getStatus();
    }

    public String getMessage() {
        return communityStatus.getMessage();
    }
}
