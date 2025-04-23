package com.example.novel_app.mapper;

import com.example.novel_app.dto.AccountDTO;
import com.example.novel_app.dto.request.RegisterRequest;
import com.example.novel_app.dto.response.UserResponse;
import com.example.novel_app.model.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest registerRequest);
    AccountDTO toAccountDTO(User user);
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponseList(List<User> userList);
}
