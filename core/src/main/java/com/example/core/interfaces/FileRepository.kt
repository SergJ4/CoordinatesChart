package com.example.core.interfaces

import android.graphics.Bitmap
import io.reactivex.Completable

interface FileRepository {

    fun saveBitmapToFile(bitmap: Bitmap): Completable
}