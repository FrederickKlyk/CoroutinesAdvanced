package de.klyk.coroutinesadvanced.io.db.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("DELETE from movie where title LIKE :query")
    suspend fun deleteByQuery(query: String)

    @Query("Select * from movie")
    fun getMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Movie>)

    @Query("SELECT * FROM movie where title LIKE :query ORDER BY id ASC")
    fun getDatabasePagingSource(query: String): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clearAllMovies()
}

@Dao
interface MovieRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<Movie.MovieRemoteKeys>)

    @Query("SELECT * FROM movie_remote_keys WHERE imdbID = :imdbID")
    suspend fun remoteKeysByMovieTitle(imdbID: String): Movie.MovieRemoteKeys?

    @Query("DELETE FROM movie_remote_keys")
    suspend fun clearRemoteKeys()
}