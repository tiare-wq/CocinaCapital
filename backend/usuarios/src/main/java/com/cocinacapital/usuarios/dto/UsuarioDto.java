
package com.cocinacapital.usuarios.dto;

import com.cocinacapital.usuarios.entity.Preferencia;
import com.cocinacapital.usuarios.entity.Tarjeta;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    @NotBlank(message = "El email es obligatorio y no puede estar vacío")
    @Email(message = "Debe ingresar un email con formato válido")
    private String email;

    @NotBlank(message = "El nombre es obligatorio y no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password;

    @Size(max = 500, message = "La URL de la foto no puede superar los 500 caracteres")
    private String urlFoto;

    @NotNull(message = "La lista de preferencias no puede ser nula")
    @Size(max = 3, message = "No puede tener más de 3 tipos de preferencias")
    private List<Preferencia> preferencias = new ArrayList<>();

    @NotNull(message = "La lista de tarjetas no puede ser nula")
    private List<Tarjeta> tarjetas = new ArrayList<>();

    @Positive(message = "El ID de ubicación debe ser un número positivo")
    private Long ubicacionID;
}
