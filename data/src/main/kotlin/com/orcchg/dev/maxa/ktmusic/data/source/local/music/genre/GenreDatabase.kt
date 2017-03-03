package com.orcchg.dev.maxa.ktmusic.data.source.local.music.genre

import android.text.TextUtils
import com.orcchg.dev.maxa.ktmusic.data.injection.local.DaggerMigrationComponent
import com.orcchg.dev.maxa.ktmusic.data.injection.local.MigrationComponent
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.GenreDBO
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.GenreToDboMapper
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.GenreToDboPopulator
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.genre.GenreDataSource
import com.orcchg.dev.maxa.ktmusic.domain.model.music.Genre
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import io.realm.Realm
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreDatabase @Inject constructor(
        private val mapper: GenreToDboMapper,
        private val populator: GenreToDboPopulator)
    : GenreDataSource {

    private val migrationComponent: MigrationComponent

    init {
        this.migrationComponent = DaggerMigrationComponent.create()
    }

    /* Create */
    // ------------------------------------------

    /* Read */
    // ------------------------------------------
    override fun genres(): Observable<List<Genre>> {
        val realm = Realm.getInstance(migrationComponent.realmConfiguration())
        val dbos = realm.where(GenreDBO::class.java).findAll()
        val models = mapper.mapBack(dbos)
        realm.close()
        return Observable.just<List<Genre>>(models)
    }

    override fun genre(name: String): Observable<Genre> {
        if (!TextUtils.isEmpty(name)) {
            val realm = Realm.getInstance(migrationComponent.realmConfiguration())
            var model: Genre? = null
            val dbo = realm.where(GenreDBO::class.java).equalTo(GenreDBO.COLUMN_NAME, name).findFirst()
            if (dbo != null) model = mapper.mapBack(dbo)
            realm.close()
            if (model != null) return Observable.just(model)
        }
        return Observable.empty()
    }

    override fun total(): Observable<TotalValue> {
        val realm = Realm.getInstance(migrationComponent.realmConfiguration())
        val dbos = realm.where(GenreDBO::class.java).findAll()
        val total = TotalValue(value = dbos.size)
        realm.close()
        return Observable.just(total)
    }

    /* Update */
    // ------------------------------------------

    /* Delete */
    // ------------------------------------------
}
