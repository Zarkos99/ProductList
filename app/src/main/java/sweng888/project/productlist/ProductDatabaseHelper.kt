package sweng888.project.productlist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ProductDatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(
        context, context.getString(R.string.database_name), null, 1
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        val query =
            ("CREATE TABLE " + table_name + " (" +
                    product_id_key + " TEXT," +
                    product_name_key + " TEXT," +
                    product_description_key + " TEXT," +
                    product_seller_key + " TEXT," +
                    product_price_key + " DOUBLE," +
                    product_picture_key + " TEXT" +
                    ")")
        db.execSQL(query)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $table_name")
        onCreate(db)
    }

    fun getAllProducts(): ArrayList<Product> {
        val db = readableDatabase
        val products_cursor = db.rawQuery("SELECT * FROM $table_name", null)
        val product_array_list: ArrayList<Product> = ArrayList()

        if (products_cursor.moveToFirst()) {
            do {
                products_cursor.getColumnIndexOrThrow(
                    product_id_key
                )

                // on below line we are adding the data from
                // cursor to our array list.
                product_array_list.add(
                    Product(
                        products_cursor.getString(
                            products_cursor.getColumnIndexOrThrow(
                                product_id_key
                            )
                        ),
                        products_cursor.getString(
                            products_cursor.getColumnIndexOrThrow(
                                product_name_key
                            )
                        ),
                        products_cursor.getString(
                            products_cursor.getColumnIndexOrThrow(
                                product_description_key
                            )
                        ),
                        products_cursor.getString(
                            products_cursor.getColumnIndexOrThrow(
                                product_seller_key
                            )
                        ),
                        products_cursor.getDouble(
                            products_cursor.getColumnIndexOrThrow(
                                product_price_key
                            )
                        ),
                        products_cursor.getString(
                            products_cursor.getColumnIndexOrThrow(
                                product_picture_key
                            )
                        )
                    )
                )
            } while (products_cursor.moveToNext())
        }

        products_cursor.close()
        db.close()
        return product_array_list
    }

    fun populateProductDatabase(
        product_id: String,
        product_name: String,
        product_seller: String,
        product_price: Double
    ) {
        val database = writableDatabase
        var values = ContentValues()

        values.put(product_id_key, product_id)
        values.put(product_name_key, product_name)
        values.put(
            product_description_key,
            "This is the color " + product_name.lowercase() + ". It is a very cool color."
        )
        values.put(product_seller_key, product_seller)
        values.put(product_price_key, product_price)
        values.put(product_picture_key, product_name.lowercase())
        database.insert(table_name, null, values)
        database.close()
    }


    companion object {
        val table_name = "Products"
        val product_id_key = "id"
        val product_name_key = "name"
        val product_description_key = "description"
        val product_seller_key = "seller"
        val product_price_key = "price"
        val product_picture_key = "picture"
    }

}