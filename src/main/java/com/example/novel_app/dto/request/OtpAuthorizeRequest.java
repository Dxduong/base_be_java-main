package com.example.novel_app.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpAuthorizeRequest {
    String email;
    String otpCode;
}
