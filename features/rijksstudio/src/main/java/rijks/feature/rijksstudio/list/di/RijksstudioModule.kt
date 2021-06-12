package rijks.feature.rijksstudio.list.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import rijks.feature.rijksstudio.list.data.RijksstudioRepositoryImp
import rijks.feature.rijksstudio.list.domain.RijksstudioRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class RijksstudioModule {

    @Binds
    abstract fun bindRijksstudioRepository(
        rijksstudioRepositoryImp: RijksstudioRepositoryImp
    ): RijksstudioRepository

}