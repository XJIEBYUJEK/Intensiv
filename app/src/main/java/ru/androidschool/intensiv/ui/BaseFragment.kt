package ru.androidschool.intensiv.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.databinding.ProgressBarBinding

abstract class BaseFragment <Binding : ViewBinding> : Fragment() {

    protected val binding: Binding get() = _binding!!
    private var _binding: Binding? = null
    private var _progressBarBinding: ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!
    protected val compositeDisposable = CompositeDisposable()

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createViewBinding(inflater, container)
        _progressBarBinding = ProgressBarBinding.bind(binding.root)
        return binding.root
    }

    protected fun <T> Single<T>.showProgressBar(): Single<T> {
        return this.doOnSubscribe {
            progressBarBinding.progressBar.visibility = View.VISIBLE
        }.doFinally {
            progressBarBinding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}