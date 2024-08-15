package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.data.MoviesResponse
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchEditText = searchBinding.searchToolbar.binding.searchEditText

        val searchObservable = Observable.create<String> { e ->
            searchEditText.afterTextChanged {
                e.onNext(
                    it.toString()
                )
            }
            e.setCancellable {
                searchEditText.setOnTouchListener(null)
                e.onComplete()
            }
        }
        searchObservable.debounce(500, TimeUnit.MILLISECONDS)
            .filter{ it.length > MIN_LENGTH}
            .map { it.replace(REMOVED_SYMBOL,"") }
            .subscribe({
                Timber.i("Search text: $it")
                //openSearch(it)
            }, {
                Timber.e(it)
            })

        val getNowPlaying = MovieApiClient.apiClient.getNowPlaying(API_KEY, ENGLISH)
        val getPopular = MovieApiClient.apiClient.getPopularMovies(API_KEY, ENGLISH)
        val getUpcoming = MovieApiClient.apiClient.getUpcomingMovies(API_KEY, ENGLISH)

        getNowPlaying.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val movies = response.results
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

        getPopular.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("Success")
            }, { error ->
                Timber.e(error)
            })

        getUpcoming.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("Success")
            }, { error ->
                Timber.e(error)
            })
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
        const val REMOVED_SYMBOL = " "
        const val KEY_ID = "id"
        const val KEY_SEARCH = "search"
        const val ENGLISH = "en-US"
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}
