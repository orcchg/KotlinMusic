package com.orcchg.dev.maxa.ktmusic.library.domain.executor

import java.util.concurrent.Executor

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the {@link UseCase} out of the UI thread.
 */
interface ThreadExecutor : Executor
