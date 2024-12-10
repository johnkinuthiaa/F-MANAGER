package com.slippery.fmanager.service;

import com.slippery.fmanager.dto.UserDto;
import com.slippery.fmanager.models.User;

public interface UserService {
    UserDto register(User user);
    UserDto login(User user);
    UserDto deleteUser(Long UserId);
}
