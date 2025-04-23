package com.example.novel_app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    int id;
    @Size(min = 10, max = 20,message = "ERROR_LENGTH")
    private String fullName;
    private String email;
    private String token;
}
