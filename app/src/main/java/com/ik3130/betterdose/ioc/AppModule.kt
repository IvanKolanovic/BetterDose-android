package com.ik3130.betterdose.ioc

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ik3130.betterdose.data.interfaces.AuthRepository
import com.ik3130.betterdose.data.repos.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(
        auth = Firebase.auth
    )
}