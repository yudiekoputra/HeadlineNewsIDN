package com.examp.yudiekoputra.headlinenewsidn.Kategori

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

class Technology : AppCompatActivity() {
    private lateinit var news: RecyclerView
    private var adapter: NewsAdapter? = null
    var list: List<News> = ArrayList<News>()
    val category = "health"
    var loading: ProgressDialog? = null
    var api: ApiService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_technology)
        news = findViewById<RecyclerView>(R.id.news)
        api = Server.apiService
        adapter = NewsAdapter(this@Technology, list)
        news.setHasFixedSize(true)
        news.setLayoutManager(LinearLayoutManager(this@Technology))
        news.setAdapter(adapter)
        refresh()

        //membuat back button toolbar
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
    }

    fun refresh() {
        loading = ProgressDialog(this@Technology)
        loading!!.setCancelable(false)
        loading!!.setMessage("Loading...")
        showDialog()
        api?.getListNews("id", category, "69514c97869a40d9a054076a3a8b1cf9")
            ?.enqueue(object : Callback<ResponseNews?> {
                override fun onResponse(
                    call: Call<ResponseNews?>?,
                    response: Response<ResponseNews?>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful()) {
                            hideDialog()
                            list = response.body()?.newsList!!
                            news.setAdapter(NewsAdapter(this@Technology, list))
                            adapter?.notifyDataSetChanged()
                        } else {
                            hideDialog()
                            Toast.makeText(
                                this@Technology,
                                "Gagal mengambil data !",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseNews?>?, t: Throwable?) {
                    hideDialog()
                    Toast.makeText(
                        this@Technology,
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
        val intent = Intent(this@Technology, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this@Technology, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}