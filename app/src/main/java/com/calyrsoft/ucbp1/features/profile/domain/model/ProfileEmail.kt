package com.calyrsoft.ucbp1.features.profile.domain.model

@JvmInline
value class ProfileEmail(val value: String) {
    init {
        require(value.isNotBlank()) { "El email no puede estar vacío" }
        require(android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            "El email no es válido"
        }
    }
}