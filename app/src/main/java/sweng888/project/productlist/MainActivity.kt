package sweng888.project.productlist

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import sweng888.project.productlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val products_recycler_view = findViewById<RecyclerView>(R.id.products_recycler_view)
        val product_database_helper = ProductDatabaseHelper(this)

        this.deleteDatabase(this.getString(R.string.database_name))
        var products = product_database_helper.getAllProducts()
        if (products.isEmpty()) {
            addProductsToDatabase(product_database_helper)
            products = product_database_helper.getAllProducts()
        }

        val product_list_adaptor = ProductListAdaptor(this, products)
        products_recycler_view.adapter = product_list_adaptor
        val layout_manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        products_recycler_view.layoutManager = layout_manager

        // Listen to the email button's clicks
        binding.confirmSelectionButton.setOnClickListener { view ->
            val selected_products = product_list_adaptor.getSelectedProducts()
            if (selected_products.size < 3) {
                Snackbar.make(
                    view,
                    "Must select " + (3 - selected_products.size) + " more " + (if ((3 - selected_products.size) == 1) "product" else "products"),
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Action", null)
                    .setAnchorView(R.id.confirm_selection_button)
                    .setBackgroundTint(getColor(R.color.persian_green))
                    .show()
                return@setOnClickListener
            }

            val intent = Intent(this@MainActivity, EmailConfirmationActivity::class.java)
            intent.putParcelableArrayListExtra(
                "selected_products",
                selected_products
            )
            startActivity(intent)
        }
    }

    fun addProductsToDatabase(product_database_helper: ProductDatabaseHelper) {
        product_database_helper.populateProductDatabase(
            "grn", "Green", "TerraInc", 99.99
        )
        product_database_helper.populateProductDatabase(
            "blu", "Blue", "TerraInc", 39.99
        )
        product_database_helper.populateProductDatabase(
            "ylw", "Yellow", "TerraInc", 19.99
        )
        product_database_helper.populateProductDatabase(
            "prp", "Purple", "TerraInc", 29.99
        )
        product_database_helper.populateProductDatabase(
            "red", "Red", "TerraInc", 25.99
        )
        product_database_helper.populateProductDatabase(
            "orn", "Orange", "TerraInc", 32.99
        )
    }
}