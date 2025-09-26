package com.calyrsoft.ucbp1.features.profile.domain.model

@JvmInline
value class ProfileTelefono(val value: Long) {
    init {
        require(value.toString().length in 7..10) {
            "El teléfono debe tener entre 7 y 10 dígitos"
        }
    }
}