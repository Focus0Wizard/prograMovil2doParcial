package com.calyrsoft.ucbp1.features.profile.domain.model.valueObjects

import com.calyrsoft.ucbp1.features.profile.domain.model.ProfileAvatarUrl
import org.junit.Test
import org.junit.Assert.*

class ProfileAvatarUrlTest {

    @Test
    fun `given valid https url when creating ProfileAvatarUrl then should succeed`() {
        val validUrl = "https://example.com/avatar.png"

        val avatar = ProfileAvatarUrl(validUrl)

        assertEquals(validUrl, avatar.value)
        assertEquals(validUrl, avatar.value)
    }

    @Test
    fun `given null url when creating ProfileAvatarUrl then should succeed`() {
        val avatar = ProfileAvatarUrl(null)

        assertNull(avatar.value)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `given invalid url when creating ProfileAvatarUrl then should throw exception`() {
        ProfileAvatarUrl("ftp://avatar.png")
    }
}
