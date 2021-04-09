package com.fsoft.brasileirao.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String login;
	private String senha;
	private Integer donoId;
	private boolean admin;
}
