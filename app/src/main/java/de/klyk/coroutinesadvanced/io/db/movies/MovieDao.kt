package de.klyk.coroutinesadvanced.io.db.movies

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("DELETE from movie where title LIKE :query")
    fun deleteByQuery(query: String)

    @Query("Select * from movie")
    fun getMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

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
    fun insertAll(remoteKey: List<Movie.MovieRemoteKeys>)

    @Query("SELECT * FROM movie_remote_keys WHERE movieTitle = :movieTitle")
    fun remoteKeysByMovieTitle(movieTitle: String): Movie.MovieRemoteKeys?

    @Query("DELETE FROM movie_remote_keys")
    fun clearRemoteKeys()
}