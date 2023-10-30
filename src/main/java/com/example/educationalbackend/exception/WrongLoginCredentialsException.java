package com.example.educationalbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Wrong Credentials")
public class WrongLoginCredentialsException extends Exception {

}