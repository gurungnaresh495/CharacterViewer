package com.example.simpsonsviewer

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import com.example.simpsonsviewer.adapter.CharacterListAdapter
import com.example.simpsonsviewer.data.CharacterInfo
import com.example.simpsonsviewer.databinding.ActivityMainBinding
import com.example.simpsonsviewer.fragment.ItemDetailsFragment
import com.example.simpsonsviewer.fragment.ItemListFragment
import com.example.simpsonsviewer.utils.ScreenUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CharacterListAdapter.ItemClickListener {

    lateinit var binding: ActivityMainBinding
     var searchMenu: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpFragments()
        setSupportActionBar(binding.toolbar)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()
        else
            finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)
        searchMenu = menu?.findItem(R.id.app_bar_search)
        setupSearchView()
        return true
    }

    private fun setUpFragments() {
        if (ScreenUtils.isTablet(this)){
            supportFragmentManager.beginTransaction()
                .replace(R.id.rv_fragment_container, ItemListFragment(), ItemListFragment.LIST_FRAGMENT_TAG)
                .replace(R.id.item_details_container, ItemDetailsFragment(), ItemDetailsFragment.DETAILS_FRAGMENT_TAG)
                .commit()
        }
        else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ItemListFragment(), ItemListFragment.LIST_FRAGMENT_TAG)
                .addToBackStack("TAG")
                .commit()
        }
    }

    override fun onItemClicked(character: CharacterInfo) {
        if (ScreenUtils.isTablet(this)){
            val detailsFrag = supportFragmentManager.findFragmentByTag(ItemDetailsFragment.DETAILS_FRAGMENT_TAG) as? ItemDetailsFragment
              detailsFrag?.updateDescription(character)
        }
        else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ItemDetailsFragment.newInstance(character))
                .addToBackStack("TAGGG")
                .commit()
        }
    }

    private fun setupSearchView() {
        val listFragment = supportFragmentManager.findFragmentByTag(ItemListFragment.LIST_FRAGMENT_TAG) as? ItemListFragment
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (searchMenu?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            isIconifiedByDefault = true
            queryHint = "please type your query"
        }

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    newText?.let { listFragment?.searchProducts(it) }
                    return true
                }
            }
        )
    }
}

