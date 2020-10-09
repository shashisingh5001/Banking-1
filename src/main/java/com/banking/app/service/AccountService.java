package com.banking.app.service;


import com.banking.app.shared.dto.AccountDto;

public interface AccountService {

	AccountDto createUser(AccountDto user);
	AccountDto getUserByAccountNumber(String accountNumber);
	AccountDto depositToAccount(AccountDto dto);
	AccountDto withAmount(AccountDto dto);
	AccountDto transferAmount(AccountDto dto);
	
}
