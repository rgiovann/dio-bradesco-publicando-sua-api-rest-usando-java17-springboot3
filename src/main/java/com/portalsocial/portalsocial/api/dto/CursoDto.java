package com.portalsocial.portalsocial.api.dto;

import java.util.HashSet;
import java.util.Set;

import com.portalsocial.portalsocial.api.input.CursoNomeInput;
import com.portalsocial.portalsocial.domain.model.Aluno;
import com.portalsocial.portalsocial.domain.model.Curso;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CursoDto extends CursoCompactDto {

    private String fornecedor;

    private Long visualizacoes;

    private Long gostei;

    private Long qtdadeHoras;

    private Set<AlunoCompactDto> alunos = new HashSet<AlunoCompactDto>();

    public CursoDto(Curso curso) {

        super(curso);

        this.setFornecedor(curso.getFornecedor());
        this.setVisualizacoes(curso.getVisualizacoes());
        this.setGostei(curso.getGostei());
        this.setQtdadeHoras(curso.getQtdadeHoras());

        for (Aluno aluno : curso.getAlunos()) {
            alunos.add(new AlunoCompactDto(aluno));
        }

    }

}
