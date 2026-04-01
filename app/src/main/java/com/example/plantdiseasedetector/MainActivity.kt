package com.example.plantdiseasedetector

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var ivPlant: ImageView
    private lateinit var tvResult: TextView
    private lateinit var progressBar: ProgressBar

    // Safety formatted URL
    private var BASE_URL = "https://potato-api-1.onrender.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ensure URL is clean of hidden spaces and ends with /
        BASE_URL = BASE_URL.trim()
        if (!BASE_URL.endsWith("/")) {
            BASE_URL += "/"
        }

        ivPlant = findViewById(R.id.ivPlant)
        tvResult = findViewById(R.id.tvResult)
        progressBar = findViewById(R.id.progressBar)

        findViewById<Button>(R.id.btnCapture).setOnClickListener {
            checkCameraPermissionAndLaunch()
        }

        findViewById<Button>(R.id.btnUpload).setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }

    private fun checkCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            launchCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launchCamera()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            cameraLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Camera error", Toast.LENGTH_SHORT).show()
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as Bitmap?
            bitmap?.let {
                ivPlant.setImageBitmap(it)
                val file = bitmapToFile(it)
                uploadImage(file)
            }
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            ivPlant.setImageURI(it)
            val file = uriToFile(it)
            uploadImage(file)
        }
    }

    private fun uploadImage(file: File) {
        progressBar.visibility = View.VISIBLE
        tvResult.text = "Predicting..."

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        service.uploadImage(body).enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val result = response.body()
                    tvResult.text = "Result: ${result?.`class`}\nConfidence: ${String.format("%.2f", (result?.confidence ?: 0.0) * 100)}%"
                } else {
                    tvResult.text = "Server Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                tvResult.text = "Network Error: ${t.message}"
            }
        })
    }

    private fun bitmapToFile(bitmap: Bitmap): File {
        val file = File(cacheDir, "temp_cap_${System.currentTimeMillis()}.jpg")
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()
        return file
    }

    private fun uriToFile(uri: Uri): File {
        val file = File(cacheDir, "temp_gal_${System.currentTimeMillis()}.jpg")
        contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
}