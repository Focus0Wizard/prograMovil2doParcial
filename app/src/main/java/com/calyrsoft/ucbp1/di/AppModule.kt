package com.calyrsoft.ucbp1.di

import NotificationViewModel
import com.calyrsoft.ucbp1.core.AuthManager
import com.calyrsoft.ucbp1.features.dollar.data.database.AppRoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.DollarRepositoryImpl
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarHistoryViewModel
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarViewModel
import com.calyrsoft.ucbp1.features.github.data.api.GithubService
import com.calyrsoft.ucbp1.features.github.data.datasource.GithubRemoteDataSource
import com.calyrsoft.ucbp1.features.github.data.repository.GithubRepository
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository
import com.calyrsoft.ucbp1.features.github.domain.usecase.FindByNickNameUseCase
import com.calyrsoft.ucbp1.features.github.presentation.GithubViewModel
import com.calyrsoft.ucbp1.features.login.data.LoginRepositoryImpl
import com.calyrsoft.ucbp1.features.login.domain.repository.LoginRepository
import com.calyrsoft.ucbp1.features.login.domain.usecase.LoginUseCase
import com.calyrsoft.ucbp1.features.login.presentation.LoginViewModel
import com.calyrsoft.ucbp1.features.movies.data.api.MoviesService
import com.calyrsoft.ucbp1.features.movies.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movies.data.datasource.ThemoviedbDataSource
import com.calyrsoft.ucbp1.features.movies.data.repository.MoviesRepository
import com.calyrsoft.ucbp1.features.movies.domain.repository.IMoviesRepository
import com.calyrsoft.ucbp1.features.movies.domain.usecase.FetchMoviesUseCase
import com.calyrsoft.ucbp1.features.movies.presentation.MoviesViewModel
import com.calyrsoft.ucbp1.features.notification.data.repository.NotificationRepository
import com.calyrsoft.ucbp1.features.notification.data.repository.NotificationRepositoryImpl
import com.calyrsoft.ucbp1.features.profile.data.ProfileRepositoryImpl
import com.calyrsoft.ucbp1.features.profile.domain.repository.ProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.usecase.GetProfileUseCase
import com.calyrsoft.ucbp1.features.profile.presentation.ProfileViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModule = module {
    single { AuthManager(get()) }

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit GitHub
    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // GithubService
    single<GithubService> {
        get<Retrofit>().create(GithubService::class.java)
    }

    // Retrofit TheMovieDB
    single(named("themoviedb")) {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // MoviesService
    single<MoviesService> {
        get<Retrofit>(named("themoviedb")).create(MoviesService::class.java)
    }

    // Repositories
    single<LoginRepository> { LoginRepositoryImpl() }
    single { com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource() }
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<IDollarRepository> { DollarRepositoryImpl(get(), get()) }
    single { GithubRemoteDataSource(get()) }
    single<IGithubRepository> { GithubRepository(get()) }
    single { AppRoomDatabase.getDatabase(get()) }
    single { get<AppRoomDatabase>().dollarDao() }
    single { DollarLocalDataSource(get()) }
    single { NotificationRepositoryImpl() as NotificationRepository }

    // UseCases
    factory { LoginUseCase(get()) }
    factory { FetchDollarUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { FindByNickNameUseCase(get()) }

    // ViewModels
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DollarViewModel(get(), get()) }
    viewModel { GithubViewModel(get(), get()) }
    viewModel { DollarHistoryViewModel(get()) }
    viewModel { NotificationViewModel(get()) }

    // Movies (Base de datos, repositorios y casos de uso)
    single { com.calyrsoft.ucbp1.features.movies.data.database.AppRoomDatabase.getDatabase(get()) }
    single { get<com.calyrsoft.ucbp1.features.movies.data.database.AppRoomDatabase>().movieDao() }
    single { ThemoviedbDataSource(get()) }
    single { MovieLocalDataSource(get()) }
    single<IMoviesRepository> { MoviesRepository(get(), get()) }
    factory { FetchMoviesUseCase(get()) }
    viewModel { MoviesViewModel(get()) }
}
