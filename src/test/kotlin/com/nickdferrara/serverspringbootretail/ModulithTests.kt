package com.nickdferrara.serverspringbootretail

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ModulithTests {

    private val modules = ApplicationModules.of(Application::class.java)

    @Test
    fun `should have valid module structure`() {
        // This will fail if there are any module boundary violations
        modules.verify()
    }


    @Test
    fun `should generate module documentation`() {
        // This generates PlantUML documentation of your module structure
        Documenter(modules)
            .writeDocumentation()
            .writeIndividualModulesAsPlantUml()
    }
}