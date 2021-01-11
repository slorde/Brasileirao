package com.fsoft.brasileirao.service.mapping;

import java.io.Serializable;

import lombok.Data;

@Data
public class TimeMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome_popular;
	private Integer time_id;
	private String escudo;
}
