package com.rangtech.ceplife.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.rangtech.ceplife.config.Transacional;
import com.rangtech.ceplife.exception.ConflitoFaixasCepException;
import com.rangtech.ceplife.model.UnidadeSaude;
import com.rangtech.ceplife.repository.UnidadeSaudeRepository;

public class UnidadeSaudeService implements Serializable {

    private static final long serialVersionUID = 1L;
	
    @Inject
	private UnidadeSaudeRepository repository;
	
    @Transacional
	public void salvar (UnidadeSaude unidadeSaude) {
    	if (!repository.verificarConflitoFaixasCep(unidadeSaude)) {
            repository.salvar(unidadeSaude);
        } else {
            throw new ConflitoFaixasCepException("A faixa de CEP conflita com outra unidade de saúde");
        }
	}
	
	
    @Transacional
	public UnidadeSaude buscarUnidadeCep(String cep) {
		return repository.buscarUnidadeCep(cep);
	}

}
