package rijks.feature.rijksstudio.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import rijks.feature.rijksstudio.data.RijksstudioRepositoryImp
import rijks.feature.rijksstudio.domain.RijksstudioRepository

@Module
@InstallIn(FragmentComponent::class)
abstract class RijksstudioModule {

    @Binds
    abstract fun bindRijksstudioRepository(
        rijksstudioRepositoryImp: RijksstudioRepositoryImp
    ): RijksstudioRepository

}