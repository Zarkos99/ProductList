package sweng888.project.productlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class for a single product item
 */
@Parcelize
data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val seller: String = "",
    val price: Double = 0.0,
    val picture: String
) :
    Parcelable {
}