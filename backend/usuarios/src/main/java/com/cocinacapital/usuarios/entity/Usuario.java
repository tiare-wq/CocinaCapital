package com.cocinacapital.usuarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter

@Entity
@Table(
    name = "usuario",
    uniqueConstraint = {
        @UniqueConstraint(
            columnNames = {"usuario_id", "tipoPreferencia"}
        )
    }    
)
public class Usuario {

    @Id
    private Long usuarioID;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String password;

    private String urlFoto;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Preferencia> preferencias = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Tarjeta> tarjetas = new ArrayList<>();

    @Column(name = "ubicacion_id")
    private Long ubicacionID;
}
