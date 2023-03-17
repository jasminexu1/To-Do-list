import com.sun.tools.javac.Main
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

internal class SimpleTextFieldTest {

    @Test
    fun getDb() {
        assertEquals(4, 2+2)
    }

    @Test
    fun getGradient() {
        assertEquals(4, 2+2)
    }

    @Test
    @DisplayName("see if text field works")
    fun simpleTextField() {
        assertEquals(4, 2+2)
    }

    @Test
    @DisplayName("see if task field shows up")
    fun taskField() {
        assertEquals(4, 2+2)
    }
}