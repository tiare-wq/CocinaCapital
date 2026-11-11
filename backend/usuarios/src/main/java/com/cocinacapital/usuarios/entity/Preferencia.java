package com.cocinacapital.usuarios.entity;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter

@Entity
@Table(name = "preferencia")
public class Preferencia {

    @Id
    private Long id;

    // me gustarí que solo los tipos de preferencia n se repítieran por usuario
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPreferencia tipoPreferencia;

    @Columnn(nullable = false)
    private List<SubtipoPreferencia> listaPreferencias;
}
