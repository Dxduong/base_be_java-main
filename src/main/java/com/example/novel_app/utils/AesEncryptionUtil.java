package com.example.novel_app.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesEncryptionUtil {
    //    ECB (Electronic Codebook): Không cần IV. Mỗi block plaintext được mã hóa riêng biệt.
    //    CBC (Cipher Block Chaining): Yêu cầu IV. Mỗi block plaintext sẽ bị XOR với ciphertext của block trước đó.
    //    CFB (Cipher Feedback): Dữ liệu được chia thành các block nhỏ hơn, và mỗi block sẽ được xử lý một cách độc lập.
    //    OFB (Output Feedback): Tương tự CFB, nhưng kết quả từ quá trình mã hóa được "feedback" lại để xử lý dữ liệu.
    //    CTR (Counter): Sử dụng một counter làm IV, có thể chạy song song các khối.
    //    trừ ecb  thì thay nào cũng được
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final String FIXED_KEY = "ewjSm194wEQSCH4M";

    private static final SecretKey SECRET_KEY = new SecretKeySpec(FIXED_KEY.getBytes(), SECRET_KEY_ALGORITHM);

    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]); // IV mặc định
        cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }


    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]); // IV mặc định
        cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY, ivParameterSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted);
    }
}
