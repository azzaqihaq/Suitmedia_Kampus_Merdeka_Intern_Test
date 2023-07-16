package com.azzaqihaq.suitmediainterntest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.azzaqihaq.suitmediainterntest.adapter.OnUserClickListener
import com.azzaqihaq.suitmediainterntest.adapter.UserAdapter
import com.azzaqihaq.suitmediainterntest.api.ApiClient
import com.azzaqihaq.suitmediainterntest.api.ApiInterface
import com.azzaqihaq.suitmediainterntest.model.Data
import com.azzaqihaq.suitmediainterntest.model.UserResponse
import kotlinx.android.synthetic.main.activity_third_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenActivity : AppCompatActivity(), OnUserClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val list = ArrayList<Data>()
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: UserAdapter
    private var page = 1
    private var totalPage = 1
    private var isLoading = false
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        // To turn off dark mode in activity
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Setup the action bar
        val actionBar = supportActionBar
        actionBar!!.title = "Third Screen"
        actionBar.setDisplayHomeAsUpEnabled(true)

        name = intent.getStringExtra("name")!!

        layoutManager = LinearLayoutManager(this)
        swipeRefresh.setOnRefreshListener(this)
        setupRecyclerView()
        getUsers(false)
        rv_users.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem  = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val total            = adapter.itemCount
                if(!isLoading && page < totalPage) {
                    if(visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getUsers(false)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) progressBar.visibility = View.VISIBLE
        val page = page
        val retro = ApiClient.getRetroData().create(ApiInterface::class.java)
        retro.getUsers(page).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                totalPage = response?.body()?.total_pages!!
                val listResponse = response.body()?.data
                if (listResponse != null) {
                    adapter.addList(listResponse)
                }

                progressBar.visibility = View.INVISIBLE
                isLoading = false
                swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@ThirdScreenActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupRecyclerView() {
        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = layoutManager
        adapter = UserAdapter(list, this)
        rv_users.adapter = adapter
    }

    override fun onRefresh() {
        adapter.clear()
        page = 1
        getUsers(true)
    }

    override fun onUserItemClicked(position: Int) {
        val intent = Intent(this, SecondScreenActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("username", list[position]?.first_name + " " + list[position]?.last_name)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}