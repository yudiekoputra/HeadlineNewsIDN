package com.examp.yudiekoputra.headlinenewsidn

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examp.yudiekoputra.headlinenewsidn.API.ApiService
import com.examp.yudiekoputra.headlinenewsidn.API.Server
import com.examp.yudiekoputra.headlinenewsidn.Adapter.NewsAdapter
import com.examp.yudiekoputra.headlinenewsidn.Entity.News
import com.examp.yudiekoputra.headlinenewsidn.Entity.ResponseNews
import com.examp.yudiekoputra.headlinenewsidn.Kategori.*
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var news:RecyclerView
    private var adapter: NewsAdapter?= null
    var list : List<News> = ArrayList()
    var loading : ProgressDialog? = null
    var api: ApiService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        news = findViewById(R.id.news)
        api = Server.apiService
        adapter = NewsAdapter(this@MainActivity, list)
        news.setHasFixedSize(true)
        news.setLayoutManager(LinearLayoutManager(this@MainActivity))
        news.setAdapter(adapter)
        refresh()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    fun refresh() {
        loading = ProgressDialog(this@MainActivity)
        loading!!.setCancelable(false)
        loading!!.setMessage("Loading...")
        showDialog()
        api!!.getListAllNews("id", "69514c97869a40d9a054076a3a8b1cf9")
            ?.enqueue(object : Callback<ResponseNews?> {
                override fun onResponse(
                    call: Call<ResponseNews?>?,
                    response: Response<ResponseNews?>?
                ) {
                    if (response!!.isSuccessful) {
                        hideDialog()
                        list = response.body()?.newsList!!
                        news!!.adapter = NewsAdapter(this@MainActivity, list)
                        adapter!!.notifyDataSetChanged()
                    } else {
                        hideDialog()
                        Toast.makeText(
                            this@MainActivity,
                            "Gagal mengambil data !",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseNews?>?, t: Throwable?) {
                    hideDialog()
                    Toast.makeText(
                        this@MainActivity,
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

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
                moveTaskToBack(true)
            } else {
                Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()
            }
            backPressed = System.currentTimeMillis()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Anda yakin ingin keluar ?")
        alertDialogBuilder.setPositiveButton(
            "Ya"
        ) { arg0, arg1 -> moveTaskToBack(true) }
        alertDialogBuilder.setNegativeButton(
            "Tidak"
        ) { arg0, arg1 -> }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.business) {
            val bus = Intent(this@MainActivity, Business::class.java)
            startActivity(bus)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else if (id == R.id.entertainment) {
            val enter = Intent(this@MainActivity, Entertainment::class.java)
            startActivity(enter)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else if (id == R.id.health) {
            val heal = Intent(this@MainActivity, Health::class.java)
            startActivity(heal)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else if (id == R.id.science) {
            val scien = Intent(this@MainActivity, Science::class.java)
            startActivity(scien)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else if (id == R.id.sports) {
            val sport = Intent(this@MainActivity, Sport::class.java)
            startActivity(sport)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else if (id == R.id.technology) {
            val tech = Intent(this@MainActivity, Technology::class.java)
            startActivity(tech)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else if (id == R.id.logout) {
            logout()
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        private const val TIME_LIMIT = 1800
        private var backPressed: Long = 0
    }
}