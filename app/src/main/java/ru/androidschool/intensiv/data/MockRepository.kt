package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovies(): List<Movie_temp> {

        val moviesList = mutableListOf<Movie_temp>()
        for (x in 0..10) {
            val movie = Movie_temp(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }
}
