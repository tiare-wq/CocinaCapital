package com.cocinacapital.usuarios.entity;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter

@Entity
@Table(name = "tarjeta")
public class Tarjeta {

    @Id
    private Long id;

    @Column(name = "nro_tarjeta", nullable = false, unique = true)
    private Long nroTarjeta;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    private Boolean prioritaria;
}
