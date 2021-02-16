package com.github.dreipol.dreidroid.serialization

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Enables processing hooks while parsing with GSON.
 *
 * Example: Extracting logoUrl and mailText from a wrapped "header" JSON attribute during deserialization
 * ```
 * open class MyModel {
 *     var id: Int = 0
 *     var articles: RealmList<ArticlePreview> = RealmList()
 *     var logoUrl: String? = ""
 *     var mailText: String? = ""
 *
 *     class Transformer : JsonTransformTypeAdapterFactory<MyModel>(MyModel::class.java) {
 *         override fun afterRead(tree: JsonElement?) {
 *             if (tree == null) {
 *                 return
 *             }
 *             val jsonObject = tree.asJsonObject
 *             jsonObject["header"]?.let { header ->
 *                 val headerObject = header.asJsonObject
 *                 headerObject?.let {
 *                     jsonObject.add("logoUrl", headerObject["logo"])
 *                     jsonObject.add("mailText", headerObject["mailText"])
 *                 }
 *             }
 *         }
 *     }
 * }
 * ```
 * The TypeAdapter needs to be added to the gson deserializer during initialization:
 * ```
 * private var gson: Gson = GsonBuilder()
 *     .registerTypeAdapterFactory(MyModel.Transformer())
 *     .create()
 * ```
 */
public abstract class JsonTransformTypeAdapterFactory<C>(private val mClass: Class<C>) : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        return if (type.rawType == mClass) {
            (wrappedAdapter(gson, type as TypeToken<C>) as TypeAdapter<T>)
        } else {
            null
        }
    }

    public open fun afterDeSerialization(c: C) {}
    public open fun afterRead(tree: JsonElement?) {}
    public open fun hasObject(jsonObject: JsonObject, field: String?): Boolean {
        return jsonObject.has(field) && jsonObject[field].isJsonObject
    }

    public open fun beforeSerialization(value: C): C {
        return value
    }

    public open fun beforeWrite(element: JsonElement) {}
    private fun wrappedAdapter(gson: Gson, type: TypeToken<C>): TypeAdapter<C> {
        val delegate = gson.getDelegateAdapter(this, type)
        val elementAdapter = gson.getAdapter(JsonElement::class.java)
        return object : TypeAdapter<C>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: C) {
                val newValue = beforeSerialization(value)
                val element = delegate.toJsonTree(newValue)
                beforeWrite(element)
                elementAdapter.write(out, element)
            }

            @Throws(IOException::class)
            override fun read(input: JsonReader): C {
                val tree = elementAdapter.read(input)
                afterRead(tree)
                val c = delegate.fromJsonTree(tree)
                afterDeSerialization(c)
                return c
            }
        }
    }
}