package com.example.novel_app.service;

import com.example.novel_app.dto.AccountDTO;
import com.example.novel_app.dto.request.ChangePasswordRequest;
import com.example.novel_app.dto.request.IntroSpectRequest;
import com.example.novel_app.dto.request.LoginRequest;
import com.example.novel_app.dto.request.UpdateInforRequest;
import com.example.novel_app.dto.response.IntrospectResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.mapper.UserMapper;
import com.example.novel_app.model.User;
import com.example.novel_app.repository.AuthRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthentiationService {
    @NonFinal
    @Value("${jwt.signerKey}")
    private String SCRET_KEY;
    private final AuthRepository authRepository;
    private final UserMapper userMapper;

    public AccountDTO login(LoginRequest loginRequest) {
        User user = authRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
        }
        if (!loginRequest.getPassword().equals(user.getPassword())) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
        }
        AccountDTO accountDTO = userMapper.toAccountDTO(user);
        accountDTO.setToken(generateToken(user));
        return accountDTO;
    }

    public AccountDTO changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = authRepository.findByEmail(changePasswordRequest.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
        }
        
        if (!changePasswordRequest.getCurrentPassword().equals(user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_CORRECT, HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(changePasswordRequest.getNewPassword());
        return userMapper.toAccountDTO(authRepository.save(user));
    }

    public AccountDTO updateInfor(UpdateInforRequest updateInforRequest) {
        User user = authRepository.findByEmail(updateInforRequest.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
        }
        
        if (!updateInforRequest.getPassword().equals(user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_NOT_CORRECT, HttpStatus.UNAUTHORIZED);
        }
        user.setFullName(updateInforRequest.getFullName());
        return userMapper.toAccountDTO(authRepository.save(user));
    }

    public String buildScope(User user) {
        StringJoiner scopes = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                scopes.add(role);
            });
        }
        return scopes.toString();
    }

    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail()).issuer("novel_app")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(15, ChronoUnit.DAYS)
                        .toEpochMilli()))
                .claim("scope", buildScope(user))
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
        }  catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmailFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

    public List<String> getScopesFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            String scope = (String) signedJWT.getJWTClaimsSet().getClaim("scope");

            if (scope == null) {
                throw new AppException(ErrorCode.SCOPE_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }

            return Arrays.asList(scope.split(" "));
        } catch (ParseException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }
}