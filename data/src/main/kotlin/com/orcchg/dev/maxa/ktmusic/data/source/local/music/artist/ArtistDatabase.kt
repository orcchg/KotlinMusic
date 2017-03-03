package com.orcchg.dev.maxa.ktmusic.data.source.local.music.artist

import android.text.TextUtils
import com.orcchg.dev.maxa.ktmusic.data.injection.local.DaggerMigrationComponent
import com.orcchg.dev.maxa.ktmusic.data.injection.local.MigrationComponent
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.ArtistDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.SmallArtistDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.ArtistToDboMapper
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.SmallArtistToDboMapper
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.ArtistToDboPopulator
import com.orcchg.dev.maxa.ktmusic.data.source.repository.RepoUtility
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.artist.ArtistDataSource
import com.orcchg.dev.maxa.ktmusic.domain.DomainConstant
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Artist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.SmallArtist
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import hugo.weaving.DebugLog
import io.realm.Realm
import rx.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistDatabase @Inject constructor(
        private val artistMapper: ArtistToDboMapper,
        private val smallArtistMapper: SmallArtistToDboMapper,
        private val populator: ArtistToDboPopulator)
    : ArtistDataSource {

    private val migrationComponent: MigrationComponent

    init {
        this.migrationComponent = DaggerMigrationComponent.create()
    }

    /* Create */
    // ------------------------------------------

    /* Read */
    // ------------------------------------------
    @DebugLog
    override fun artists(): Observable<List<SmallArtist>> {
        return artists(-1, 0)
    }

    @DebugLog
    override fun artists(limit: Int, offset: Int): Observable<List<SmallArtist>> {
        return artists(limit, offset, null)
    }

    @DebugLog
    override fun artists(genres: List<String>?): Observable<List<SmallArtist>> {
        return artists(-1, 0, genres)
    }

    @DebugLog
    override fun artists(limit: Int, offset: Int, genres: List<String>?): Observable<List<SmallArtist>> {
        RepoUtility.checkLimitAndOffset(limit, offset)
        val realm = Realm.getInstance(migrationComponent.realmConfiguration())
        val dbos = realm.where(SmallArtistDBO::class.java).findAll()
        val models = ArrayList<SmallArtist>()
        val size = if (limit < 0) dbos.size else limit
        RepoUtility.checkListBounds(offset + size - 1, dbos.size)
        for (i in offset..offset + size - 1) {
            models.add(smallArtistMapper.mapBack(dbos[i]))
        }
        realm.close()
        return Observable.just<List<SmallArtist>>(models)
    }

    @DebugLog
    override fun artist(artistId: Long): Observable<Artist> {
        if (artistId != DomainConstant.BAD_ID) {
            val realm = Realm.getInstance(migrationComponent.realmConfiguration())
            var model: Artist? = null
            val dbo = realm.where(ArtistDBO::class.java).equalTo(ArtistDBO.COLUMN_ID, artistId).findFirst()
            if (dbo != null) model = artistMapper.mapBack(dbo)
            realm.close()
            if (model != null) return Observable.just(model)
        }
        return Observable.empty()
    }

    @DebugLog
    override fun total(): Observable<TotalValue> {
        val realm = Realm.getInstance(migrationComponent.realmConfiguration())
        val dbos = realm.where(SmallArtistDBO::class.java).findAll()
        val total = TotalValue(value = dbos.size)
        realm.close()
        return Observable.just(total)
    }

    @DebugLog
    override fun total(genres: List<String>?): Observable<TotalValue> {
        if (genres != null && !genres.isEmpty()) {
            val genresStr = TextUtils.join(",", genres)
            val realm = Realm.getInstance(migrationComponent.realmConfiguration())
            val dbos = realm.where(ArtistDBO::class.java).equalTo(ArtistDBO.COLUMN_GENRES, genresStr).findAll()
            val total = TotalValue(value = dbos.size)
            realm.close()
            return Observable.just(total)
        }
        return total()
    }

    /* Update */
    // ------------------------------------------

    /* Delete */
    // ------------------------------------------
}
