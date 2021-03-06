package com.lastblade.paypaycorpcurrencyexchanger.di

import com.lastblade.paypaycorpcurrencyexchanger.data.db.RoomHelper
import com.lastblade.paypaycorpcurrencyexchanger.data.network.ApiHelper
import com.lastblade.paypaycorpcurrencyexchanger.repo.home.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeRepoModule {
    @Singleton
    @Provides
    fun provideHomeLocalRepo(
        @AppModule.LocalRoomHelper roomHelper: RoomHelper,
        @ApplicationScope coroutineScope: CoroutineScope,
    ): HomeLocalDbRepo {
        val currenciesDao = roomHelper.getDatabase().currenciesDao()
        val currencyRateDao = roomHelper.getDatabase().currencyRateDao()
        return HomeLocalDbRepoImpl(currenciesDao, currencyRateDao, coroutineScope)
    }

    @Singleton
    @Provides
    fun provideHomeRemoteRepo(@AppModule.RemoteApiHelper apiHelper: ApiHelper): HomeRemoteRepo {
        return HomeRemoteRepoImpl(apiHelper)
    }

    @Singleton
    @Provides
    fun provideHomeRepo(
        homeLocalRepo: HomeLocalDbRepo,
        homeNetworkRepo: HomeRemoteRepo,
    ): HomeRepo {
        return HomeRepoImpl(homeLocalRepo, homeNetworkRepo)
    }

}