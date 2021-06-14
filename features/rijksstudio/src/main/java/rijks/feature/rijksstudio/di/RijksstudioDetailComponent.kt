package rijks.feature.rijksstudio.di

import android.app.Application
import com.example.rijks.di.RijksstudioModuleDependencies
import dagger.BindsInstance
import dagger.Component
import rijks.feature.rijksstudio.presentation.detail.RijksstudioDetailFragment

@Component(
    dependencies = [RijksstudioModuleDependencies::class],
    modules = [RijksstudioModule::class]
)
interface RijksstudioDetailComponent {

    fun inject(artObjectListFragment: RijksstudioDetailFragment)

    @Component.Factory
    interface Factory{
        fun create(
            rijksstudioModuleDependencies: RijksstudioModuleDependencies,
            @BindsInstance application: Application
        ): RijksstudioDetailComponent
    }
}