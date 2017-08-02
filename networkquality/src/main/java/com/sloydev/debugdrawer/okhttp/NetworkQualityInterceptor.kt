package com.sloydev.debugdrawer.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class NetworkQualityInterceptor(val config: NetworkQualityConfig) : Interceptor {

    companion object {
        private val DELAY_VARIANCE = 0.40
    }
    var random = Random()


    override fun intercept(chain: Interceptor.Chain): Response {
        if (!config.networkEnabled) {
            throw IOException("Mock: No Internet connection")
        }
        sleepDelay()
        if (isFailure()) {
            throw IOException("Mock: Request failure")
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun sleepDelay() {
        val delay = calculateDelayMs()
        if (delay > 0) {
            try {
                Log.d("NetworkQuality", "Applying delay of $delay ms")
                Thread.sleep(delay)
            } catch (e: InterruptedException) {
                throw IOException("Delay interrupted")
            }

        }
    }

    private fun calculateDelayMs(): Long {
        val lowerBound = 1.0f - DELAY_VARIANCE
        val upperBound = 1.0f + DELAY_VARIANCE
        val bound = upperBound - lowerBound
        val delayPercent = lowerBound + this.random.nextFloat() * bound
        val callDelayMs = (config.delayMs.toFloat() * delayPercent).toLong()
        return callDelayMs
    }

    private fun isFailure() = random.nextInt(100) < config.errorPercentage
}