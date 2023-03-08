package com.example.esapi_noauthotizaion

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface RestService {
    @GET("posts")
    fun getData() : Call<List<DataItem>>
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMyData()
    }

    private fun getMyData() {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .create(RestService::class.java)
                val retrofitData = retrofit.getData()

        retrofitData.enqueue(object : Callback<List<DataItem>?> {
            override fun onResponse(
                call: Call<List<DataItem>?>,
                response: Response<List<DataItem>?>
            ) {
                Log.d("MainActivity", "The repo work? ${response.isSuccessful} ")
                val responseBody = response.body()
                val myStringBuilder = StringBuilder()
                if (responseBody != null) {
                    for (myData in responseBody) {
                        myStringBuilder.append(myData.id)
                        myStringBuilder.append("\n")
                        myStringBuilder.append(myData.body)
                        myStringBuilder.append("\n")
                    }
                } else {
                    Log.d("MainActivity", "Error, nothing to show")
                }
                val text = findViewById<TextView>(R.id.textView)
                text.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<DataItem>?>, t: Throwable) {
                Log.d("MainActivity", "ERROR! ${t.message}")
            }

        })
    }
}