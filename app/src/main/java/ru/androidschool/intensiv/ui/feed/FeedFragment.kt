package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.data.MoviesResponse
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.BaseFragment
import ru.androidschool.intensiv.ui.afterTextChanged
import ru.androidschool.intensiv.ui.applySchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FeedFragment : BaseFragment<FeedFragmentBinding>() {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FeedFragmentBinding {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(_binding!!.root)
        return _binding!!
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchObserver()
        getMovies()
    }

    private fun getMovies(){
        val getNowPlaying = MovieApiClient.apiClient.getNowPlaying(API_KEY, ENGLISH)
        val getPopular = MovieApiClient.apiClient.getPopularMovies(API_KEY, ENGLISH)
        val getUpcoming = MovieApiClient.apiClient.getUpcomingMovies(API_KEY, ENGLISH)

        compositeDisposable.add(
            Single.zip(getNowPlaying,getPopular,getUpcoming){
                nowPlaying, popular, upcoming ->
                listOf( nowPlaying, popular, upcoming)
            }.applySchedulers().subscribe({ response ->
                val movies = response[0].results
                binding.moviesRecyclerView.adapter = adapter.apply {
                    addAll(movies.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(
                                movie
                            )
                        }
                    }.toList())
                }
            }, { error ->
                Timber.e(error)
            })
        )
    }

    private fun setupSearchObserver(){
        val searchObservable = searchBinding.searchToolbar.getSearchObservableWithFilter()
        compositeDisposable.add(searchObservable.subscribe({
            Timber.i("Search text: $it")
            //openSearch(it)
        }, {
            Timber.e(it)
        }))
    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt(KEY_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
    }

    companion object {
        const val MIN_LENGTH = 3
        const val KEY_ID = "id"
        const val KEY_SEARCH = "search"
        const val ENGLISH = "en-US"
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}
