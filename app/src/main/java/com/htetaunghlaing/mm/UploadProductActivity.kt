package com.htetaunghlaing.mm

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.htetaunghlaing.mm.model.ErrorMessage
import com.htetaunghlaing.mm.model.FileInfo
import com.htetaunghlaing.mm.services.ServiceBuilder
import com.htetaunghlaing.mm.services.WebService
import com.htetaunghlaing.mm.tOast.H
import com.htetaunghlaing.mm.tOast.H.Companion.USER_TOKEN
import kotlinx.android.synthetic.main.activity_upload_product.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream

class UploadProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_product)
        image_upload_product.setOnClickListener {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    fileUpload()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun fileUpload() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), 103)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 103 && resultCode == Activity.RESULT_OK && data != null) {
            val data: Uri = data.data!!
            val inst: InputStream = contentResolver.openInputStream(data)!!
            val bitmap = BitmapFactory.decodeStream(inst)
            image_upload_product.imageBitmap = bitmap

            var imagePath = getImagePath(data)
            var file = File(imagePath)
            var requestBody: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            var body: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestBody)

            var servie: WebService = ServiceBuilder.buildService(WebService::class.java)
            val responseUpload: Call<FileInfo> = servie.ImageUpload("Bearer $USER_TOKEN", body)
            responseUpload.enqueue(object : Callback<FileInfo> {
                override fun onFailure(call: Call<FileInfo>, t: Throwable) {
                    H.l(t.message!!)
                }

                override fun onResponse(call: Call<FileInfo>, response: Response<FileInfo>) {
                    if (response.isSuccessful) {
                        val info: FileInfo = response.body()!!
                        //uploadProduct(info.name)
                        btnConfirm.setOnClickListener {
                            uploadProduct(info.name)
                        }
                    } else {
                        toast("Something went wrong!")
                    }
                }

            })

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getImagePath(uri: Uri): String? {
        var filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.moveToNext()
        var columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        var mediaPath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return mediaPath
    }

    fun uploadProduct(image: String) {
        val cat_id= et_id.text.toString().toInt()
        val name=et_name.text.toString()
        val price=et_price.text.toString().toDouble()
        val description=et_description.text.toString()

        toast("${cat_id}  ${description}" )

        var servie: WebService = ServiceBuilder.buildService(WebService::class.java)
        val responseUploa: Call<ErrorMessage> = servie.newProductUpload(
            "Bearer $USER_TOKEN",
            cat_id,
            name,
            price,
            image,
            description)
        responseUploa.enqueue(object : Callback<ErrorMessage>{
            override fun onFailure(call: Call<ErrorMessage>, t: Throwable) {
                toast(t.message.toString())
            }

            override fun onResponse(call: Call<ErrorMessage>, response: Response<ErrorMessage>) {
                if(response.isSuccessful){
                    val message:ErrorMessage= response.body()!!
                    toast(message.msg)
                }else{
                    toast("Errorrrrrrrrrrrrrrr!")
                }
            }
        })


    }
}