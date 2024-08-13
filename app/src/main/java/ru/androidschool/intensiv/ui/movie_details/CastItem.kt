package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Cast
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.databinding.ItemCastBinding

class CastItem(
    private val content: Cast,
    private val onClick: (cast: Cast) -> Unit
) : BindableItem<ItemCastBinding>() {

    override fun getLayout(): Int = R.layout.item_cast

    override fun bind(view: ItemCastBinding, position: Int) {
        view.castName.text = content.name
        view.content.setOnClickListener {
            onClick.invoke(content)
        }

        Picasso.get()
            .load(content.profilePath)
            .into(view.castImage)
    }

    override fun initializeViewBinding(view: View) = ItemCastBinding.bind(view)
}