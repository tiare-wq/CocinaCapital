package com.example.cocinacapital.data.model

enum class TipoEntrada { ENSALADA, SOPA, CREMA, SOPA_FRIA, PATE, APERTIVO_LIGERO }
enum class TipoPlatoPrincipal { CARNE, AVE, PESCADO_MARISCO, PASTA, ARROZ }
enum class TipoGuarnicion { ARROZ, PAPAS, VERDURAS }
enum class TipoPostre { DULCES, TARTA, HELADO, REPOSTERIA }
enum class TipoBebida { AGUA, JUGO, GASEOSA, COCTEL, CARTA_VINO }

open class Producto(
    val id: Long,
    val nombre: String,
    val descr: String,
    val localID: Long,
    precioInicial: Double
) {
    var precio: Double = precioInicial
        set(value) {
            require(value > 0) { "El precio debe ser mayor a cero." }
            require(value < 100000) { "El precio es exageradamente alto." }
            field = value
        }

    init {
        require(nombre.isNotBlank()) { "El nombre es obligatorio." }
        require(descr.isNotBlank()) { "La descripción es obligatoria." }
        require(precioInicial > 0) { "El precio debe ser mayor a cero." }
        require(precioInicial < 100000) { "El precio es exageradamente alto." }
        precio = precioInicial
    }
}

class Entrada(
    id: Long, nombre: String, descr: String, localID: Long,
    precio: Double, val tipoEntrada: TipoEntrada
) : Producto(id, nombre, descr, localID,  precio)

class PlatoPrincipal(
    id: Long, nombre: String, descr: String, localID: Long,
    precio: Double, val tipoPlatoPrincipal: TipoPlatoPrincipal
) : Producto(id, nombre, descr, localID, precio)

class Guarnicion(
    id: Long, nombre: String, descr: String, localID: Long,
    precio: Double, val tipoGuarnicion: TipoGuarnicion
) : Producto(id, nombre, descr, localID, precio)

class Postre(
    id: Long, nombre: String, descr: String, localID: Long,
    precio: Double, val tipoPostre: TipoPostre
) : Producto(id, nombre, descr, localID, precio)

class Bebida(
    id: Long, nombre: String, descr: String, localID: Long,
    precio: Double, val tipoBebida: TipoBebida
) : Producto(id, nombre, descr, localID, precio)
