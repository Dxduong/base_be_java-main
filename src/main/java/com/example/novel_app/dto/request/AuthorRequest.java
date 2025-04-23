package com.example.novel_app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorRequest {
    String authorName;
    String bio;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate;
}
