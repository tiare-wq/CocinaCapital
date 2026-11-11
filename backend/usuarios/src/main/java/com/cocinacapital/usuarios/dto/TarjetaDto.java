package com.cocinacapital.usuarios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaDto {

    @NotNull(message = "El número de tarjeta es obligatorio")
    @Positive(message = "El número de tarjeta debe ser positivo")
    @Size(
        min = 15,
        max = 19,
        message = "El número de la tarjeta debe tener entre 16 y 19 dígitos")
    private Long nroTarjeta;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Future(message = "La tarjeta debe tener una fecha de vencimiento futura")
    private LocalDate fechaVencimiento;

    @NotNull(message = "Debe indicar si la tarjeta es prioritaria")
    private Boolean prioritaria;
}
