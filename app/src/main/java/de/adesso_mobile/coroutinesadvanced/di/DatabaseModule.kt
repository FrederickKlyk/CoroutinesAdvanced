package de.adesso_mobile.coroutinesadvanced.di

import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDao
import de.adesso_mobile.coroutinesadvanced.io.db.movies.MovieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<MovieDatabase> { MovieDatabase.getInstance(context = get()) }
    single<MovieDao> { getDb(db = get()).dataDao() }
}

fun getDb(db: MovieDatabase) = db