package com.orcchg.dev.maxa.ktmusic.utility.ui

import android.content.Context
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils

/**
 * Transformations of image for Glide.
 * See my blog for details {@see https://orcchg.wordpress.com/2016/02/29/glide-crop-transformations/}
 */
object ImageTransform {

    fun create(context: Context, cropType: CropType): BitmapTransformation {
        when (cropType) {
            CropType.CIRCLE_CROP -> return CircleTransform(context)
            else -> return CropTransform(cropType, context)
        }
    }

    // --------------------------------------------------------------------------------------------
    class CircleTransform(context: Context) : BitmapTransformation(context) {

        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
            return circleCrop(pool, toTransform)
        }

        private fun circleCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
            if (source == null) return null
            val size = Math.min(source.width, source.height)
            val x = (source.width - size) / 2
            val y = (source.height - size) / 2

            var squared: Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)
            if (squared == null) {
                squared = Bitmap.createBitmap(source, x, y, size, size)
            }
            var result: Bitmap? = pool.get(size, size, Bitmap.Config.ARGB_8888)
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(result!!)
            val paint = Paint()
            paint.shader = BitmapShader(squared!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)
            return result
        }

        override fun getId(): String {
            return "com.orcchg.ImageTransform: CircleTransform"
        }
    }

    // --------------------------------------------------------------------------------------------
    class CropTransform(val mCropType: CropType, context: Context) : BitmapTransformation(context) {

        private val PAINT_FLAGS = Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG

        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
            val toReuse = pool.get(outWidth, outHeight, if (toTransform.config != null) toTransform.config else Bitmap.Config.ARGB_8888)
            val transformed = crop(toReuse, toTransform, outWidth, outHeight)
            if (toReuse != null && toReuse != transformed && !pool.put(toReuse)) {
                toReuse.recycle()
            }
            return transformed
        }

        private fun crop(recycled: Bitmap?, toTransform: Bitmap?, outWidth: Int, outHeight: Int): Bitmap? {
            if (toTransform == null) {
                return null
            } else if (toTransform.width == outWidth && toTransform.height == outHeight) {
                return toTransform
            }

            val scale: Float
            var dx = 0f
            var dy = 0f
            var offsetFactor = 0.0f
            val m = Matrix()
            if (toTransform.width * outHeight > outWidth * toTransform.height) {
                scale = outHeight.toFloat() / toTransform.height.toFloat()
                dx = (outWidth - toTransform.width * scale) * 0.5f
            } else {
                scale = outWidth.toFloat() / toTransform.width.toFloat()
                when (mCropType) {
                    CropType.TOP_CROP -> dy = 0f
                    CropType.BOTTOM_CROP -> dy = outHeight - toTransform.height * scale
                    else -> {
                        dy = (outHeight - toTransform.height * scale) * 0.5f
                        offsetFactor = 0.5f
                    }
                }
            }

            m.setScale(scale, scale)
            m.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + offsetFactor).toInt().toFloat())
            val result: Bitmap
            if (recycled != null) {
                result = recycled
            } else {
                result = Bitmap.createBitmap(outWidth, outHeight, getSafeConfig(toTransform))
            }

            // We don't add or remove alpha, so keep the alpha setting of the Bitmap we were given.
            TransformationUtils.setAlpha(toTransform, result)

            val canvas = Canvas(result)
            val paint = Paint(PAINT_FLAGS)
            canvas.drawBitmap(toTransform, m, paint)
            return result
        }

        override fun getId(): String {
            return "com.orcchg.ImageTransform: CropTransform_" + mCropType
        }

        private fun getSafeConfig(bitmap: Bitmap): Bitmap.Config {
            return if (bitmap.config != null) bitmap.config else Bitmap.Config.ARGB_8888
        }
    }
}
