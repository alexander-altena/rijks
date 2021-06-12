package rijks.feature.rijksstudio.list.di

import android.app.Application
import android.content.Context
import com.example.rijks.di.RijksstudioModuleDependencies
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import rijks.feature.rijksstudio.list.data.RijksstudioRepositoryImp
import rijks.feature.rijksstudio.list.domain.RijksstudioRepository
import rijks.feature.rijksstudio.list.presentation.RijksstudioListFragment

@Component(
    dependencies = [RijksstudioModuleDependencies::class],
    modules = [RijksstudioModule::class]
)
interface RijksstudioListComponent {

    fun inject(artObjectListFragment: RijksstudioListFragment)

    @Component.Factory
    interface Factory{
        fun create(
            rijksstudioModuleDependencies: RijksstudioModuleDependencies,
            @BindsInstance application: Application
        ): RijksstudioListComponent
    }
}