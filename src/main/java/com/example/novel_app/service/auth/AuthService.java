package com.example.novel_app.service.auth;

import com.example.novel_app.dto.AccountDTO;
import com.example.novel_app.dto.request.IntroSpectRequest;
import com.example.novel_app.dto.response.IntrospectResponse;
import com.example.novel_app.model.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Slf4j
@Service
public class AuthService {
    @NonFinal
    @Value("${jwt.signerKey}")
    private String SCRET_KEY;

    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        // subject : doi tuong cua token
        // issuse : ten to chuc

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail()).issuer("novel_app")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS)
                .toEpochMilli()))
                .claim("scope","abc")
                // add more claim
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SCRET_KEY.getBytes()));
        } catch (JOSEException e) {
            log.error("Can't create token " + e.getMessage());
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }
    public IntrospectResponse verifyToken(IntroSpectRequest introSpectRequest) {
        var token = introSpectRequest.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SCRET_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);
            return new IntrospectResponse(verified && expityTime.after(new Date()));

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
