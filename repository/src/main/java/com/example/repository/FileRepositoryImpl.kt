package com.example.repository

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import com.example.core.di.scopes.RepoScope
import com.example.core.interfaces.FileRepository
import io.reactivex.Completable
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@RepoScope
class FileRepositoryImpl @Inject constructor(private val context: Context) : FileRepository {

    override fun saveBitmapToFile(bitmap: Bitmap): Completable {
        return Completable.fromRunnable {
            val baseDir = Environment.getExternalStorageDirectory().absolutePath

            val formatter = SimpleDateFormat("yyyyMMdd_HHmmss")
            val currentDateAndTime = formatter.format(Date())
            val fileName = "Chart_$currentDateAndTime.jpg"

            val file = File(baseDir + File.separator + fileName)
            val fOut = FileOutputStream(file)

            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                90,
                fOut
            )
            fOut.flush()
            fOut.close()

            MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, file.name, file.name)
        }
    }
}