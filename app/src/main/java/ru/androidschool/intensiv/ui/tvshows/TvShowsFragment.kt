package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.applySchedulers
import timber.log.Timber

class TvShowsFragment : BaseFragment<TvShowsFragmentBinding>() {

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        TvShowsFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getPopularTvShows = MovieApiClient.apiClient.getPopularTvShows(API_KEY)

        compositeDisposable.add(getPopularTvShows.applySchedulers()
            .showProgressBar()
            .subscribe({ tvShowsResponse ->
                val tvShows = tvShowsResponse.results
                binding.tvShowRecyclerView.adapter = adapter.apply {
                    addAll(tvShows.map {
                        TvShowItem(it) {}
                    }.toList())
                }
            }, { error ->
                Timber.e(error)
            }))
    }

    companion object {
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}
