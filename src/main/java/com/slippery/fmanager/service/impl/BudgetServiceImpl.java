package com.slippery.fmanager.service.impl;

import com.slippery.fmanager.dto.BudgetDto;
import com.slippery.fmanager.models.BudgetMaking;
import com.slippery.fmanager.models.User;
import com.slippery.fmanager.repository.BudgetRepository;
import com.slippery.fmanager.repository.UserRepository;
import com.slippery.fmanager.service.BudgetService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository repository;
    private final UserRepository userRepository;

    public BudgetServiceImpl(BudgetRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;

    }

    @Override
    public BudgetDto createNewBudget(BudgetMaking budgetDetails, Long userId) {
        BudgetDto response =new BudgetDto();
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            response.setErrorMessage("User does not exist!");
            response.setStatusCode(204);
            return response;
        }
        BudgetMaking budget =new BudgetMaking();
        budget.setAmount(budgetDetails.getAmount());
        budget.setBudgetMonth(budgetDetails.getBudgetMonth());
        budget.setCategory(budgetDetails.getCategory());
        budget.setCreatedOn(LocalDateTime.now());
        budget.setName(budgetDetails.getName());
        budget.setTotalAmountToBudgetFor(budgetDetails.getTotalAmountToBudgetFor());
        budget.setUser(user.get());
        repository.save(budget);
        response.setMessage("new budget created");
        response.setStatusCode(200);
        return response;
    }

    @Override
    public BudgetDto updateBudget(BudgetMaking budgetDetails, Long userId, Long budgetId) {
        BudgetDto response =new BudgetDto();
        Optional<User> user =userRepository.findById(userId);
        Optional<BudgetMaking> budget =repository.findById(budgetId);
        if(user.isEmpty() || budget.isEmpty()){
            response.setErrorMessage("user or budget does not exist");
            response.setStatusCode(204);
            return response;
        }
        User existingUser =user.get();
        BudgetMaking existingBudget =budget.get();
        if(!Objects.equals(existingBudget.getUser().getId(), existingUser.getId())){
            response.setErrorMessage("budget does not belong to"+existingUser.getUsername());
            response.setStatusCode(204);
            return response;
        }

        budget.get().setAmount(budgetDetails.getAmount());
        budget.get().setBudgetMonth(budgetDetails.getBudgetMonth());
        budget.get().setCategory(budgetDetails.getCategory());
        budget.get().setCreatedOn(LocalDateTime.now());
        budget.get().setName(budgetDetails.getName());
        budget.get().setTotalAmountToBudgetFor(budgetDetails.getTotalAmountToBudgetFor());
        budget.get().setUser(user.get());
        repository.save(budget.get());
        response.setMessage("budget updated");
        response.setStatusCode(200);
        response.setBudget(budget.get());

        return response;
    }

    @Override
    public BudgetDto deleteBudgetById(Long userId, Long budgetId) {
        BudgetDto response =new BudgetDto();
        Optional<User> user =userRepository.findById(userId);
        Optional<BudgetMaking> budget =repository.findById(budgetId);
        if(user.isEmpty() || budget.isEmpty()){
            response.setErrorMessage("user or budget does not exist");
            response.setStatusCode(204);
        }
        if(!Objects.equals(budget.get().getUser().getId(), user.get().getId())){
            response.setErrorMessage("budget does not belong to"+user.get().getUsername());
            response.setStatusCode(204);
            return response;
        }
        repository.deleteById(budgetId);
        response.setMessage("budget deleted");
        response.setStatusCode(204);

        return response;
    }

    @Override
    public BudgetDto getBudgetById(Long userId, Long budgetId) {
        BudgetDto response =new BudgetDto();
        Optional<User> user =userRepository.findById(userId);
        Optional<BudgetMaking> budget =repository.findById(budgetId);
        if(user.isEmpty() || budget.isEmpty()){
            response.setErrorMessage("user or budget does not exist");
            response.setStatusCode(204);
        }
        if(!Objects.equals(budget.get().getUser().getId(), user.get().getId())){
            response.setErrorMessage("budget does not belong to"+user.get().getUsername());
            response.setStatusCode(204);
            return response;
        }
        response.setMessage("budget with id"+budgetId);
        response.setBudget(repository.findById(budgetId).get());
        response.setStatusCode(200);
        return response;
    }

    @Override
    public BudgetDto getAllBudget(Long userId) {
        BudgetDto response =new BudgetDto();
        Optional<User> user =userRepository.findById(userId);
        if(user.isEmpty() ){
            response.setErrorMessage("user does not exist");
            response.setStatusCode(204);
        }
        var budgets =repository.findAll().stream()
                .filter(budgetMaking -> budgetMaking.getUser().getId().equals(user.get().getId()))
                .toList();
        if(budgets.isEmpty()){
            response.setErrorMessage("user does not have any budgets");
            response.setStatusCode(204);
            return response;
        }
        response.setMessage("budgets for "+user.get().getUsername());
        response.setStatusCode(200);
        response.setBudgetList(budgets);

        return response;
    }

    @Override
    public BudgetDto findBudgetWithName(String name,Long userId) {
        BudgetDto response =new BudgetDto();
        Optional<User> user =userRepository.findById(userId);
        if(user.isEmpty() ){
            response.setErrorMessage("user does not exist");
            response.setStatusCode(204);
        }
        var budgets =repository.findAll().stream()
                .filter(budgetMaking -> budgetMaking.getUser().getUsername().equalsIgnoreCase(user.get().getUsername()))
                .toList();


        if(budgets.isEmpty()){
            response.setErrorMessage("no budget with name "+ name);
            response.setStatusCode(404);
            return response;
        }
        response.setMessage("budget with name "+ name);
        response.setBudgetList(budgets.stream().filter(budgetMaking -> budgetMaking.getName().toLowerCase().contains(name.toLowerCase())).toList());
        response.setStatusCode(200);

        return response;
    }

}
