package de.adesso_mobile.android.processors

import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.element.TypeElement

@AutoService(Processor::class) // Registriert den Service
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
@SupportedOptions("kapt.kotlin.generated")
class TestProcessor : AbstractProcessor() {
    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        return true
    }
}