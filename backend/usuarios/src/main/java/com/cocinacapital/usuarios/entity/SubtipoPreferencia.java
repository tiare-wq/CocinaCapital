package com.cocinacapital.usuarios.entity;

@AllArgsConstructor
@Getter
public enum SubtipoPreferencia {

    // CULINARIA
    CHILENA(
        TipoPreferencia.CULINARIA,
        "Comida Chilena"
    ),
    ITALIANA(
        TipoPreferencia.CULINARIA,
        "Comida Italiana"
    ),
    JAPONESA(
        TipoPreferencia.CULINARIA,
        "Comida Japonesa"
    ),
    PERUANA(
        TipoPreferencia.CULINARIA,
        "Comida Peruana"
    ),
    MEXICANA(
        TipoPreferencia.CULINARIA,
        "Comida Mexicana"
    ),
    COMIDA_RAPIDA(
        TipoPreferencia.CULINARIA,
        "Comida Rápida"
    ),
    CAFETERIA_PASTELERIA(
        TipoPreferencia.CULINARIA,
        "Cafetería y Pastelería"
    ),
    MARISCOS_PESCADOS(
        TipoPreferencia.CULINARIA,
        "Mariscos y Pescados"
    ),
    INTERNACIONAL(
        TipoPreferencia.CULINARIA,
        "Comida Internacional"
    ),
    VEGETARIANA(
        TipoPreferencia.CULINARIA,
        "Comida Vegetariana"
    );

    // MONETARIA
    MENOS_5000(
        TipoPreferencia.MONETARIA,
        "Menos de $5.000"
    ),
    BETWEEN_5000_10000(
        TipoPreferencia.MONETARIA,
        "$5.000 - $10.000"
    ),
    BETWEEN_10000_15000(
        TipoPreferencia.MONETARIA,
        "$10.000 - $15.000"
    ),
    BETWEEN_15000_25000(
        TipoPreferencia.MONETARIA,
        "$15.000 - $25.000"
    ),
    MAS_25000(
        TipoPreferencia.MONETARIA,
        "Más de $25.000"
    ),
    INDEFINIDO(
        TipoPreferencia.MONETARIA,
        "Sin límite definido"
    ),

    // EXPERIENCIA
    SALIDA_AMIGOS(
        TipoPreferencia.EXPERIENCIA,
        "Una salida con amigos"
    ),
    CITA(
        TipoPreferencia.EXPERIENCIA,
        "Una cita romántica"
    ),
    FAMILIAR(
        TipoPreferencia.EXPERIENCIA,
        "Una salida familiar"
    ),
    ALGO_RAPIDO(
        TipoPreferencia.EXPERIENCIA,
        "Comer algo rápido"
    ),
    CAFE(
        TipoPreferencia.EXPERIENCIA,
        "Tomar un café y conversar"
    ),
    OCASION_ESPECIAL(
        TipoPreferencia.EXPERIENCIA,
        "Celebrar una ocasión especial"
    ),
    DESCUBRIR(
        TipoPreferencia.EXPERIENCIA,
        "Descubrir un lugar nuevo"
    ),
    COMER_SOLO(
        TipoPreferencia.EXPERIENCIA,
        "Comer solo/sola tranquilamente"
    );

    private final TipoPreferencia tipoPreferencia;
    private final String descripcion;
}