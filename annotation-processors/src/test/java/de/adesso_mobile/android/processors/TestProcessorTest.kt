package de.adesso_mobile.android.processors

import com.tschuchort.compiletesting.KotlinCompilation
import org.amshove.kluent.shouldEqual
import org.junit.Assert.*
import org.junit.Test

class TestProcessorTest{
    @Test
    fun `Prüfe, dass der Prozessor kompiliert`() {
        //WENN: die Klasse gebaut wird
        val result = KotlinCompilation().apply {
            annotationProcessors = listOf(TestProcessor())
        }.compile()

        //DANN: kompiliert der generierte Code
        result.exitCode shouldEqual KotlinCompilation.ExitCode.OK
    }
}