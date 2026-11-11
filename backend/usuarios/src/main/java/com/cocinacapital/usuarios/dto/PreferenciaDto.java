package com.cocinacapital.usuarios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Preferencia {

    @NotNull(message = "El tipo de preferencia es obligatorio")
    @Enumerated(EnumType.STRING)
    private TipoPreferencia tipoPreferencia;

    @NotNull(message = "La lista de subtipos de preferencia no puede ser nula")
    @NotEmpty(message = "Debe seleccionar al menos una opción de preferencia")
    private List<SubtipoPreferencia> listaPreferencias;
}
