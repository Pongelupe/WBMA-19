package br.com.pucminas.wbma.dtos;

import lombok.Value;

@Value
public class DiffDTO {
	
	private long filesAltered;
	private long insertions;
	private long deletions;	
	private long modifications;	

}
