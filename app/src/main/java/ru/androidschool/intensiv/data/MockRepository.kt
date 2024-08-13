package ru.androidschool.intensiv.data

object MockRepository {

    fun getMovies(): List<MovieTemp> {

        val moviesList = mutableListOf<MovieTemp>()
        for (x in 0..10) {
            val movie = MovieTemp(
                title = "Spider-Man $x",
                voteAverage = 10.0 - x
            )
            moviesList.add(movie)
        }

        return moviesList
    }
}
