package com.example.novel_app.controller;

import com.example.novel_app.dto.request.AesRequest;
import com.example.novel_app.dto.response.ApiResponse;
import com.example.novel_app.exception.AppException;
import com.example.novel_app.exception.ErrorCode;
import com.example.novel_app.model.TestAes;
import com.example.novel_app.repository.AesRepository;
import com.example.novel_app.utils.AesEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "aes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AesController {
    private final AesRepository aesRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> createRecodeAes(@RequestBody AesRequest aesRequest) throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("success");
        String cypherText = AesEncryptionUtil.encrypt(aesRequest.getPlaintext());
        TestAes testAes = new TestAes();
        testAes.setAesValue(cypherText);
        aesRepository.save(testAes);
        apiResponse.setData(testAes.getAesValue());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<String>> getPlainText(@PathVariable int id) throws Exception {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("success");
        TestAes testAes = aesRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEST_ERROR, HttpStatus.BAD_GATEWAY));
        String cypherText = AesEncryptionUtil.decrypt(testAes.getAesValue());
        apiResponse.setData(cypherText);
        return ResponseEntity.ok(apiResponse);
    }
}
