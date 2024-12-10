package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.UserDto;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto register(User user) {
        UserDto response =new UserDto();
        User existingUser =repository.findUserByUsername(user.getUsername());
        if(existingUser ==null){
            repository.save(user);
            response.setMessage("user "+user.getUsername()+" was created successfully");
            response.setStatusCode(200);
        }else{
            response.setMessage("user "+user.getUsername()+" was not created" +
                    " successfully because user with similar username already exists ");
            response.setStatusCode(404);
        }
        return response;
    }

    @Override
    public UserDto login(User user) {
        return null;
    }

    @Override
    public UserDto deleteUser(Long UserId) {
        return null;
    }
}
