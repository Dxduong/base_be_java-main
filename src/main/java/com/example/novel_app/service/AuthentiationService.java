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
import com.example.novel_app.utils.AesEncryptionUtil;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AccountDTO login(LoginRequest loginRequest) {
        User user = authRepository.findByEmail(loginRequest.getEmail());
        try {
            String plaintextPassword = AesEncryptionUtil.decrypt(loginRequest.getPassword());
            if (user == null) {
                throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
            }
            if (!passwordEncoder.matches(plaintextPassword, user.getPassword())) {
                throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
            }
            AccountDTO accountDTO = userMapper.toAccountDTO(user);
            try {
                String plaintextEmail = AesEncryptionUtil.decrypt(accountDTO.getEmail());
                accountDTO.setToken(generateToken(user));
                accountDTO.setEmail(plaintextEmail);
                return accountDTO;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public AccountDTO changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = authRepository.findByEmail(changePasswordRequest.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
        }
        try {
            String plaintextCurrentPassword =
                    AesEncryptionUtil.decrypt(changePasswordRequest.getCurrentPassword());
            String plaintextNewPassword =
                    AesEncryptionUtil.decrypt(changePasswordRequest.getNewPassword());
            String hashedPassword = user.getPassword();
            if (passwordEncoder.matches(plaintextCurrentPassword, hashedPassword)) {
                throw new AppException(ErrorCode.PASSWORD_NOT_CORRECT, HttpStatus.UNAUTHORIZED);
            }
            user.setPassword(passwordEncoder.encode(plaintextNewPassword));
            return userMapper.toAccountDTO(authRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AccountDTO updateInfor(UpdateInforRequest updateInforRequest) {
        User user = authRepository.findByEmail(updateInforRequest.getEmail());
        if (user == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_EXIST, HttpStatus.UNAUTHORIZED);
        }
        try {
            String plaintextPassword =
                    AesEncryptionUtil.decrypt(updateInforRequest.getPassword());
            String plaintextFullName = AesEncryptionUtil.decrypt(updateInforRequest.getFullName());
            String hashedPassword = user.getPassword();
            if (!passwordEncoder.matches(plaintextPassword, hashedPassword)) {
                throw new AppException(ErrorCode.PASSWORD_NOT_CORRECT, HttpStatus.UNAUTHORIZED);
            }
            user.setFullName(plaintextFullName);
            return userMapper.toAccountDTO(authRepository.save(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        // subject : doi tuong cua token
        // issuse : ten to chuc

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail()).issuer("novel_app")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(15, ChronoUnit.SECONDS)
                        .toEpochMilli()))
                .claim("scope", buildScope(user))
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
            // Parse token để trích xuất payload
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Lấy giá trị của claim "scope"
            String scope = (String) signedJWT.getJWTClaimsSet().getClaim("scope");

            if (scope == null) {
                throw new AppException(ErrorCode.SCOPE_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }

            // Tách scope thành danh sách bởi ký tự " "
            return Arrays.asList(scope.split(" "));
        } catch (ParseException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID, HttpStatus.UNAUTHORIZED);
        }
    }

}
