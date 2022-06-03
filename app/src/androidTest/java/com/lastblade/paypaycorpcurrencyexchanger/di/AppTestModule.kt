package com.lastblade.paypaycorpcurrencyexchanger.di

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class AppTestModule : AppModule() {
    override var baseUrl = "http://127.0.0.1:8080"
}