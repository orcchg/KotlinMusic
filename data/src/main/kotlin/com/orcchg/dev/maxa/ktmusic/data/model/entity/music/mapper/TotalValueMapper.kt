package com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper

import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.TotalValueEntity
import com.orcchg.dev.maxa.ktmusic.domain.model.music.TotalValue
import com.orcchg.dev.maxa.ktmusic.library.domain.model.mapper.BaseMapper
import javax.inject.Inject

class TotalValueMapper @Inject constructor() : BaseMapper<TotalValueEntity, TotalValue>() {

    override fun map(`object`: TotalValueEntity): TotalValue {
        return TotalValue(value = `object`.value)
    }
}
