package sweng888.project.productlist

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.selects.select

class ProductListAdaptor(
    private val context: Context,
    private val products: List<Product>
) :
    RecyclerView.Adapter<ProductListAdaptor.ViewHolder>() {

    // Array of product names
    var selected_products = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create new view with UI of weather item
        val view = LayoutInflater.from(context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.product_name_text_view.text = product.name
        holder.product_price_text_view.text = "$" + product.price.toString()
        holder.product_seller_text_view.text = product.seller
        holder.product_image_view.setImageDrawable(getImage(product.picture))

        holder.setItemClickListener(object : ViewHolder.ItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                val product_checkbox = v as CheckBox
                val current_product = products[pos]

                if (product_checkbox.isChecked) {
                    selected_products.add(current_product)
                } else {
                    selected_products.remove(current_product)
                }
            }
        })
    }

    fun getSelectedProducts(): ArrayList<Product> {
        return selected_products
    }

    override fun getItemCount(): Int {
        return products.size
    }

    private fun getImage(ImageName: String?): Drawable {
        return context.resources.getDrawable(
            context.resources.getIdentifier(
                ImageName,
                "drawable",
                context.packageName
            )
        )
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val product_name_text_view: TextView = view.findViewById(R.id.textview_product_name)
        val product_price_text_view: TextView = view.findViewById(R.id.textview_price)
        val product_seller_text_view: TextView = view.findViewById(R.id.textview_seller)
        val product_image_view: ImageView = view.findViewById(R.id.product_image_view)
        val product_checkbox: CheckBox = view.findViewById(R.id.checkbox_button)

        lateinit var product_checkbox_click_listener: ItemClickListener

        init {
            product_checkbox.setOnClickListener(this)
        }

        fun setItemClickListener(ic: ItemClickListener) {
            this.product_checkbox_click_listener = ic
        }

        override fun onClick(v: View) {
            this.product_checkbox_click_listener.onItemClick(v, layoutPosition)
        }

        interface ItemClickListener {
            fun onItemClick(v: View, pos: Int)
        }
    }
}
