package de.klyk.coroutinesadvanced.di

import de.klyk.coroutinesadvanced.io.db.movies.MovieDao
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesFlowRepository
import de.klyk.coroutinesadvanced.io.repository.movie.GetMoviesRemoteFlowRepositoryImpl
import de.klyk.coroutinesadvanced.io.repository.movie.MovieRemoteMediator
import de.klyk.dummy.di.DummyDao
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {
    single<MovieDatabase> { MovieDatabase.getInstance(context = get()) }
    single<MovieDao> { getDb(db = get()).movieDao() }
    single<DummyDao> { getDb(db = get()).dummyDao() }
    single { MovieRemoteMediator(database = get(), movieService = get()) }
    single<GetMoviesFlowRepository>(named("remote")) { GetMoviesRemoteFlowRepositoryImpl(movieDatabase = get(), movieRemoteMediator = get()) }
}

fun getDb(db: MovieDatabase) = db