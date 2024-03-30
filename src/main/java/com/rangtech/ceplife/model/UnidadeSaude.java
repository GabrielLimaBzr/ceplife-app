package com.rangtech.ceplife.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_UNIDADE_SAUDE")
@Getter
@Setter
public class UnidadeSaude implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INT_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "STR_CNES", nullable = false, unique = true, length = 120)
	private String cnes;
	
	@Column(name = "STR_ESTABELECIMENTO", length = 120)
	private String estabelecimento;
	
	@Column(name = "INT_FAIXA_CEP_INI", nullable = false, length = 30)
	private Integer faixaCepIni;
	
	@Column(name = "INT_FAIXA_CEP_FIM", nullable = false, length = 30)
	private Integer faixaCepFim;
}
