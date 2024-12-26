package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.ExpendituresDto;
import com.slippery.fmanager.models.Expenditures;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.repository.ExpenditureRepository;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.service.ExpenditureService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {
    private final ExpenditureRepository repository;
    private final UserRepository userRepository;

    public ExpenditureServiceImpl(ExpenditureRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public ExpendituresDto createNewExpenditure(Expenditures expenditureDetails,Long userId) {
        Optional<User> user =userRepository.findById(userId);
        ExpendituresDto response =new ExpendituresDto();
        if(user.isPresent()){
            expenditureDetails.setSpentOn(LocalDateTime.now());
            expenditureDetails.setUser(user.get());
            repository.save(expenditureDetails);
            response.setMessage("new Expenditure saved");
            response.setStatusCode(200);
            response.setExpenditure(expenditureDetails);
        }else{
            response.setErrorMessage("user was not found!");
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public ExpendituresDto getAllExpendituresByUser(Long userId) {
        Optional<User> user =userRepository.findById(userId);
        ExpendituresDto response =new ExpendituresDto();

        if(user.isPresent() ){
            response.setAllExpenditures(repository.findAll()
                    .stream()
                    .filter(expenditures -> expenditures.getUser().getUsername().equals(user.get().getUsername()))
                    .collect(Collectors.toList()));
            response.setMessage("all expenditures by kinuthia");
            response.setStatusCode(200);
        }else{
            response.setMessage("no expenditures by yet");
            response.setStatusCode(200);
        }
        return response;
    }
}
