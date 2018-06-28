package br.com.iftm.areapesquisaservice.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.util.*
import javax.validation.Valid

abstract class GenericController<T, K : Serializable> {

    @GetMapping("/{id:[0-9.,]*$}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable(value = "id") id: K) =
            Optional.ofNullable(getRepository().findById(id))
                    .map { r -> ResponseEntity(r, HttpStatus.OK) }
                    .orElse(ResponseEntity(HttpStatus.NOT_FOUND))

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun insert(@RequestBody @Valid entity: T) = getRepository().save(entity)

    @PutMapping("/{id:[0-9.,]*$}", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@PathVariable(value = "id") id: K, @RequestBody @Valid entity: T) =
            Optional.ofNullable(getRepository().findById(id))
                    .map { ResponseEntity(getRepository().save(entity), HttpStatus.OK) }
                    .orElse(ResponseEntity(HttpStatus.NOT_FOUND))

    @DeleteMapping("/{id:[0-9.,]*$}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    open fun delete(@PathVariable(value = "id") id: K): ResponseEntity<T> {
        if (validate(id)) {
            getRepository().deleteById(id)
            return ResponseEntity(HttpStatus.OK)
        } else return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun find(@RequestParam(value = "page", required = false) page: Int?,
             @RequestParam(value = "size", required = false) size: Int?,
             @RequestParam(value = "direction", required = false) direction: String?,
             @RequestParam(value = "field", required = false) field: String?,
             @RequestParam(value = "search", required = false) search: String?): ResponseEntity<Page<T>> {
        val pageable = createPageable(size, page, createSort(field, direction))
        return ResponseEntity(find("%$search%", pageable), HttpStatus.OK)
    }

    fun createPageable(size: Int?, page: Int?, sort: Sort?): PageRequest =
            if (size != null && size > 0 && page != null && page >= 0)
                if (sort != null) PageRequest.of(page, size, sort)
                else PageRequest.of(page, size)
            else PageRequest.of(0, Int.MAX_VALUE)

    fun createSort(field: String?, direction: String?): Sort? =
            if (!field.isNullOrBlank())
                Sort.Direction.fromOptionalString(direction.toString()).map { s -> Sort(s, field) }.orElse(null)
            else null


    abstract fun find(search: String?, pageable: PageRequest): Page<T>
    abstract fun getRepository(): JpaRepository<T, K>
    open fun validate(id: K): Boolean = true
}
