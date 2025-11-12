package com.example.prueba.jwt;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Una vez que el usuario hizo logout esta clase invalida el token.
 */
@Component
public class TokenBlacklist {
    
    private final ConcurrentHashMap<String, LocalDateTime> blacklist = new ConcurrentHashMap<>();
    
    public void addToBlacklist(String jti, java.util.Date expiration) {
        LocalDateTime expDateTime = LocalDateTime.ofInstant(
            expiration.toInstant(), 
            java.time.ZoneId.systemDefault()
        );
        blacklist.put(jti, expDateTime);
    }
    
    public boolean isBlacklisted(String jti) {
        LocalDateTime expiration = blacklist.get(jti);
        if (expiration == null) {
            return false;
        }
        
        // Limpiar tokens expirados
        if (LocalDateTime.now().isAfter(expiration)) {
            blacklist.remove(jti);
            return false;
        }
        
        return true;
    }
    
    public void cleanExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        blacklist.entrySet().removeIf(entry -> now.isAfter(entry.getValue()));
    }
}
