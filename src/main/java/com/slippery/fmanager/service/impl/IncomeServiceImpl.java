package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.IncomeDto;
import com.slippery.fmanager.models.Incomes;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.repository.IncomesRepository;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.service.IncomesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
// income service
public class IncomeServiceImpl implements IncomesService {
    private final IncomesRepository repository;
    private final UserRepository userRepository;

    public IncomeServiceImpl(IncomesRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }


    @Override
    public IncomeDto createNewIncome(Incomes incomesDetails,Long userId) {
        Optional<User> user =userRepository.findById(userId);
        IncomeDto response =new IncomeDto();
        if(user.isPresent()){
            User existingUser =user.get();
            incomesDetails.setUser(existingUser);
            repository.save(incomesDetails);
            response.setMessage("new income source for "+existingUser.getUsername()+" created");
            response.setStatusCode(200);
            response.setIncome(incomesDetails);
        }else{
            response.setErrorMessage("user with id "+userId+"not found");
            response.setStatusCode(404);
        }

        return response;
    }

    @Override
    public IncomeDto viewAllIncomes(Long userId) {
        IncomeDto response =new IncomeDto();
        Optional<User> user =userRepository.findById(userId);
        if(user.isEmpty()){
            response.setErrorMessage("User with "+userId+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        var incomes =repository.findAll().
                stream()
                .filter(incomes1 -> incomes1.getUser().equals(user.get()))
                        .toList();
        response.setIncomes(incomes.isEmpty() ?new ArrayList<>():incomes);
        response.setMessage(
                incomes.isEmpty()?
                        "user "+user.get().getUsername()+" does not have any active income generating activities"
                        :"incomes by "+user.get().getUsername()
                );
        response.setStatusCode(incomes.isEmpty()?204:200);
        return response;
    }

    @Override
    public IncomeDto deleteIncomeById(Long userId, Long incomeId) {
        Optional<User> user =userRepository.findById(userId);
        Optional<Incomes> incomes =repository.findById(incomeId);
        IncomeDto response =new IncomeDto();
        if(user.isEmpty() || incomes.isEmpty()){
            var answ =user.isEmpty()?"user":"income";
            response.setErrorMessage(answ+" does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(user.get().getUsername().equalsIgnoreCase(incomes.get().getUser().getUsername())){
            repository.deleteById(incomeId);
            response.setMessage("deleted "+user.get().getUsername()+"'s income "+incomes.get().getName());
            response.setStatusCode(204);
            return response;
        }
        response.setMessage("income not deleted because its not yours! ");
        response.setStatusCode(200);

        return response;
    }

    @Override
    public IncomeDto deleteAllIncomesForUser(Long userId) {
        Optional<User> user =userRepository.findById(userId);
        List<Incomes> incomes =repository.findAll().stream()
                .filter(incomes1 -> incomes1.getUser().getId().equals(userId))
                .toList();
        IncomeDto response =new IncomeDto();
        if(user.isEmpty()){
            response.setErrorMessage("user does not exist");
            response.setStatusCode(404);
            return response;
        }
        if(incomes.isEmpty()){
            response.setErrorMessage("user does not have incomes ");
            response.setStatusCode(404);
            return response;
        }
        repository.deleteAll();
        response.setMessage("all incomes for "+user.get().getUsername() +" deleted");
        response.setStatusCode(204);
        return response;
    }

    @Override
    public IncomeDto getIncomeByName(Long userId, String name) {
        IncomeDto response =new IncomeDto();
        Optional<User> user =userRepository.findById(userId);
        if(user.isPresent()){
            User existingUser =user.get();
            var income =repository.findAll().stream()
                    .filter(incomes -> incomes.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
            if(!income.isEmpty()){
                response.setIncomes(income);
                response.setMessage("incomes with the name "+name+" belonging to "+existingUser.getUsername());
                response.setStatusCode(200);
            }else{
                response.setMessage(existingUser.getUsername()+" does not have incomes with the name "+name);
                response.setStatusCode(204);
            }
        }else{
            response.setMessage(" user with id "+userId+" does not exist");
            response.setStatusCode(404);
        }
        return response;
    }

    @Override
    public IncomeDto updateIncome(Long userId, Long incomeId) {
        IncomeDto response =new IncomeDto();
        Optional<User> user =userRepository.findById(userId);
        Optional<Incomes> income =repository.findById(incomeId);
        if( user.isEmpty() || income.isEmpty()){
            response.setMessage("user or income does not exist");
            response.setStatusCode(200);
            return response;
        }
        if(user.get().getId().equals(income.get().getUser().getId())){

        }
        return response;
    }
}
