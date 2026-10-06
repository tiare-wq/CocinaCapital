package org.example

// ======================================
// PRODUCTOS
// ======================================

enum class TipoEntrada {
    ENSALADA,
    SOPA,
    CREMA,
    SOPA_FRIA,
    PATE,
    APERTIVO_LIGERO
}

enum class TipoPlatoPrincipal {
    CARNE,
    AVE,
    PESCADO_MARISCO,
    PASTA,
    ARROZ
}

enum class TipoGuarnicion {
    ARROZ,
    PAPAS,
    VERDURAS
}

enum class TipoPostre {
    DULCES,
    TARTA,
    HELADO,
    REPOSTERIA
}

enum class TipoBebida {
    AGUA,
    JUGO,
    GASEOSA,
    COCTEL,
    CARTA_VINO
}

open class Producto(
    val id: Int,
    val nombre: String,
    val descr: String,
    val restaruante: Restaurante,
    val stock: Int,
    precioInicial: Double
) {
    var precio = 0.0
        set (value) {
            require(value > 0) { "El precio no puede ser negativo." }
            require(value < 100000) { "El precio es exageradamente alto." }
            field = value
        }

    val infoAdicional = mutableListOf<String>()

    init {
        require(nombre.isBlank()) { "El nombre es obligatorio." }
        require(descr.isBlank()) { "La descripción es obligatoria." }

        precio = precioInicial
    }

    open fun mostrarProducto() {
        println("----- DESCRIPCIÓN DEL PRODUCTO -----")
        println("Nombre: " + nombre)
        println("Descripción: " + descr)
        println("Precio: " + precio)
    }
}

open class Entrada(
    id: Int,
    nombre: String,
    descr: String,
    restaruante: Restaurante,
    stock: Int,
    precio: Double,
    val tipoEntrada: TipoEntrada
): Producto(id, nombre, descr, restaruante, stock, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo entrada: " + tipoEntrada)
    }
}

open class PlatoPrincipal(
    id: Int,
    nombre: String,
    descr: String,
    restaruante: Restaurante,
    stock: Int,
    precio: Double,
    val tipoPlatoPrincipal: TipoPlatoPrincipal
): Producto(id, nombre, descr, restaruante, stock, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo plato principal: " + tipoPlatoPrincipal)
    }
}

open class Guarnicion (
    id: Int,
    nombre: String,
    descr: String,
    restaruante: Restaurante,
    stock: Int,
    precio: Double,
    val tipoGuarnicion: TipoGuarnicion
): Producto(id, nombre, descr, restaruante, stock, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo guarnición: " + tipoGuarnicion)
    }
}

open class Postre (
    id: Int,
    nombre: String,
    descr: String,
    restaruante: Restaurante,
    stock: Int,
    precio: Double,
    val tipoPostre: TipoPostre
): Producto(id, nombre, descr, restaruante, stock, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo postre: " + tipoPostre)
    }
}

open class Bebida (
    id: Int,
    nombre: String,
    descr: String,
    restaruante: Restaurante,
    stock: Int,
    precio: Double,
    val tipoBebida: TipoBebida
): Producto(id, nombre, descr, restaruante, stock, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo bebida: " + tipoBebida)
    }
}

open class ExperienciaCulinaria(
    val nombre: String,
    val descr: String,
    precioInicial: Double
) {

    var precio = 0.0
        set(value) {
            require(precio > 0) { "El precio no puede ser negativo." }
            field = value
        }
    init {
        require(nombre.isBlank()) { "El nombre es obligatorio." }
        require(descr.isBlank()) { "La descripción es obligatoria." }

        precio = precioInicial
    }
}

class Restaurante()

// ======================================
// PERFILES
// ======================================

class Cliente(
    var nombre: String
)
