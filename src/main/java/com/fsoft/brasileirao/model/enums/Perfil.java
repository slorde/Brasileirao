package com.fsoft.brasileirao.model.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"), CLIENTE(2, "ROLE_USUARIO");

	private Integer codigo;
	private String descricao;

	private Perfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer codigo) {
		for(Perfil tipoCliente: Perfil.values()) {
			if (tipoCliente.getCodigo().equals(codigo))
				return tipoCliente;
		}
		
		return null;
	}
}
