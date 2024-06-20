package sweng888.project.productlist

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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