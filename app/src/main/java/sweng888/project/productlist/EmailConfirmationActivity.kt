package sweng888.project.productlist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import sweng888.project.productlist.databinding.ActivityMainBinding
import sweng888.project.productlist.databinding.EmailConfirmationLayoutBinding

class EmailConfirmationActivity : Activity() {

    private lateinit var binding: EmailConfirmationLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmailConfirmationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val products_recycler_view = findViewById<RecyclerView>(R.id.products_recycler_view)
        val selected_products = intent.extras?.getParcelableArrayList<Product>("selected_products")
        if (selected_products == null) {
            Toast.makeText(
                this,
                "No selected products to display. You shouldn't be here >:(",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val product_list_adaptor = ProductListAdaptor(this, selected_products)
        products_recycler_view.adapter = product_list_adaptor
        val layout_manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        products_recycler_view.layoutManager = layout_manager

        // Listen to the email button's clicks
        binding.emailSelectionButton.setOnClickListener { view ->
            sendProductsEmail(selected_products)
        }
    }


    fun sendProductsEmail(products: ArrayList<Product>) {
        val gson_pretty = GsonBuilder().setPrettyPrinting().create()

        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val email_intent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        email_intent.setDataAndType(Uri.parse("mailto:"), "text/plain")
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        email_intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("kzarvis15@gmail.com"))
        //put the Subject in the intent
        email_intent.putExtra(Intent.EXTRA_SUBJECT, "SWENG-888 Practice 3 Products")
        //put the message in the intent
        var json_products: String = ""
        for (product in products) {
            json_products += gson_pretty.toJson(product) + "\n"
        }
        email_intent.putExtra(Intent.EXTRA_TEXT, json_products)

        try {
            //start email intent
            startActivity(Intent.createChooser(email_intent, "Choose Email Client..."))
            Toast.makeText(this, "Email sent", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}