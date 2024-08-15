package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.databinding.ItemTvShowBinding
import ru.androidschool.intensiv.ui.loadUrl

class TvShowItem(
    private val content: TvShow,
    private val onClick: (tvShow: TvShow) -> Unit
) : BindableItem<ItemTvShowBinding>() {

    override fun getLayout(): Int = R.layout.item_tv_show

    override fun bind(view: ItemTvShowBinding, position: Int) {
        view.description.text = content.name
        view.tvShowRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }
        view.imagePreview.loadUrl(content.posterPath)
    }

    override fun initializeViewBinding(v: View) = ItemTvShowBinding.bind(v)
}
