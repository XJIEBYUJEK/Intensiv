package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.CreditsResponse
import ru.androidschool.intensiv.data.MovieDetails
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.databinding.FragmentSearchBinding
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.databinding.MovieDetailsHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.loadUrl
import timber.log.Timber

class MovieDetailsFragment : BaseFragment<MovieDetailsFragmentBinding>() {
    private var _posterBinding: MovieDetailsHeaderBinding? = null

    private val posterBinding get() = _posterBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun createViewBinding(inflater: LayoutInflater,container: ViewGroup?): MovieDetailsFragmentBinding {
        _posterBinding = MovieDetailsHeaderBinding.bind(binding.root)
        return MovieDetailsFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt(KEY_ID) ?: 0

        val getMovieDetails = MovieApiClient.apiClient.getMovieDetails(movieId, API_KEY, ENGLISH)
        val getMovieCredits = MovieApiClient.apiClient.getMovieCredits(movieId, API_KEY, ENGLISH)

        getMovieDetails.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                val movieDetails = response.body()
                binding.movieTitle.text = movieDetails?.title
                binding.rating.rating = movieDetails?.rating ?: 0.0f
                binding.movieOverview.text = movieDetails?.overview
                posterBinding.posterImage.loadUrl(movieDetails?.posterPath)
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Timber.e(t.toString())
            }
        })

        getMovieCredits.enqueue(object : Callback<CreditsResponse> {
            override fun onResponse(
                call: Call<CreditsResponse>,
                response: Response<CreditsResponse>
            ) {
                val actors = response.body()?.cast
                binding.itemsContainer.adapter = adapter.apply {
                    addAll( actors?.map {
                        CastItem(it) {}
                    }?.toList() ?: listOf())
                }
            }

            override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                Timber.e(t.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _posterBinding = null
    }

    companion object {
        const val KEY_ID = "id"
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        const val ENGLISH = "en-US"
    }
}
