package com.example.piyo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

object ImageCompressor {

    /**
     * Compress and resize image for optimal storage usage
     * Reduces file size by 70-80% without visible quality loss
     */
    fun compressImage(
        context: Context,
        imageUri: Uri,
        maxWidth: Int = 800,
        maxHeight: Int = 800,
        quality: Int = 70
    ): ByteArray? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            // Resize bitmap
            val resizedBitmap = resizeBitmap(originalBitmap, maxWidth, maxHeight)

            // Compress to JPEG
            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

            // Cleanup
            if (resizedBitmap != originalBitmap) {
                resizedBitmap.recycle()
            }
            originalBitmap.recycle()

            outputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Resize bitmap while maintaining aspect ratio
     */
    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        // Calculate aspect ratio
        val aspectRatio = width.toFloat() / height.toFloat()

        var newWidth = width
        var newHeight = height

        if (width > maxWidth || height > maxHeight) {
            if (aspectRatio > 1) {
                // Landscape
                newWidth = maxWidth
                newHeight = (maxWidth / aspectRatio).toInt()
            } else {
                // Portrait
                newHeight = maxHeight
                newWidth = (maxHeight * aspectRatio).toInt()
            }
        }

        return if (newWidth != width || newHeight != height) {
            Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        } else {
            bitmap
        }
    }

    /**
     * Get estimated size reduction percentage
     */
    fun getCompressionRatio(originalSize: Long, compressedSize: Long): Float {
        return ((originalSize - compressedSize).toFloat() / originalSize.toFloat()) * 100
    }
}

