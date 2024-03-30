package com.rangtech.ceplife;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.rangtech.ceplife.model.UnidadeSaude;
import com.rangtech.ceplife.service.UnidadeSaudeService;

import lombok.Getter;
import lombok.Setter;

@SessionScoped
@Named
@ManagedBean
public class UnidadeSaudeBean implements  Serializable {

    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
	private UnidadeSaude unidadeSaude;
    
    @Getter
    @Setter
    private String cep;
    
    @Getter
    @Setter
    private String unidadeSaudeEncontrada;
	
	@Inject
	private UnidadeSaudeService unidadeSaudeService;

	
	@PostConstruct
	public void init() {
		unidadeSaude = new UnidadeSaude();
		cep = "";
		unidadeSaudeEncontrada = null;
		System.out.println("teste init");
	}

	public void prepararNovoCadastro() {
		unidadeSaude = new UnidadeSaude();
		try {
	        FacesContext.getCurrentInstance().getExternalContext().redirect("/ceplife/views/cadastrarUnidadeSaude.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepararConsulta() {
		cep = "";
		unidadeSaudeEncontrada = null;
		try {
	        FacesContext.getCurrentInstance().getExternalContext().redirect("/ceplife/views/consultaUnidadeSaude.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvar() {
		
		try {
			unidadeSaudeService.salvar(unidadeSaude);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unidade de Saúde foi Salva com Sucesso",
					"Unidade de Saúde cadastrada com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, message);
			unidadeSaude = new UnidadeSaude();
	    } catch (ConflitoFaixasCepException e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO SALVAR: " + e.getMessage() , e.getLocalizedMessage()));
	    }
		
	}

	public void pesquisarPorCep() {
		UnidadeSaude result = new UnidadeSaude();
		unidadeSaudeEncontrada = null;
		
		if(cep == null || cep == "") {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo cep deve ser informado!",
					"Campo cep deve ser informado!");
			FacesContext.getCurrentInstance().addMessage(null, message);	
			return;
		}
		
		result = unidadeSaudeService.buscarUnidadeCep(cep);
		
		if(result != null) {
			unidadeSaudeEncontrada = result.getEstabelecimento();
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nenhuma unidade de saúde encontrada para o CEP informado.",
					"Nenhuma unidade de saúde encontrada para o CEP informado.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

}
