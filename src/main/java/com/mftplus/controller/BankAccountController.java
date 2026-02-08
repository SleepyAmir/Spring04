package com.mftplus.controller;


import com.mftplus.dto.BankAccountDto;
import com.mftplus.exception.ResourceNotFoundException;
import com.mftplus.model.enums.AccountType;
import com.mftplus.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;


    private void preparedListModel(Model model, int page , int size,String name ,String family , String accountNumber , BigDecimal balance, AccountType type) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("balance").descending());
        Page<BankAccountDto> bankAccountPage;

        if (name != null && accountNumber != null) {
            bankAccountPage=bankAccountService.findByNameAndAccountNumber(name,accountNumber,pageable);
        }else{
            bankAccountPage=bankAccountService.findAll(pageable);
        }
        model.addAttribute("bankAccountPage",bankAccountPage);
    }

    @GetMapping
    public String getAllBankAccounts(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String family,
            @RequestParam(required = false) BigDecimal balance,
            @RequestParam(required = false) AccountType type,
            Model model){

        preparedListModel(model,page,size,name,family,accountNumber,balance,type);

        if (!model.containsAttribute("bankAccountPage")){
            model.addAttribute("bankAccountPage",new BankAccountDto());
        }
        return "bankAccount/list";

    }

    @PostMapping("/trash")
    public String getTrash(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            Model model
    ){
        Pageable  pageable = PageRequest.of(page,size, Sort.by("balance").descending());
        Page<BankAccountDto> deletedPage =  bankAccountService.findAllDeleted(pageable);

        model.addAttribute("bankAccounts",deletedPage);
        model.addAttribute("isTrash",true);
        return "bankAccount/list";
    }


    @PostMapping
    public String createBankAccount(
            @Valid @ModelAttribute("bankAccount") BankAccountDto bankAccountDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size
    ){

        if (result.hasErrors()){
            preparedListModel(model,page,size,null,null,null,null,null);
            model.addAttribute("showModel",true);
            model.addAttribute("formAction","bankAccount");
            return   "bankAccount/list";
        }
        bankAccountService.save(bankAccountDto);
        redirectAttributes.addFlashAttribute("successMessage","Account Created successfully");
        return "redirect:/bankAccount";


    }

    @PutMapping("/{id}")
    public String updateBankAccount(
            @PathVariable Long id,
            @Valid @ModelAttribute("bankAccount") BankAccountDto bankAccountDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(defaultValue = "0")int page
    ){
        if (result.hasErrors()){
            preparedListModel(model,page,10,null,null,null,null,null);
            model.addAttribute("showModel",true);
            model.addAttribute("formAction","/bankAccount"+id);
            bankAccountDto.setId(id);
            return"bankAccount/list";
        }

        if (bankAccountService.findById(id)==null){
            throw new ResourceNotFoundException("Bank Account not found");
        }

        bankAccountDto.setId(id);
        bankAccountService.update(bankAccountDto);
        redirectAttributes.addFlashAttribute("successMessage","Account Updated successfully");
        return "redirect:/bankAccount";
    }



    @PostMapping("/restore/{id}")
    public String restoreBankAccount(@PathVariable Long id, RedirectAttributes redirectAttributes){
        bankAccountService.restoreById(id);
        redirectAttributes.addFlashAttribute("successMessage","Account Restored successfully");
        return "redirect:/bankAccount/trash";
    }

}

