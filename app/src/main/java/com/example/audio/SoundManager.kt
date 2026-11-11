package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

/**
 * Pure Kotlin real-time audio synthesizer for funny cartoon sound effects
 * and whimsical ambient background melody. Operates without any external media files.
 */
class SoundManager(private val context: Context) {

    private val sampleRate = 22050
    private val scope = CoroutineScope(Dispatchers.Default)

    var soundEffectsEnabled = true
    var musicEnabled = true
        set(value) {
            field = value
            if (value) startBackgroundMusic() else stopBackgroundMusic()
        }
    var hapticsEnabled = true

    private var musicJob: Job? = null

    private val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    /** Cheerful cartoon bubble pop */
    fun playPop() {
        if (!soundEffectsEnabled) return
        scope.launch {
            val duration = 0.08
            val numSamples = (duration * sampleRate).toInt()
            val samples = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val freq = 400.0 + 900.0 * (1.0 - t / duration)
                val envelope = (1.0 - t / duration) * (1.0 - t / duration)
                val value = sin(2.0 * PI * freq * t) * envelope
                samples[i] = (value * 32767 * 0.7).toInt().coerceIn(-32768, 32767).toShort()
            }
            playPcm(samples)
        }
        vibrate(30)
    }

    /** Bouncy cartoon spring boing */
    fun playBoing() {
        if (!soundEffectsEnabled) return
        scope.launch {
            val duration = 0.35
            val numSamples = (duration * sampleRate).toInt()
            val samples = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val baseFreq = 220.0 + (500.0 * t / duration)
                val vibrato = 25.0 * sin(2.0 * PI * 18.0 * t)
                val envelope = (1.0 - t / duration)
                val value = sin(2.0 * PI * (baseFreq + vibrato) * t) * envelope
                samples[i] = (value * 32767 * 0.75).toInt().coerceIn(-32768, 32767).toShort()
            }
            playPcm(samples)
        }
        vibrate(60)
    }

    /** Cartoon wrong buzzer ("uh-oh" double bonk) */
    fun playBuzzer() {
        if (!soundEffectsEnabled) return
        scope.launch {
            val duration = 0.28
            val numSamples = (duration * sampleRate).toInt()
            val samples = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val envelope = if (t < 0.12) {
                    (1.0 - t / 0.12)
                } else if (t < 0.14) {
                    0.0
                } else {
                    (1.0 - (t - 0.14) / 0.14)
                }
                val wave1 = if (sin(2.0 * PI * 130.0 * t) > 0) 1.0 else -1.0
                val wave2 = if (sin(2.0 * PI * 110.0 * t) > 0) 0.5 else -0.5
                val value = (wave1 + wave2) * 0.5 * envelope
                samples[i] = (value * 32767 * 0.7).toInt().coerceIn(-32768, 32767).toShort()
            }
            playPcm(samples)
        }
        vibratePattern(longArrayOf(0, 80, 50, 80))
    }

    /** Glorious victory fanfare (C-E-G-C arpeggio chime) */
    fun playVictory() {
        if (!soundEffectsEnabled) return
        scope.launch {
            val notes = listOf(523.25, 659.25, 783.99, 1046.50) // C5, E5, G5, C6
            val noteDuration = 0.12
            val totalDuration = noteDuration * 4 + 0.4
            val numSamples = (totalDuration * sampleRate).toInt()
            val samples = ShortArray(numSamples)

            for (noteIdx in notes.indices) {
                val startSample = (noteIdx * noteDuration * sampleRate).toInt()
                val len = if (noteIdx == notes.lastIndex) ((noteDuration + 0.4) * sampleRate).toInt() else (noteDuration * sampleRate).toInt()
                val freq = notes[noteIdx]

                for (i in 0 until len) {
                    val idx = startSample + i
                    if (idx < numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = (1.0 - t / (len.toDouble() / sampleRate))
                        val s = (sin(2.0 * PI * freq * t) + 0.3 * sin(4.0 * PI * freq * t)) * env
                        val existing = samples[idx].toDouble() / 32767.0
                        val combined = (existing + s * 0.5).coerceIn(-1.0, 1.0)
                        samples[idx] = (combined * 32767).toInt().toShort()
                    }
                }
            }
            playPcm(samples)
        }
        vibratePattern(longArrayOf(0, 40, 60, 40, 60, 120))
    }

    /** Crisp button tap click */
    fun playClick() {
        if (!soundEffectsEnabled) return
        scope.launch {
            val duration = 0.03
            val numSamples = (duration * sampleRate).toInt()
            val samples = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val freq = 800.0 - 500.0 * (t / duration)
                val envelope = (1.0 - t / duration)
                val value = sin(2.0 * PI * freq * t) * envelope
                samples[i] = (value * 32767 * 0.5).toInt().toShort()
            }
            playPcm(samples)
        }
        vibrate(15)
    }

    /** Pencil doodle scribble */
    fun playScribble() {
        if (!soundEffectsEnabled) return
        scope.launch {
            val duration = 0.15
            val numSamples = (duration * sampleRate).toInt()
            val samples = ShortArray(numSamples)
            var lastRand = 0.0
            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val rawNoise = (Math.random() * 2.0 - 1.0)
                lastRand = lastRand * 0.8 + rawNoise * 0.2
                val envelope = sin(PI * (t / duration))
                samples[i] = (lastRand * envelope * 32767 * 0.4).toInt().toShort()
            }
            playPcm(samples)
        }
        vibrate(20)
    }

    /** Sparkle hint chime */
    fun playSparkle() {
        if (!soundEffectsEnabled) return
        scope.launch {
            val notes = listOf(1318.51, 1567.98, 1760.0, 2093.0) // E6, G6, A6, C7
            val noteDuration = 0.07
            val numSamples = ((noteDuration * 4 + 0.2) * sampleRate).toInt()
            val samples = ShortArray(numSamples)

            for (noteIdx in notes.indices) {
                val startSample = (noteIdx * noteDuration * sampleRate).toInt()
                val len = (0.2 * sampleRate).toInt()
                val freq = notes[noteIdx]

                for (i in 0 until len) {
                    val idx = startSample + i
                    if (idx < numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = (1.0 - t / 0.2) * (1.0 - t / 0.2)
                        val s = sin(2.0 * PI * freq * t) * env
                        val existing = samples[idx].toDouble() / 32767.0
                        val combined = (existing + s * 0.4).coerceIn(-1.0, 1.0)
                        samples[idx] = (combined * 32767).toInt().toShort()
                    }
                }
            }
            playPcm(samples)
        }
        vibrate(30)
    }

    fun startBackgroundMusic() {
        if (!musicEnabled || musicJob?.isActive == true) return
        musicJob = scope.launch {
            // Whimsical pizzicato background cartoon melody loop
            val melody = listOf(
                Pair(523.25, 300L), // C5
                Pair(659.25, 300L), // E5
                Pair(783.99, 300L), // G5
                Pair(659.25, 300L), // E5
                Pair(587.33, 300L), // D5
                Pair(698.46, 300L), // F5
                Pair(783.99, 300L), // G5
                Pair(587.33, 300L), // D5
                Pair(523.25, 300L), // C5
                Pair(783.99, 300L), // G5
                Pair(880.00, 400L), // A5
                Pair(783.99, 400L), // G5
                Pair(659.25, 400L), // E5
                Pair(523.25, 600L)  // C5
            )

            while (isActive && musicEnabled) {
                for ((freq, dur) in melody) {
                    if (!isActive || !musicEnabled) break
                    val numSamples = ((dur / 1000.0) * sampleRate).toInt()
                    val samples = ShortArray(numSamples)
                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        val env = (1.0 - t / (dur / 1000.0))
                        val s = (sin(2.0 * PI * freq * t) * 0.7 + sin(4.0 * PI * freq * t) * 0.2) * env
                        samples[i] = (s * 32767 * 0.18).toInt().toShort()
                    }
                    playPcm(samples)
                    delay(dur + 40L)
                }
                delay(1200L)
            }
        }
    }

    fun stopBackgroundMusic() {
        musicJob?.cancel()
        musicJob = null
    }

    private fun playPcm(samples: ShortArray) {
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val audioFormat = AudioFormat.Builder()
                .setSampleRate(sampleRate)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .build()

            val bufferSize = samples.size * 2
            val track = AudioTrack(
                audioAttributes,
                audioFormat,
                bufferSize,
                AudioTrack.MODE_STATIC,
                0
            )

            track.write(samples, 0, samples.size)
            track.play()
            // Track will release after playing
            scope.launch {
                delay((samples.size.toDouble() / sampleRate * 1000).toLong() + 200L)
                try {
                    track.stop()
                    track.release()
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {
            // AudioTrack unavailable or interrupted
        }
    }

    private fun vibrate(durationMs: Long) {
        if (!hapticsEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (_: Exception) {}
    }

    private fun vibratePattern(pattern: LongArray) {
        if (!hapticsEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(pattern, -1)
            }
        } catch (_: Exception) {}
    }
}
