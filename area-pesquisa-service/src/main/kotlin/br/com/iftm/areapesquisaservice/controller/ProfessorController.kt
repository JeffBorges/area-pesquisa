package br.com.iftm.areapesquisaservice.controller

import br.com.iftm.areapesquisaservice.entity.Professor
import br.com.iftm.areapesquisaservice.repository.AreaPesquisaRepository
import br.com.iftm.areapesquisaservice.repository.ProfessorRepository
import br.com.iftm.areapesquisaservice.repository.UnidadeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/professor"])
class ProfessorController : GenericController<Professor, Int>() {

    @Autowired
    lateinit var repository: ProfessorRepository

    @Autowired
    lateinit var unidadeRepository: UnidadeRepository

    @Autowired
    lateinit var areaPesquisaRepository: AreaPesquisaRepository


    override fun find(search: String?, pageable: PageRequest): Page<Professor> = repository.findByNomeIgnoreCaseContaining(search, pageable)

    override fun getRepository(): JpaRepository<Professor, Int> = repository

    @GetMapping("/unidade/{id:[0-9.,]*$}/count", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun countByUnidade(@PathVariable(value = "id") id: Int): ResponseEntity<Long> =
            unidadeRepository.findById(id).map { r -> ResponseEntity(repository.countByUnidade(r), HttpStatus.OK) }
                    .orElse(ResponseEntity(HttpStatus.NOT_FOUND))

    @GetMapping("/area-pesquisa/{id:[0-9.,]*$}/count", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun countByAreaPesquisa(@PathVariable(value = "id") id: Int): ResponseEntity<Long> =
            areaPesquisaRepository.findById(id).map { r -> ResponseEntity(repository.countByAreasPesquisa(r), HttpStatus.OK) }
                    .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
}
