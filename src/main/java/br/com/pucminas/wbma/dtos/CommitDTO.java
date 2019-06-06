package br.com.pucminas.wbma.dtos;

import org.eclipse.jgit.lib.PersonIdent;

import br.com.pucminas.wbma.entity.Commit;
import lombok.Value;

@Value
public class CommitDTO {
	
	private Commit commit;
	private PersonIdent author;
}
