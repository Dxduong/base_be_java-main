package com.example.novel_app.controller;

import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "email")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailController {
    private final MailService mailService;

    @GetMapping
    public ResponseEntity<ApiResponse> sendEmailWarning() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        String to = "duy@gmail.com";
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
                        .footer { font-size: 14px; color: #777; text-align: center; padding: 10px 0; }
                    </style>
                </head>
                <body>
                               <h1>Cảnh Báo Đăng Nhập 🚨</h1>
                               <p>Chào bạn,</p>
                               <p>Chúng tôi phát hiện một hoạt động đăng nhập đáng ngờ trên tài khoản của bạn. Để bảo mật tài khoản, chúng tôi khuyên bạn nên thay đổi mật khẩu ngay lập tức.</p>
                               <p><strong>Vui lòng thay đổi mật khẩu của bạn ngay bây giờ để tránh rủi ro bảo mật.</strong> 🔒</p>
                               <p>Nhấn vào nút bên dưới để đổi mật khẩu:</p>
                               <a href="https://192.168.0.2:8888/web/auth/change-password" class="button">Đổi Mật Khẩu Ngay! 🔑</a>
                
                               <p>Trân trọng, <br> Đội ngũ bảo mật của bạn 💼</p>
                
                               <div class="footer">
                                   <p>Gửi bởi Ứng dụng của bạn &copy; 2024 | Tất cả quyền được bảo vệ. 🔒</p>
                               </div>
                           </body>
            </html>
                
                """;
        String subject = "Cảnh báo: Quên mật khẩu 🤖🤖🤖";
        mailService.sendmail(to, subject, content);
        apiResponse.setMessage("Sending email to " + to);
        apiResponse.setData(null);
        return ResponseEntity.status(200).body(apiResponse);
    }
}
