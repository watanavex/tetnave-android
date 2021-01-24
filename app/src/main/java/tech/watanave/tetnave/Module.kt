package tech.watanave.tetnave

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import tech.watanave.tetnave.domain.*
import tech.watanave.tetnave.domain.value.Size
import tech.watanave.tetnave.repository.GameRepositoryImpl
import tech.watanave.tetnave.service.*

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
    fun bindGameUseCase(impl: GameUseCaseImpl) : GameUseCase

    @Binds
    fun bindGameRepository(impl: GameRepositoryImpl) : GameRepository

}

@Module
@InstallIn(ActivityRetainedComponent::class)
object GameModule {

    @Provides
    fun provideSize() : Size = Size(10, 20)

}
