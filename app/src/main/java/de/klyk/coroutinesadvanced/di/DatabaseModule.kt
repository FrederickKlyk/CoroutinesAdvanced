package de.klyk.coroutinesadvanced.di

import de.klyk.coroutinesadvanced.io.db.movies.MovieDao
import de.klyk.coroutinesadvanced.io.db.movies.MovieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<MovieDatabase> { MovieDatabase.getInstance(context = get()) }
    single<MovieDao> { getDb(db = get()).dataDao() }
}

fun getDb(db: MovieDatabase) = db