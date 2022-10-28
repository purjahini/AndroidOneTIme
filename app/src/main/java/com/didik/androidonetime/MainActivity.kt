package com.didik.androidonetime

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.didik.androidonetime.adapter.AdapterMahasiswa
import com.didik.androidonetime.databinding.ActivityMainBinding
import com.didik.androidonetime.model.mahasiswaModel
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Proses...")
        progressDialog.setMessage("Mohon Menunggu..")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Klik floating button , menu dalam pengembangan", Toast.LENGTH_SHORT).show()
        }

        loadMahasiswa()


    }

    private fun loadMahasiswa() {
        progressDialog.show()
        AndroidNetworking.post("http://belipulsabeta.purja.web.id/public/api/mahasiswa")
            .addHeaders("Authorization","BearerpG6ff0zt6LNTGnh1")
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object  : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                   progressDialog.dismiss()
                    val respon = response.toString()
                    Log.d("Mahasiswa", respon)
                    val json = JSONObject(respon)
                    val apiStatus = json.getInt("api_status")
                    val apiMessage = json.getString("api_message")
                    if (apiStatus.equals(1)) {
                        val data = Gson().fromJson(respon, mahasiswaModel::class.java).data

                        binding.rvDataMahasiswa.apply {
                            adapter = AdapterMahasiswa(this@MainActivity, data)
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            setHasFixedSize(true)
                        }


                    } else {
                        Toast.makeText(this@MainActivity, apiMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(anError: ANError?) {
                    progressDialog.dismiss()
                    if (anError != null) {
                        Toast.makeText(this@MainActivity, anError.errorBody, Toast.LENGTH_SHORT).show()
                    }
                }

            })



    }

    override fun onResume() {
        super.onResume()
        loadMahasiswa()
    }
}