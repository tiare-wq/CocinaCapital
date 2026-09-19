package org.example

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
    val nombre: String,
    val descr: String,
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
    nombre: String,
    descr: String,
    precio: Double,
    val tipoEntrada: TipoEntrada
): Producto(nombre, descr, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo entrada: " + tipoEntrada)
    }
}

open class PlatoPrincipal(
    nombre: String,
    descr: String,
    precio: Double,
    val tipoPlatoPrincipal: TipoPlatoPrincipal
): Producto(nombre, descr, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo plato principal: " + tipoPlatoPrincipal)
    }
}

open class Guarnicion (
    nombre: String,
    descr: String,
    precio: Double,
    val tipoGuarnicion: TipoGuarnicion
): Producto(nombre, descr, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo guarnición: " + tipoGuarnicion)
    }
}

open class Postre (
    nombre: String,
    descr: String,
    precio: Double,
    val tipoPostre: TipoPostre
): Producto(nombre, descr, precio) {

    override fun mostrarProducto() {
        super.mostrarProducto()
        println("Tipo postre: " + tipoPostre)
    }
}

open class Bebida (
    nombre: String,
    descr: String,
    precio: Double,
    val tipoBebida: TipoBebida
): Producto(nombre, descr, precio) {

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