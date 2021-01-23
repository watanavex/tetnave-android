package tech.watanave.tetnave.game

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface Module {

    @Binds
    fun bindGameSpecification(impl: GameSpecificationImpl) : GameSpecification

    @Binds
    fun bindGameTicker(impl: GameTickerImpl) : GameTicker

    @Binds
    fun bindGameService(impl: GameServiceImpl) : GameService

    @Binds
    fun bindGame(impl: GameImpl) : Game

}

@Module
@InstallIn(ActivityRetainedComponent::class)
object GameModule {

    @Provides
    fun provideSize() : Size = Size(10, 20)

}
