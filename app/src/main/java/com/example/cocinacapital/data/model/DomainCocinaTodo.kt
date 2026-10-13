package com.example.cocinacapital.data.model

enum class TipoEntrada { ENSALADA, SOPA, CREMA, SOPA_FRIA, PATE, APERTIVO_LIGERO }
enum class TipoPlatoPrincipal { CARNE, AVE, PESCADO_MARISCO, PASTA, ARROZ }
enum class TipoGuarnicion { ARROZ, PAPAS, VERDURAS }
enum class TipoPostre { DULCES, TARTA, HELADO, REPOSTERIA }
enum class TipoBebida { AGUA, JUGO, GASEOSA, COCTEL, CARTA_VINO }

open class Producto(
    val id: Int,
    val nombre: String,
    val descr: String,
    val restaurante: Restaurante?,
    val stock: Int,
    precioInicial: Double
) {
    var precio: Double = precioInicial
        set(value) {
            require(value > 0) { "El precio debe ser mayor a cero." }
            require(value < 100000) { "El precio es exageradamente alto." }
            field = value
        }

    val infoAdicional = mutableListOf<String>()

    init {
        require(nombre.isNotBlank()) { "El nombre es obligatorio." }
        require(descr.isNotBlank()) { "La descripción es obligatoria." }
        require(stock >= 0) { "El stock no puede ser negativo." }
        require(precioInicial > 0) { "El precio debe ser mayor a cero." }
        require(precioInicial < 100000) { "El precio es exageradamente alto." }
    }
}

class Entrada(
    id: Int, nombre: String, descr: String, restaurante: Restaurante?, stock: Int,
    precio: Double, val tipoEntrada: TipoEntrada
) : Producto(id, nombre, descr, restaurante, stock, precio)

class PlatoPrincipal(
    id: Int, nombre: String, descr: String, restaurante: Restaurante?, stock: Int,
    precio: Double, val tipoPlatoPrincipal: TipoPlatoPrincipal
) : Producto(id, nombre, descr, restaurante, stock, precio)

class Guarnicion(
    id: Int, nombre: String, descr: String, restaurante: Restaurante?, stock: Int,
    precio: Double, val tipoGuarnicion: TipoGuarnicion
) : Producto(id, nombre, descr, restaurante, stock, precio)

class Postre(
    id: Int, nombre: String, descr: String, restaurante: Restaurante?, stock: Int,
    precio: Double, val tipoPostre: TipoPostre
) : Producto(id, nombre, descr, restaurante, stock, precio)

class Bebida(
    id: Int, nombre: String, descr: String, restaurante: Restaurante?, stock: Int,
    precio: Double, val tipoBebida: TipoBebida
) : Producto(id, nombre, descr, restaurante, stock, precio)

class ExperienciaCulinaria(
    val nombre: String,
    val descr: String,
    precioInicial: Double
) {
    var precio: Double = precioInicial
        set(value) {
            require(value > 0) { "El precio debe ser mayor a cero." }
            field = value
        }

    init {
        require(nombre.isNotBlank()) { "El nombre es obligatorio." }
        require(descr.isNotBlank()) { "La descripción es obligatoria." }
        require(precioInicial > 0) { "El precio debe ser mayor a cero." }
    }
}

class Restaurante

class Cliente(
    var nombre: String
)
