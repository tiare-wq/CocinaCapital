package com.example.cocinacapital.data.repository

import com.example.cocinacapital.R
import com.example.cocinacapital.data.model.Promocion

class PromocionesRepository {

    val sucursalRepository = SucursalRepository()
    val productoRepository = ProductoRepository()

    private val productos = productoRepository.getProductos()

    // Productos que aplica la promoción
    private val promociones = listOf(

        // LA PICA DE JUAN
        Promocion(
            id = 1,
            local = requireNotNull(sucursalRepository.getById(1)),
            productos = mutableListOf(
                productos[0],
                productos[1]
            ),
            titulo = "Sabores Chilenos",
            descripcion = "Disfruta de una leche asada y papas rústicas con un descuento especial.",
            descuento = 20,
            distancia = 0.5,
            imagen = R.drawable.promo1,
            activa = true
        ),

        // PIZZA NAPOLI
        Promocion(
            id = 2,
            local = requireNotNull(sucursalRepository.getById(2)),
            productos = mutableListOf(
                productos[2],
                productos[3]
            ),
            titulo = "Momento Italiano",
            descripcion = "Acompaña tu experiencia italiana con un delicioso tiramisú y papas fritas.",
            descuento = 15,
            distancia = 0.8,
            imagen = R.drawable.promo2,
            activa = true
        ),

        // SUSHI HOUSE
        Promocion(
            id = 3,
            local = requireNotNull(sucursalRepository.getById(3)),
            productos = mutableListOf(
                productos[4],
                productos[5]
            ),
            titulo = "Experiencia Japonesa",
            descripcion = "Disfruta de mochis y papas tempura en una combinación especial.",
            descuento = 25,
            distancia = 1.2,
            imagen = R.drawable.promo3,
            activa = true
        ),

        // SABORES DEL PERÚ
        Promocion(
            id = 4,
            local = requireNotNull(sucursalRepository.getById(4)),
            productos = mutableListOf(
                productos[6],
                productos[7]
            ),
            titulo = "Tradición Peruana",
            descripcion = "Prueba el tradicional suspiro limeño junto a unas deliciosas papas a la huancaína.",
            descuento = 20,
            distancia = 1.5,
            imagen = R.drawable.promo4,
            activa = true
        ),

        // CAFÉ CENTRAL
        Promocion(
            id = 5,
            local = requireNotNull(sucursalRepository.getById(5)),
            productos = mutableListOf(
                productos[8],
                productos[9]
            ),
            titulo = "Dulce Tentación",
            descripcion = "Combina un cheesecake de frutilla con un brownie de chocolate.",
            descuento = 30,
            distancia = 0.7,imagen = R.drawable.promo5,
            activa = true
        ),

        // GREEN FOOD
        Promocion(
            id = 6,
            local = requireNotNull(sucursalRepository.getById(6)),
            productos = mutableListOf(
                productos[10],
                productos[11]
            ),
            titulo = "Green Experience",
            descripcion = "Disfruta de una alternativa fresca y natural con productos seleccionados.",
            descuento = 15,
            distancia = 1.0,
            imagen = R.drawable.promo6,
            activa = true
        )
    )

    fun getPromociones(): List<Promocion> = promociones
}
