package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.databinding.MovieDetailsHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.applySchedulers
import ru.androidschool.intensiv.ui.loadUrl
import timber.log.Timber

class MovieDetailsFragment : BaseFragment<MovieDetailsFragmentBinding>() {
    private var _binding: MovieDetailsFragmentBinding? = null
    private var _posterBinding: MovieDetailsHeaderBinding? = null

    private val posterBinding get() = _posterBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MovieDetailsFragmentBinding {
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        _posterBinding = MovieDetailsHeaderBinding.bind(_binding!!.root)
        return _binding!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt(KEY_ID) ?: 0

        val getMovieDetails = MovieApiClient.apiClient.getMovieDetails(movieId, API_KEY, ENGLISH)
        val getMovieCredits = MovieApiClient.apiClient.getMovieCredits(movieId, API_KEY, ENGLISH)


        compositeDisposable.add(getMovieDetails.applySchedulers()
            .subscribe({ movieDetails ->
                binding.movieTitle.text = movieDetails.title
                binding.rating.rating = movieDetails.rating
                binding.movieOverview.text = movieDetails.overview
                posterBinding.posterImage.loadUrl(movieDetails.posterPath)
            }, { error ->
                Timber.e(error)
            }))


        compositeDisposable.add(getMovieCredits.applySchedulers()
            .subscribe({ response ->
                val actors = response.cast
                binding.itemsContainer.adapter = adapter.apply {
                    addAll(actors.map {
                        CastItem(it) {}
                    }.toList())
                }
            }, { error ->
                Timber.e(error)
            }))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _posterBinding = null
    }

    companion object {
        const val KEY_ID = "id"
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        const val ENGLISH = "en-US"
    }
}
