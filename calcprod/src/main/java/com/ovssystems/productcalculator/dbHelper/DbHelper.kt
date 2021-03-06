package com.ovssystems.productcalculator.dbHelper

import android.content.ContentValues
import android.content.Context
import com.ovssystems.productcalculator.data.BasketTableEntry
import com.ovssystems.productcalculator.data.ProductTableEntry
import com.ovssystems.productcalculator.model.BasketProductModel
import com.ovssystems.productcalculator.model.ProductModel
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import java.util.*

open class DbHelper   //    SQLiteDatabase db;
    (context: Context?) : SQLiteAssetHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    private fun getBasketList(purchased: Boolean): List<BasketProductModel> {
        val result: MutableList<BasketProductModel> = ArrayList<BasketProductModel>()
        val db = writableDatabase
        val projection = arrayOf<String>(
            BasketTableEntry.TABLE_NAME.toString() + "." + BasketTableEntry._ID + " as " + BasketTableEntry._ID,
//            ProductTableEntry.TABLE_NAME.toString() + "." + ProductTableEntry.COLUMN_NAME + " as " + ProductTableEntry.COLUMN_NAME,
            BasketTableEntry.TABLE_NAME.toString() + "." + BasketTableEntry.COLUMN_ITEM_NAME + " as " + BasketTableEntry.COLUMN_ITEM_NAME,
            BasketTableEntry.TABLE_NAME.toString() + "." + BasketTableEntry.COLUMN_GROUP_ID + " as " + BasketTableEntry.COLUMN_GROUP_ID,
            BasketTableEntry.TABLE_NAME.toString() + "." + BasketTableEntry.COLUMN_NOTE + " as " + BasketTableEntry.COLUMN_NOTE,
            BasketTableEntry.TABLE_NAME.toString() + "." + BasketTableEntry.COLUMN_COUNT + " as " + BasketTableEntry.COLUMN_COUNT,
            BasketTableEntry.TABLE_NAME.toString() + "." + BasketTableEntry.COLUMN_ITEM_ID + " as " + BasketTableEntry.COLUMN_ITEM_ID,
            BasketTableEntry.TABLE_NAME.toString() + "." + BasketTableEntry.COLUMN_DATE_PURCHASED + " as " + BasketTableEntry.COLUMN_DATE_PURCHASED
        )
        val selection: String =
            BasketTableEntry.COLUMN_DATE_PURCHASED.toString() + (if (purchased) " not " else " is ") + " null  "
        val cursor = db.query(
            "basket",  // таблица
            projection,  // столбцы
            selection,  // столбцы для условия WHERE
            null,  // значения для условия WHERE
            null,  // Don't group the rows
            null,  // Don't filter by row groups
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val p = BasketProductModel()

                p.name = cursor.getString(cursor.getColumnIndex(BasketTableEntry.COLUMN_ITEM_NAME))
                p._id = cursor.getLong(cursor.getColumnIndex(BasketTableEntry._ID))
                p.date_purchased =  cursor.getInt(cursor.getColumnIndex(BasketTableEntry.COLUMN_DATE_PURCHASED))
                p.item_id = cursor.getInt(cursor.getColumnIndex(BasketTableEntry.COLUMN_ITEM_ID))
                p.count = cursor.getInt(cursor.getColumnIndex(BasketTableEntry.COLUMN_COUNT))
                val ind = cursor.getColumnIndex(BasketTableEntry.COLUMN_NOTE)
                p.note = cursor.getString(ind) ?: ""
                result.add(p)
            } while (cursor.moveToNext())
        }
        return result
    }//                p.setGroupId(cursor.getInt(cursor.getColumnIndex(ToDoListEntry.)));// таблица


    val basketList: List<BasketProductModel>
        get() {
             return getBasketList(false)
        }
    val basketArchiveList: List<BasketProductModel>
        get() {
            return getBasketList(true)
        }

    // Зададим условие для выборки - список столбцов
    val products: List<ProductModel>
        get() {
            val result: MutableList<ProductModel> = ArrayList<ProductModel>()
            val db = writableDatabase
            //           val qb = SQLiteQueryBuilder()
            // Зададим условие для выборки - список столбцов
            val projection = arrayOf<String>(
                ProductTableEntry._ID,
                ProductTableEntry.COLUMN_NAME,
                ProductTableEntry.COLUMN_GROUP_ID
            )

            // qb.query(db,projection,null,null,null,null,null);
            // Делаем запрос
            val cursor = db.query(
                ProductTableEntry.TABLE_NAME,  // таблица
                projection,  // столбцы
                null,  // столбцы для условия WHERE
                null,  // значения для условия WHERE
                null,  // Don't group the rows
                null,  // Don't filter by row groups
                null
            )
            if (cursor.moveToFirst()) {
                do {
                    val name =
                        cursor.getString(cursor.getColumnIndex(ProductTableEntry.COLUMN_NAME))
                    val id = (cursor.getLong(cursor.getColumnIndex(ProductTableEntry._ID)))
                    val groupId =
                        cursor.getInt(cursor.getColumnIndex(ProductTableEntry.COLUMN_GROUP_ID));
                    result.add(ProductModel(name, groupId, id))
                } while (cursor.moveToNext())
            }
            return result
        }

    fun productByName(name: String): ProductModel {
        val result: ProductModel = ProductModel(name)
        val db = writableDatabase
        //           val qb = SQLiteQueryBuilder()
        // Зададим условие для выборки - список столбцов
        val projection = arrayOf<String>(
            ProductTableEntry._ID,
            ProductTableEntry.COLUMN_NAME,
            ProductTableEntry.COLUMN_GROUP_ID
        )
        val whereProjection = arrayOf<String>(
            ProductTableEntry.COLUMN_NAME,
        )
        // Делаем запрос
        val cursor = db.query(
            ProductTableEntry.TABLE_NAME,  // таблица
            projection,  // столбцы
            "lower( ${ProductTableEntry.COLUMN_NAME}) like '%?%'",  // столбцы для условия WHERE
            arrayOf<String>(name),  // значения для условия WHERE
            null,  // Don't group the rows
            null,  // Don't filter by row groups
            null
        )
        if (cursor.count < 1) {
            val contentValues = ContentValues();
            contentValues.put(ProductTableEntry.COLUMN_NAME, name)
            result.id = db.insert(ProductTableEntry.TABLE_NAME, null, contentValues)
        }else{
            cursor.moveToFirst()
            result.id = (cursor.getLong(cursor.getColumnIndex(ProductTableEntry._ID)))
        }
        return result
    }


    /**
     * Заносим элементы в список покупок по ИД
     */
    fun addProductsToBasket(products: List<ProductModel>) {
        val db = writableDatabase
        products.forEach {
            val contentValues = ContentValues();
            contentValues.put(BasketTableEntry.COLUMN_GROUP_ID, it.groupId)
            contentValues.put(BasketTableEntry.COLUMN_ITEM_NAME, it.name)
            db.insert(BasketTableEntry.TABLE_NAME, null, contentValues)
        }
    }

    fun updatePurchased(_id: Long, data : Int) {
        val db = writableDatabase
        val contentValues = ContentValues();
        contentValues.put(BasketTableEntry.COLUMN_DATE_PURCHASED,data);
        db.update(BasketTableEntry.TABLE_NAME,contentValues,"${BasketTableEntry._ID}=$_id", null);
    }


    companion object {
        val LOG_TAG = DbHelper::class.java.simpleName

        /**
         * Имя файла базы данных
         */
        private const val DATABASE_NAME = "calcprod.db"

        /**
         * Версия базы данных. При изменении схемы увеличить на единицу
         */
        private const val DATABASE_VERSION = 1
    }
}