package br.com.iftm.areapesquisaservice.repository

import br.com.iftm.areapesquisaservice.entity.Unidade
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UnidadeRepository : JpaRepository<Unidade, Int> {
    fun findByNomeIgnoreCaseContainingOrCidadeIgnoreCaseContaining(nome: String?, cidade: String?, page: Pageable): Page<Unidade>
}
