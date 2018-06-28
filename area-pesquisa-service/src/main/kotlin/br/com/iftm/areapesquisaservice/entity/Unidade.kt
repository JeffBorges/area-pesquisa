package br.com.iftm.areapesquisaservice.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "unidade")
class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @NotBlank
    @Size(max = 100)
    val nome: String? = null

    @NotBlank
    @Size(max = 100)
    val cidade: String? = null
}
