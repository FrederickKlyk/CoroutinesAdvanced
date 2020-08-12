package de.klyk.coroutinesadvanced.di

import de.klyk.coroutinesadvanced.io.db.movies.MovieDao
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import de.klyk.coroutinesadvanced.io.network.movies.MovieService
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesFlowRepository
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesFlowRepositoryImpl
import de.klyk.coroutinesadvanced.io.repository.movie.MovieRemoteMediator
import org.koin.dsl.module

val databaseModule = module {
    single<MovieDatabase> { MovieDatabase.getInstance(context = get()) }
    single<MovieDao> { getDb(db = get()).movieDao() }
    single { MovieRemoteMediator(database = get(), movieService = get()) }
    single<GetMoviesFlowRepository> { GetMoviesFlowRepositoryImpl(movieDatabase = get(), movieRemoteMediator = get()) }
}

fun getDb(db: MovieDatabase) = db