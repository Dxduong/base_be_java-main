package com.example.novel_app.service;

import com.example.novel_app.dto.request.OtpAuthorizeRequest;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.model.Otp;
import com.example.novel_app.repository.OtpRespository;
import com.example.novel_app.utils.AesEncryptionUtil;
import com.example.novel_app.utils.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OtpService {
    private final MailService mailService;
    private final OtpRespository otpRespository;
    private final RegisterService registerService;

    public boolean isOtpExpired(Otp otp) {
        return LocalDateTime.now().isAfter(otp.getExpiredAt());
    }

    public void sendOTP(String email) {
        String plaintextEmail = null;
        try {
            plaintextEmail = AesEncryptionUtil.decrypt(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!registerService.findEmailExisting(plaintextEmail)) {
            String optCode = OtpGenerator.generateOtp(6);
            String content = """
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <meta http-equiv="X-UA-Compatible" content="IE=edge">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <style>
                            body { font-family: Arial, sans-serif; background-color: #f4f4f9; color: #333; padding: 20px; }
                            h1 { color: #4CAF50; }
                            p { font-size: 16px; line-height: 1.6; }
                            .button {
                                display: inline-block;
                                background-color: #4CAF50;
                                color: white;
                                padding: 10px 20px;
                                text-decoration: none;
                                border-radius: 5px;
                                font-size: 16px;
                            }
                            .text_otp{
                                text-align: center;
                                height: 100px;
                            }
                            .footer { font-size: 14px; color: #777; text-align: center; padding: 10px 0; }
                        </style>
                    </head>
                    <body>
                    <h1>Mã OTP của bạn là: 🔑</h1>
                    <p>👋 Chào bạn,</p>
                    <p>🛡️ Bạn vừa yêu cầu mã OTP để thực hiện một tác vụ bảo mật. Vui lòng sử dụng mã OTP dưới đây để tiếp tục:</p>
                    <h2 style="color: #212121;" class="text_otp">${otp}</h2>
                    <p>⚠️ <strong>Lưu ý:</strong> Mã OTP này có hiệu lực trong 2 phút. Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này. 🚫</p>
                    <p>💌 Trân trọng, <br> Đội ngũ bảo mật của bạn 💼</p>
                    <div class="footer">
                    <p>📩 Gửi bởi Ứng dụng của bạn &copy; 2024 | Tất cả quyền được bảo vệ. 🔒</p>
                    </div>
                    </body>
                    </html>
                    """;
            String finalContent = content.replace("${otp}", optCode);
            String subject = "Notification from Novel App 🎶🎶🎶";
            mailService.sendmail(plaintextEmail, subject, finalContent);
            Otp newOtp = new Otp();
            newOtp.setEmail(email);
            newOtp.setOtpCode(optCode);
            otpRespository.save(newOtp);
        }
    }

    public void authorizeOtp(OtpAuthorizeRequest otpAuthorizeRequest) {
        try {
            String plaintextOTP = AesEncryptionUtil.decrypt(otpAuthorizeRequest.getOtpCode());
            Otp otp = otpRespository.findByEmailAndOtpCodeAndStatusOtp(otpAuthorizeRequest.getEmail()
                    , plaintextOTP, false);
            if (isOtpExpired(otp)) {
                throw new AppException(ErrorCode.OTP_INVALID, HttpStatus.BAD_REQUEST);
            } else {
                otp.setStatusOtp(true);
                otpRespository.save(otp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
