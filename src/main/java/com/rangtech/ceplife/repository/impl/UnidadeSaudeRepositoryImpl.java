package com.rangtech.ceplife.repository.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rangtech.ceplife.model.UnidadeSaude;
import com.rangtech.ceplife.repository.UnidadeSaudeRepository;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnidadeSaudeRepositoryImpl implements UnidadeSaudeRepository{

	@Inject
	private EntityManager entityManager;
	
	public UnidadeSaudeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	
	@Override
	public void salvar(UnidadeSaude unidadeSaude) {
		entityManager.persist(unidadeSaude);
	}

	@Override
	public UnidadeSaude buscarUnidadeCep(String cep) {
	    int cepInt = Integer.parseInt(cep);
	    Query query = entityManager.createQuery(
	            "SELECT u FROM UnidadeSaude u WHERE :cep BETWEEN u.faixaCepIni AND u.faixaCepFim");
	    query.setParameter("cep", cepInt);
	    @SuppressWarnings("unchecked")
	    List<UnidadeSaude> unidades = query.getResultList();
	    if (unidades.isEmpty()) {
	        return null;
	    }
	    return unidades.get(0);
	}
	
	@Override
	public boolean verificarConflitoFaixasCep(UnidadeSaude novaUnidadeSaude) {
        String sql = "SELECT COUNT(*) FROM UnidadeSaude u " +
                     "WHERE (:faixaCepIni BETWEEN u.faixaCepIni AND u.faixaCepFim " +
                     "OR :faixaCepFim BETWEEN u.faixaCepIni AND u.faixaCepFim " +
                     "OR u.faixaCepIni BETWEEN :faixaCepIni AND :faixaCepFim " +
                     "OR u.faixaCepFim BETWEEN :faixaCepIni AND :faixaCepFim)";
        
        Query query = entityManager.createQuery(sql)
                                    .setParameter("faixaCepIni", novaUnidadeSaude.getFaixaCepIni())
                                    .setParameter("faixaCepFim", novaUnidadeSaude.getFaixaCepFim());
        
        Long count = (Long) query.getSingleResult();

        return count > 0;
    }

}
