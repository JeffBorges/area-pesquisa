package br.com.iftm.areapesquisaservice.controller

import br.com.iftm.areapesquisaservice.entity.AreaPesquisa
import br.com.iftm.areapesquisaservice.entity.Unidade
import br.com.iftm.areapesquisaservice.repository.AreaPesquisaRepository
import br.com.iftm.areapesquisaservice.repository.ProfessorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/area-pesquisa"])
class AreaPesquisaController : GenericController<AreaPesquisa, Int>() {

    @Autowired
    lateinit var repository: AreaPesquisaRepository

    @Autowired
    lateinit var professorRepository: ProfessorRepository

    override fun getRepository(): JpaRepository<AreaPesquisa, Int> = repository

    override fun find(search: String?, pageable: PageRequest): Page<AreaPesquisa> =
            repository.findByNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(search, search, pageable)

    override fun validate(id: Int) = professorRepository.countByAreasPesquisa(repository.getOne(id)).compareTo(0) < 1
}
