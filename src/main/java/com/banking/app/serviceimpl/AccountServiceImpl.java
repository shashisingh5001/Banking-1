package com.banking.app.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.app.io.entity.AccountEntity;
import com.banking.app.respository.AccountRepository;
import com.banking.app.service.AccountService;
import com.banking.app.shared.dto.AccountDto;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	
	
	@Override
	public AccountDto createUser(AccountDto user) {
			
		if(accountRepository.findByaccountNumber(user.getAccountNumber())!=null) throw new RuntimeException ("User Already Exists");
		
		AccountEntity accountEntity= new AccountEntity();
		BeanUtils.copyProperties(user, accountEntity);
		   
		AccountEntity storeDetails=accountRepository.save(accountEntity);
		
		AccountDto dto= new AccountDto();
		BeanUtils.copyProperties(storeDetails, dto);
		
		return dto;
	}
  

	@Override
	public AccountDto getUserByAccountNumber(String accountNumber) {
		AccountDto accountDto = new AccountDto();
		
		AccountEntity accountEntity= accountRepository.findByaccountNumber(accountNumber);
		BeanUtils.copyProperties(accountEntity, accountDto);
		return accountDto;
	}


	@Override
	public AccountDto depositToAccount(AccountDto dto) {
	  
		AccountDto Dto= new AccountDto();
		
		if(accountRepository.findByaccountNumber(dto.getAccountNumber())==null)throw new RuntimeException("User Not Found");
		 
		AccountEntity accountEntity=accountRepository.findByaccountNumber(dto.getAccountNumber());
		
		Long amount=Long.parseLong(dto.getAmount());
		
	    Long currentAmount=Long.parseLong(accountEntity.getAmount());
		
		String newAccount=String.valueOf(amount+currentAmount);
		
		accountEntity.setAmount(newAccount);
		
		AccountEntity entity=accountRepository.save(accountEntity);
		
		BeanUtils.copyProperties(entity, Dto);
		return Dto;
	}


	@Override
	public AccountDto withAmount(AccountDto dto) {
	    AccountDto Dto= new AccountDto();
		if(accountRepository.findByaccountNumber(dto.getAccountNumber())==null)throw new RuntimeException("User Not Found");
		
		AccountEntity accountEntity=accountRepository.findByaccountNumber(dto.getAccountNumber());
		
		Long amount=Long.parseLong(dto.getAmount());
		Long currentAmount=Long.parseLong(accountEntity.getAmount());
		
		if(amount>currentAmount)throw new RuntimeException("Insufficent Amount");
		
		String newAccount=String.valueOf(currentAmount-amount);
		accountEntity.setAmount(newAccount);
		
		AccountEntity entity=accountRepository.save(accountEntity);
		BeanUtils.copyProperties(entity, Dto);
		
		return Dto;
	}


	@Override
	public AccountDto transferAmount(AccountDto dto) {
		
		AccountDto accountDto= new AccountDto();
		  
		Long aM=Long.parseLong(dto.getAmount());
		
		AccountEntity accountEntity=accountRepository.findByaccountNumber(dto.getFromAccountNumber());
	    AccountEntity account=accountRepository.findByaccountNumber(dto.getToAccountNumber());
		
	    Long amountFrom=Long.parseLong(accountEntity.getAmount());
	    Long amountTo=Long.parseLong(account.getAmount()); 
		  
	     if(amountFrom<aM)throw new RuntimeException("Insufficent Balance");
	     
	     String newFromAmount=String.valueOf(amountFrom-aM);
	     String newToAmount=String.valueOf(amountTo+aM);
	     
	     accountEntity.setAmount(newFromAmount);
	     account.setAmount(newToAmount);
	     
	     accountRepository.save(accountEntity);
	     AccountEntity eentity=accountRepository.save(account);
	     
	     BeanUtils.copyProperties(eentity, accountDto);
		return accountDto;
	}


  





}
