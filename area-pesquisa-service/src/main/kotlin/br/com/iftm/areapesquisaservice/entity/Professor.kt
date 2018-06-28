package br.com.iftm.areapesquisaservice.entity

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "professor")
class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @NotBlank
    @Size(max = 100)
    val nome: String? = null

    @NotNull
    @ManyToOne
    @JoinColumn(name = "unidade_id")
    val unidade: Unidade? = null

    @NotNull
    @Column(name = "grau_academico")
    @Enumerated(EnumType.STRING)
    val grauAcademico: GrauAcademico? = null

    @ManyToMany
    @JoinTable(name = "professor_area_pesquisa",
            joinColumns = arrayOf(JoinColumn(name = "professor_id", referencedColumnName = "id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "area_pesquisa_id", referencedColumnName = "id")))
    @Size(min = 1)
    val areasPesquisa: List<AreaPesquisa>? = null

}
