package com.users.jwt;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtBlackList {

    private Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blackListToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlackListed(String token) {
        return blacklistedTokens.contains(token);
    }

    public void removeExpiredTokens(JwtService jwtService) {
        blacklistedTokens.removeIf(token -> {
            try {
                return jwtService.isTokenExpired(token);
            } catch (Exception e) {
                return true;
            }
        });
    }
}
