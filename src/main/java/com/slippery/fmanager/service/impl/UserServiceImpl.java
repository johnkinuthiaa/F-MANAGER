package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.UserDto;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.models.Wallet;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.service.UserService;
import com.slippery.fmanager.service.WalletService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final JwtService jwtService;
    private final WalletService walletService;

    public UserServiceImpl(UserRepository repository, JwtService jwtService, WalletService walletService) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.walletService = walletService;
    }
    private UUID generateAccountNumber(){
        UUID uuid =UUID.randomUUID();
        return uuid;

    }

    @Override
    public UserDto register(User user) {
        UserDto response =new UserDto();
        User existingUser =repository.findUserByUsername(user.getUsername());
        if(existingUser ==null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAccountNumber(generateAccountNumber());
            user.setLoanLimit(100L);

            user.setBlackListed(false);
            repository.save(user);
            Wallet wallet = new Wallet();
            wallet.setAmount(0L);
            wallet.setTransactions(null);
            wallet.setUsers(user);
            wallet.setWalletAccountNumber(generateAccountNumber());
            walletService.createNewWallet(wallet);
            response.setMessage("user "+user.getUsername()+" was created successfully");
            response.setStatusCode(200);
            response.setUser(user);
        }else{
            response.setMessage("user "+user.getUsername()+" was not created" +
                    " successfully because user with similar username already exists ");
            response.setStatusCode(404);
        }
        return response;
    }

    @Override
    public UserDto login(User user) {
        UserDto response =new UserDto();
        User existingUser =repository.findUserByUsername(user.getUsername());

        if(existingUser !=null){
            existingUser.setActive(true);
            repository.save(existingUser);
            response.setJwtToken(jwtService.generateJwtToken(user.getUsername()));
            response.setMessage("user "+user.getUsername()+" logged in successfully");
            response.setStatusCode(200);
        }else{
            response.setMessage("user not logged in successfully as user with the username was not found!");
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public UserDto deleteUser(Long UserId) {
        return null;
    }

    @Override
    public UserDto getUserInformation(Long userId) {
        UserDto response =new UserDto();
        Optional<User> user =repository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("user not found");
            response.setStatusCode(204);
            return response;
        }
        response.setUser(user.get());
        response.setMessage("user with id"+userId);
        response.setStatusCode(200);
        return response;
    }
}
