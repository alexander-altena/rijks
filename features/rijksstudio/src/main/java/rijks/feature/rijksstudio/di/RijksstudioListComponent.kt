package rijks.feature.rijksstudio.di

import android.app.Application
import com.example.rijks.di.RijksstudioModuleDependencies
import dagger.BindsInstance
import dagger.Component
import rijks.feature.rijksstudio.presentation.list.RijksstudioListFragment

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