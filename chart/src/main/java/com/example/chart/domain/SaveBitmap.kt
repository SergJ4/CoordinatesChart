package com.example.chart.domain

import android.graphics.Bitmap
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.FileRepository
import io.reactivex.Completable
import javax.inject.Inject

@FragmentScope
class SaveBitmap @Inject constructor(private val fileRepository: FileRepository) {

    operator fun invoke(bitmap: Bitmap): Completable = fileRepository.saveBitmapToFile(bitmap)
}