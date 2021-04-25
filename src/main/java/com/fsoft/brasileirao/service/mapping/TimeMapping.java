package com.fsoft.brasileirao.service.mapping;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome_popular;
	private Integer time_id;
	private String escudo;
}
