package com.example.novel_app.controller.webcontroller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "web/auth")
public class PasswordController {

    // Hiển thị trang đổi mật khẩu
    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "change-password";  // Trả về trang HTML đổi mật khẩu
    }

    // Xử lý yêu cầu đổi mật khẩu
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword) {

        // Kiểm tra xem mật khẩu mới và mật khẩu xác nhận có khớp không
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/change-password?error=Passwords do not match";
        }

        // Giả sử mật khẩu hiện tại đúng (trong thực tế, bạn cần kiểm tra mật khẩu hiện tại từ cơ sở dữ liệu)
        String storedPassword = "oldPassword123";  // Mật khẩu cũ giả định

        // Sử dụng BCrypt để mã hóa mật khẩu
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(currentPassword, storedPassword)) {
            // Mật khẩu đúng, cập nhật mật khẩu mới
            String encodedNewPassword = encoder.encode(newPassword);
            // Lưu mật khẩu mới vào cơ sở dữ liệu (ví dụ: sử dụng một dịch vụ UserService)
            // userService.updatePassword(encodedNewPassword);

            return "redirect:/change-password?success=Password changed successfully";
        } else {
            return "redirect:/change-password?error=Incorrect current password";
        }
    }
}
