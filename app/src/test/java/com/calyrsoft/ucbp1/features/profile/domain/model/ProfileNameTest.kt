package com.calyrsoft.ucbp1.features.profile.domain.model.valueObjects

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileName
import org.junit.Test
import org.junit.Assert.*

class ProfileNameTest {

    @Test
    fun `given valid name when creating ProfileName then should succeed`() {
        val validName = "Jhonn Ramirez"

        val name = ProfileName(validName)

        assertEquals(validName, name.value)
        assertEquals(validName, name.toString())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given blank name when creating ProfileName then should throw exception`() {
        ProfileName("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given short name when creating ProfileName then should throw exception`() {
        ProfileName("Jo") // menos de 3 caracteres
    }


}
