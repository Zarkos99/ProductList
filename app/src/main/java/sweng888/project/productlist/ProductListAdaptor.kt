package sweng888.project.productlist

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductListAdaptor(private val context: Context, private val products: List<Product>) :
    RecyclerView.Adapter<ProductListAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create new view with UI of weather item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.product_name_text_view.text = product.name
        holder.product_price_text_view.text = "$" + product.price.toString()
        holder.product_seller_text_view.text = product.seller
        holder.product_image_view.setImageDrawable(getImage(product.picture))
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val product_name_text_view: TextView
        val product_price_text_view: TextView
        val product_seller_text_view: TextView
        val product_image_view: ImageView

        init {
            product_name_text_view = view.findViewById(R.id.textview_product_name)
            product_price_text_view = view.findViewById(R.id.textview_price)
            product_seller_text_view = view.findViewById(R.id.textview_seller)
            product_image_view = view.findViewById(R.id.product_image_view)
        }
    }
}
