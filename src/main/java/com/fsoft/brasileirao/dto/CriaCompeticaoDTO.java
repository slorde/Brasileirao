package com.fsoft.brasileirao.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CriaCompeticaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer ano;
	private List<String> times;
}
