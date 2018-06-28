package br.com.iftm.areapesquisaservice.controller

import br.com.iftm.areapesquisaservice.entity.Unidade
import br.com.iftm.areapesquisaservice.repository.ProfessorRepository
import br.com.iftm.areapesquisaservice.repository.UnidadeRepository
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
@RequestMapping(path = ["/api/unidade"])
class UnidadeController : GenericController<Unidade, Int>() {

    @Autowired
    lateinit var repository: UnidadeRepository

    @Autowired
    lateinit var professorRepository: ProfessorRepository

    override fun find(search: String?, pageable: PageRequest): Page<Unidade> =
            repository.findByNomeIgnoreCaseContainingOrCidadeIgnoreCaseContaining(search, search, pageable)

    override fun getRepository(): JpaRepository<Unidade, Int> = repository
    override fun validate(id: Int) = professorRepository.countByUnidade(repository.getOne(id)).compareTo(0) < 1

}
