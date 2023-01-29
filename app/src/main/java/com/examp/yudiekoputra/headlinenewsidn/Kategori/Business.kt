package com.examp.yudiekoputra.headlinenewsidn.Kategori

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.gifdecoder.BuildConfig
import com.examp.yudiekoputra.headlinenewsidn.API.ApiService
import com.examp.yudiekoputra.headlinenewsidn.API.Server
import com.examp.yudiekoputra.headlinenewsidn.Adapter.NewsAdapter
import com.examp.yudiekoputra.headlinenewsidn.Entity.News
import com.examp.yudiekoputra.headlinenewsidn.Entity.ResponseNews
import com.examp.yudiekoputra.headlinenewsidn.MainActivity
import com.examp.yudiekoputra.headlinenewsidn.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Business:AppCompatActivity() {
    private lateinit var news: RecyclerView
    private var adapter: NewsAdapter? = null
    var list: List<News> = ArrayList<News>()
    val category = "business"
    var loading: ProgressDialog? = null
    var api: ApiService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        news = findViewById<RecyclerView>(R.id.news)
        api = Server.apiService
        adapter = NewsAdapter(this@Business, list)
        news.setHasFixedSize(true)
        news.setLayoutManager(LinearLayoutManager(this@Business))
        news.setAdapter(adapter)
        refresh()

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
    }

    fun refresh() {
        loading = ProgressDialog(this@Business)
        loading!!.setCancelable(false)
        loading!!.setMessage("Loading...")
        showDialog()
        api?.getListNews("id", category, "69514c97869a40d9a054076a3a8b1cf9")
            ?.enqueue(object : Callback<ResponseNews?> {
                override fun onResponse(
                    call: Call<ResponseNews?>?,
                    response: Response<ResponseNews?>?
                ) {
                    if (response!!.isSuccessful()) {
                        hideDialog()
                        list = response?.body()?.newsList!!
                        news.setAdapter(NewsAdapter(this@Business, list))
                        adapter?.notifyDataSetChanged()
                    } else {
                        hideDialog()
                        Toast.makeText(this@Business, "Gagal mengambil data !", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseNews?>?, t: Throwable?) {
                    hideDialog()
                    Toast.makeText(
                        this@Business,
                        "Gagal menyambung ke internet !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun showDialog() {
        if (!loading!!.isShowing) loading!!.show()
    }

    private fun hideDialog() {
        if (loading!!.isShowing) loading!!.dismiss()
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this@Business, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this@Business, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}