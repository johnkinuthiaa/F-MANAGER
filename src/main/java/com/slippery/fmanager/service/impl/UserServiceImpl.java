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
    private final SendEmail sendEmail ;

    public UserServiceImpl(UserRepository repository, JwtService jwtService, WalletService walletService, SendEmail sendEmail) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.walletService = walletService;
        this.sendEmail = sendEmail;
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
            user.setPassword(user.getPassword());
            wallet.setUsers(user);
            wallet.setWalletAccountNumber(generateAccountNumber());
            walletService.createNewWallet(wallet);

            sendEmail.Mail(user.getEmail()," Welcome to financia - Your Journey to Better Financial Management Starts Here!",
                    "Dear "+user.getUsername()+",\n" +
                            "\n" +
                            "Thank you for registering with Financia! We’re excited to have you onboard as you take the next step towards managing your finances with ease and confidence. \n" +
                            "\n" +
                            "With Financia, you’ll have all the tools you need to track spending, create budgets, set financial goals, and much more—all in one easy-to-use platform. Whether you’re looking to save, invest, or simply gain a clearer picture of your financial situation, we’re here to help you achieve your goals.\n" +
                            "\n" +
                            "**Here’s how to get started:**\n" +
                            "\n" +
                            "1. **Log in** to your account using your email and password.\n" +
                            "2. **Set up your profile** and connect your bank accounts or credit cards to track transactions.\n" +
                            "3. **Explore features** like budgeting, savings goals, or financial reports to customize the app for your needs.\n" +
                            "\n" +
                            "If you have any questions or need assistance, our support team is here to help! Feel free to reach out to us at johnmuniu477@gmail.com for more details.\n" +
                            "\n" +
                            "We’re looking forward to being part of your financial journey!\n" +
                            "\n" +
                            "Best regards,  \n" +
                            "The Financia Team  \n" +
                            "[] | [johnmuniu477@gmail.com]"
                    );
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
        User user1 = user.get();
        user1.setPassword(null);
        response.setUser(user1);
        response.setMessage("user with id"+userId);
        response.setStatusCode(200);
        return response;
    }
}
