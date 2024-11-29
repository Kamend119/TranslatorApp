import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    // Подключение плагинов через Version Catalog
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

// Создаём объект libs для доступа к Version Catalog
val libs = the<LibrariesForLibs>()
