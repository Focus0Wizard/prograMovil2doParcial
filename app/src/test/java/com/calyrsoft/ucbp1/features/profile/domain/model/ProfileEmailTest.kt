package com.calyrsoft.ucbp1.features.profile.domain.model.valueObjects

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileEmail
import org.junit.Test
import org.junit.Assert.*

class ProfileEmailTest {

    @Test
    fun `given valid email when creating ProfileEmail then should succeed`() {
        val validEmail = "jhonn.ramirez@ucb.edu.bo"

        val email = ProfileEmail(validEmail)

        assertEquals(validEmail, email.value)
        assertEquals(validEmail, email.toString())
    }

    @Test
    fun `given valid gmail when creating ProfileEmail then should succeed`() {
        val validEmail = "test@gmail.com"

        val email = ProfileEmail(validEmail)

        assertEquals(validEmail, email.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given blank email when creating ProfileEmail then should throw exception`() {
        ProfileEmail("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given invalid email when creating ProfileEmail then should throw exception`() {
        ProfileEmail("correo_invalido")
    }
}
