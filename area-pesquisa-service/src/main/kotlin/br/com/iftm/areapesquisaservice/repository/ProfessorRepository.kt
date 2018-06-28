package br.com.iftm.areapesquisaservice.repository

import br.com.iftm.areapesquisaservice.entity.AreaPesquisa
import br.com.iftm.areapesquisaservice.entity.Professor
import br.com.iftm.areapesquisaservice.entity.Unidade
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProfessorRepository : JpaRepository<Professor, Int> {

    fun findByNomeIgnoreCaseContaining(nome: String?, page: Pageable): Page<Professor>

    fun countByUnidade(unidade: Unidade): Long

    fun countByAreasPesquisa(areaPesquisa: AreaPesquisa): Long
}
