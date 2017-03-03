package com.orcchg.dev.maxa.ktmusic.utility.ui

import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

class UiUtility {
    companion object {

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        fun dimView(view: View) {
            dimView(view, 0.1f)
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        fun dimViewCancel(view: View) {
            dimView(view, 1.0f)
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        fun dimView(view: View, alpha: Float) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.alpha = alpha
            }
        }

        fun getAttributeColor(context: Context, attributeId: Int): Int {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attributeId, typedValue, true)
            return context.resources.getColor(typedValue.resourceId)
        }

        fun getAttributeDrawable(context: Context, attributeId: Int): Drawable? {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attributeId, typedValue, true)
            return context.resources.getDrawable(typedValue.resourceId)
        }

        fun getAttributeDimension(context: Context, attributeId: Int): Float {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attributeId, typedValue, true)
            return context.resources.getDimension(typedValue.resourceId)
        }

        // {@see http://stackoverflow.com/questions/15055458/detect-7-inch-and-10-inch-tablet-programmatically}
        fun getSmallestWidth(context: Activity): Float {
            val metrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(metrics)
            val widthPixels = metrics.widthPixels
            val heightPixels = metrics.heightPixels
            val scaleFactor = metrics.density
            val widthDp = widthPixels / scaleFactor
            val heightDp = heightPixels / scaleFactor
            val smallestWidth = Math.min(widthDp, heightDp)
            return smallestWidth
        }

        fun getScreenshot(view: View): Bitmap {
            view.isDrawingCacheEnabled = true
            val bmp = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false
            return bmp
        }

        /**
         * Retrieves [Bitmap] from file specified with {@param path}, resized to adopt
         * the {@param targetWidth} and {@param targetHeight}.

         * {@see https://developer.android.com/training/camera/photobasics.html}
         */
        fun getBitmapFromFile(path: String, targetWidth: Int, targetHeight: Int): Bitmap {
            // Get the dimensions of the bitmap
            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, bmOptions)
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight

            // Determine how much to scale down the image
            val scaleFactor = Math.min(photoW / targetWidth, photoH / targetHeight)

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor
            bmOptions.inPurgeable = true

            return BitmapFactory.decodeFile(path, bmOptions)
        }

        fun isVisible(view: View): Boolean {
            return view.visibility == View.VISIBLE
        }

        fun showSnackbar(activity: Activity, @StringRes resId: Int) {
            showSnackbar(activity, resId, Snackbar.LENGTH_SHORT)
        }

        fun showSnackbar(activity: Activity, @StringRes resId: Int, duration: Int) {
            Snackbar.make(activity.findViewById(R.id.content), resId, duration).show()
        }

        fun showSnackbar(activity: Activity, @StringRes resId: Int, duration: Int,
                         @StringRes actionResId: Int, listener: View.OnClickListener) {
            Snackbar.make(activity.findViewById(R.id.content), resId, duration)
                    .setActionTextColor(getAttributeColor(activity, com.orcchg.dev.maxa.ktmusic.utility.R.attr.colorAccent))
                    .setAction(actionResId, listener)
                    .show()
        }

        fun showSnackbar(activity: Activity, text: String) {
            showSnackbar(activity, text, Snackbar.LENGTH_SHORT)
        }

        fun showSnackbar(activity: Activity, text: String, duration: Int) {
            Snackbar.make(activity.findViewById(R.id.content), text, duration).show()
        }

        fun showSnackbar(activity: Activity, text: String, duration: Int,
                         @StringRes actionResId: Int, listener: View.OnClickListener) {
            Snackbar.make(activity.findViewById(R.id.content), text, duration)
                    .setActionTextColor(getAttributeColor(activity, com.orcchg.dev.maxa.ktmusic.utility.R.attr.colorAccent))
                    .setAction(actionResId, listener)
                    .show()
        }

        fun showSnackbar(view: View, @StringRes resId: Int) {
            showSnackbar(view, resId, Snackbar.LENGTH_SHORT)
        }

        fun showSnackbar(view: View, @StringRes resId: Int, duration: Int) {
            Snackbar.make(view, resId, duration).show()
        }

        fun showSnackbar(view: View, @StringRes resId: Int, duration: Int, @ColorInt colorId: Int,
                         @StringRes actionResId: Int, listener: View.OnClickListener) {
            Snackbar.make(view, resId, duration)
                    .setActionTextColor(view.resources.getColor(colorId))
                    .setAction(actionResId, listener)
                    .show()
        }

        fun showSnackbar(view: View, text: String) {
            showSnackbar(view, text, Snackbar.LENGTH_SHORT)
        }

        fun showSnackbar(view: View, text: String, duration: Int) {
            Snackbar.make(view, text, duration).show()
        }

        fun showSnackbar(view: View, text: String, duration: Int, @ColorInt colorId: Int,
                         @StringRes actionResId: Int, listener: View.OnClickListener) {
            Snackbar.make(view, text, duration)
                    .setActionTextColor(view.resources.getColor(colorId))
                    .setAction(actionResId, listener)
                    .show()
        }

        fun showSoftKeyboard(view: View) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
