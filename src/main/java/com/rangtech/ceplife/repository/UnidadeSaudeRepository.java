package com.rangtech.ceplife.repository;

import com.rangtech.ceplife.model.UnidadeSaude;

public interface UnidadeSaudeRepository {

	void salvar(UnidadeSaude unidadeSaude);	
	
	UnidadeSaude buscarUnidadeCep (String cep);
	
	boolean verificarConflitoFaixasCep(UnidadeSaude novaUnidadeSaude);
}
