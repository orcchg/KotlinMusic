package com.orcchg.dev.maxa.ktmusic.app.ui.music.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.bindOptionalView
import butterknife.bindView
import butterknife.bindViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orcchg.dev.maxa.ktmusic.R
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseActivity
import com.orcchg.dev.maxa.ktmusic.app.ui.base.common.view.misc.ViewUtility
import com.orcchg.dev.maxa.ktmusic.app.ui.music.detail.injection.ArtistDetailsComponent
import com.orcchg.dev.maxa.ktmusic.app.ui.music.detail.injection.ArtistDetailsModule
import com.orcchg.dev.maxa.ktmusic.app.ui.music.detail.injection.DaggerArtistDetailsComponent
import com.orcchg.dev.maxa.ktmusic.app.ui.viewobject.ArtistDetailsVO
import com.orcchg.dev.maxa.ktmusic.domain.DomainConstant
import com.orcchg.dev.maxa.ktmusic.utility.ui.CropType
import com.orcchg.dev.maxa.ktmusic.utility.ui.ImageTransform

class ArtistDetailsActivity : BaseActivity<ArtistDetailsContract.View, ArtistDetailsContract.Preseneter>(), ArtistDetailsContract.View {

    val collapsingToolbar:   CollapsingToolbarLayout? by bindOptionalView(R.id.collapsing_toolbar)  // disabled on tablets
    val toolbar:             Toolbar                  by bindView(R.id.toolbar)
    val progressBar:         ProgressBar              by bindView(R.id.pb_loading)
    val coverImageView:      ImageView                by bindView(R.id.iv_cover)
    val coverErrorTextView:  TextView                 by bindView(R.id.tv_cover_error)
    val topOverlayView:      View                     by bindView(R.id.top_overlay)
    val bottomOverlayView:   View                     by bindView(R.id.bottom_overlay)
    val descriptionTextView: TextView                 by bindView(R.id.tv_description)
    val linkTextView:        TextView                 by bindView(R.id.tv_link)
    val genresTextView:      TextView                 by bindView(R.id.tv_genres)
    val tracksCountTextView: TextView                 by bindView(R.id.tv_tracks_count)
    val albumsCountTextView: TextView                 by bindView(R.id.tv_albums_count)
    val starViews: List<ImageView> by bindViews(R.id.iv_star_1, R.id.iv_star_2, R.id.iv_star_3, R.id.iv_star_4, R.id.iv_star_5)

    private var strokeStar: Drawable? = null
    private var halfStar: Drawable? = null
    private var fullStar: Drawable? = null

    private var artistId = DomainConstant.BAD_ID
    private lateinit var component: ArtistDetailsComponent

    companion object {
        val EXTRA_ARTIST_ID = "extra_artist_id"

        fun getCallingIntent(context: Context, artistId: Long): Intent {
            val intent = Intent(context, ArtistDetailsActivity::class.java)
            intent.putExtra(EXTRA_ARTIST_ID, artistId)
            return intent
        }
    }

    override fun createPresenter(): ArtistDetailsContract.Preseneter {
        return component.presenter()
    }

    override fun injectDependencies() {
        // ArtistRepositoryComponent artistRepositoryComponent = DaggerArtistRepositoryComponent.create();
        component = DaggerArtistDetailsComponent.builder()
                .applicationComponent(applicationComponent)
                //.artistRepositoryComponent(artistRepositoryComponent)
                .artistDetailsModule(ArtistDetailsModule(artistId))
                .build()
    }

    /* Lifecycle */
    // ------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        artistId = intent.getLongExtra(EXTRA_ARTIST_ID, DomainConstant.BAD_ID)
        if (ViewUtility.isLargeScreen(this)) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)  // removes title from Activity as Dialog
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_details)
        ButterKnife.bind(this)
        initToolbar()
        initResources()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseResources()
    }

    /* View */
    // ------------------------------------------
    private fun initToolbar() {
        collapsingToolbar?.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingToolbar?.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        toolbar.setNavigationOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                supportFinishAfterTransition()
            } else {
                finish()
            }
        }
    }

    private fun initResources() {
        val strokeStar1 = resources.getDrawable(R.drawable.ic_star_border_white_24dp)
        val halfStar1 = resources.getDrawable(R.drawable.ic_star_half_white_24dp)
        val fullStar1 = resources.getDrawable(R.drawable.ic_star_white_24dp)

        strokeStar = DrawableCompat.wrap(strokeStar1)
        halfStar = DrawableCompat.wrap(halfStar1)
        fullStar = DrawableCompat.wrap(fullStar1)

        @ColorInt val color = resources.getColor(R.color.star_color)
        DrawableCompat.setTint(strokeStar!!, color)
        DrawableCompat.setTint(halfStar!!, color)
        DrawableCompat.setTint(fullStar!!, color)
    }

    private fun releaseResources() {
        DrawableCompat.unwrap<Drawable>(strokeStar!!)
        DrawableCompat.unwrap<Drawable>(halfStar!!)
        DrawableCompat.unwrap<Drawable>(fullStar!!)
    }

    internal fun startProgressAnimation() {
        progressBar.visibility = View.VISIBLE
        topOverlayView.visibility = View.INVISIBLE
        bottomOverlayView.visibility = View.INVISIBLE
        coverErrorTextView.visibility = View.GONE
    }

    internal fun stopProgressAnimation() {
        progressBar.visibility = View.GONE
        topOverlayView.visibility = View.VISIBLE
        bottomOverlayView.visibility = View.VISIBLE
        coverErrorTextView.visibility = View.GONE
    }

    /* Contract */
    // ------------------------------------------
    override fun setGrade(grade: Int) {
        val quotient = grade / 2
        val residual = grade % 2

        for (i in 0..quotient - 1) {
            starViews[i].setBackgroundDrawable(fullStar)
        }
        if (residual > 0) {
            starViews[quotient].setBackgroundDrawable(halfStar)
        }
        for (i in quotient + residual..starViews.size - 1) {
            starViews[i].setBackgroundDrawable(strokeStar)
        }
    }

    override fun showArtist(artist: ArtistDetailsVO) {
        collapsingToolbar?.title = artist.name
        toolbar.title = artist.name
        genresTextView.text = TextUtils.join(", ", artist.genres)
        tracksCountTextView.text = String.format(resources.getString(R.string.str_tracks_count), Integer.toString(artist.tracksCount))
        albumsCountTextView.text = String.format(resources.getString(R.string.str_albums_count), Integer.toString(artist.albumsCount))
        descriptionTextView.text = artist.description
        linkTextView.text = artist.link
        linkTextView.movementMethod = LinkMovementMethod.getInstance()

        startProgressAnimation()

        Glide.with(this)
            .load(artist.coverLarge)
            .asBitmap()
            .listener(object : RequestListener<String, Bitmap> {
                override fun onException(e: Exception, model: String, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                    showError()
                    return false
                }

                override fun onResourceReady(resource: Bitmap, model: String, target: Target<Bitmap>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    stopProgressAnimation()
                    return false
                }
            })
            .transform(ImageTransform.create(this, CropType.TOP_CROP))
            .into(coverImageView)
    }

    override fun showError() {
        progressBar.visibility = View.GONE
        topOverlayView.visibility = View.INVISIBLE
        bottomOverlayView.visibility = View.INVISIBLE
        coverErrorTextView.visibility = View.VISIBLE
    }
}
