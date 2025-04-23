package com.example.novel_app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    int code;
    String message;
    T data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        @Size(min = 10, max = 20,message = "ERROR_LENGTH")
        private String username;
    //    private String password;
        private String email;
    }
}
