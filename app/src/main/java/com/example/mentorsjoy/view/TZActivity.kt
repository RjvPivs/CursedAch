package com.example.mentorsjoy.view

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mentorsjoy.repository.PdfData
import com.example.mentorsjoy.R
import com.example.mentorsjoy.backend.Generator
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class TZActivity : AppCompatActivity() {
    lateinit var pdfView: PDFView
    lateinit var pdfData: PdfData
    lateinit var result : File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tzactivity)
        val arguments = intent.extras
        pdfData = arguments!!.getSerializable("pdf") as PdfData
        var file = File(filesDir, "aboba.pdf")
        Generator.createPdf(file.canonicalPath, pdfData, "TZ");
        pdfView = findViewById(R.id.pdfView)
        pdfView.fromFile(file).load()
        result = file
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun createFile(view: View){
        var resolver: ContentResolver = contentResolver
        var values: ContentValues = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "cursed_"+".pdf")
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        var p : OutputStream? = uri?.let { resolver.openOutputStream(it) }
        var temp: InputStream = result.inputStream()
        p.use { out ->
            if (out != null) {
                temp.copyTo(out)
            }
        }
        temp.close()
        p?.close()
    }
}