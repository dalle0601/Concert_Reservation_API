package org.example.ticketing.api.usecase.user;

import lombok.RequiredArgsConstructor;
import org.example.ticketing.api.dto.user.request.UserJoinRequestDTO;
import org.example.ticketing.domain.user.model.UserInfo;
import org.example.ticketing.domain.user.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJoinUseCase {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;


    public String execute(UserJoinRequestDTO userJoinRequestDTO) {
        String userId = userJoinRequestDTO.userId();
        String userPassword = userJoinRequestDTO.password();

        boolean isExist = userService.existByUserId(userId);

        if(isExist) {
            return "이미 존재하는 계정임";
        }

        String encryptedPassword = passwordEncoder.encode(userPassword);

        UserInfo userInfo = new UserInfo(userId, encryptedPassword, 0L);

        userService.save(userInfo);

        return "jwt";
    }
}
