package com.calyrsoft.ucbp1.features.profile.domain.model.valueObjects

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileTelefono
import org.junit.Test
import org.junit.Assert.*

class ProfileTelefonoTest {

    @Test
    fun `given valid phone when creating ProfileTelefono then should succeed`() {
        val validPhone = 71234567L

        val telefono = ProfileTelefono(validPhone)

        assertEquals(validPhone, telefono.value)
        assertEquals(validPhone.toString(), telefono.toString())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given short phone when creating ProfileTelefono then should throw exception`() {
        ProfileTelefono(123L)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given too long phone when creating ProfileTelefono then should throw exception`() {
        ProfileTelefono(1234567890123L)
    }
}
