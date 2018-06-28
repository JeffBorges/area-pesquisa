package br.com.iftm.areapesquisaservice.repository

import br.com.iftm.areapesquisaservice.entity.AreaPesquisa
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AreaPesquisaRepository : JpaRepository<AreaPesquisa, Int> {
    fun findByNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(nome: String?, descricao: String?, page: Pageable): Page<AreaPesquisa>
}
