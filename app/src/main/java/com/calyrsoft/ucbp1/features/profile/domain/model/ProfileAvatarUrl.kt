package com.calyrsoft.ucbp1.features.profile.domain.model

@JvmInline
value class ProfileAvatarUrl(val value: String?) {
    init {
        value?.let {
            require(it.startsWith("http")) { "La URL del avatar debe ser válida" }
        }
    }
}