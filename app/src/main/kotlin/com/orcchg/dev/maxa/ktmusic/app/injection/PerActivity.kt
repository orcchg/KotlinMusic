package com.orcchg.dev.maxa.ktmusic.app.injection

import javax.inject.Scope

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the activity to be memorized in the
 * correct component.
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class PerActivity
