package com.banking.app.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.app.request.DepositDetails;
import com.banking.app.request.TransferDetails;
import com.banking.app.request.UserDetailsRequest;
import com.banking.app.response.UserRest;
import com.banking.app.service.AccountService;
import com.banking.app.shared.dto.AccountDto;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	AccountService accountService;
	
	
	@PostMapping(produces= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			consumes= {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	 public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequest userDetailsRequest)
	 {		
		UserRest userRest = new UserRest();
		
		AccountDto dto= new AccountDto();
		
		BeanUtils.copyProperties(userDetailsRequest, dto);
		
		AccountDto userDto = accountService.createUser(dto);
		BeanUtils.copyProperties(userDto, userRest);
		
		 return new ResponseEntity<UserRest>(userRest,HttpStatus.OK);
	 }

	@GetMapping(path="/{accountNumber}")
	public UserRest getUser(@PathVariable String accountNumber)
	{
		UserRest userRest = new UserRest();
		AccountDto accountDto=accountService.getUserByAccountNumber(accountNumber);
		BeanUtils.copyProperties(accountDto, userRest);
		return userRest;
	}
	
	@PutMapping(path="/deposit")
	public UserRest deposit(@RequestBody DepositDetails depositDetails)
	{   
		UserRest userRest = new UserRest();
		
		AccountDto dto = new AccountDto();
		BeanUtils.copyProperties(depositDetails, dto);
		
		AccountDto accountDto=accountService.depositToAccount(dto);
		BeanUtils.copyProperties(accountDto, userRest);
		
		return userRest;
		
	}
	
	@PutMapping(path="/withdraw")
	public UserRest withdraw(@RequestBody DepositDetails depositDetails) {
		
		UserRest userRest=new UserRest();
		
		AccountDto dto= new AccountDto();
		BeanUtils.copyProperties(depositDetails, dto);
		
		AccountDto accountDto=accountService.withAmount(dto);
		BeanUtils.copyProperties(accountDto, userRest);
		return userRest;
		
	}
	
	@PutMapping(path="/trans")
	public UserRest trannsferAmount(@RequestBody TransferDetails transferDetails) {
		
		UserRest userRest=new UserRest();
		
		AccountDto dto=new AccountDto();
       	BeanUtils.copyProperties(transferDetails, dto);	
       	
       	AccountDto accountDto=accountService.transferAmount(dto);
       	BeanUtils.copyProperties(accountDto, userRest);
		return userRest;
	}
	
	
}
