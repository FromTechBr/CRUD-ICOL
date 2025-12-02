package com.br.org.icol.icolbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequisicaoNaoEncontrada extends RuntimeException{
    public RequisicaoNaoEncontrada(String msg){
        super(msg);
    }
}
