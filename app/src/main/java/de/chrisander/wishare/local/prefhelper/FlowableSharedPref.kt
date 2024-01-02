package de.chrisander.wishare.local.prefhelper

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun SharedPreferences.notifyOnChange(key: String): Flow<Unit> = callbackFlow {
    val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, updatedKey ->
            if (key == updatedKey) trySend(Unit)
        }

    registerOnSharedPreferenceChangeListener(listener)
    trySend(Unit)

    awaitClose {
        unregisterOnSharedPreferenceChangeListener(listener)
    }
}

fun SharedPreferences.flowChangesInt(key: String, defValue: Int = 0) = notifyOnChange(key).map {
    getInt(
        key,
        defValue
    )
}

fun SharedPreferences.flowChangesLong(key: String, defValue: Long = 0) = notifyOnChange(key).map {
    getLong(
        key,
        defValue
    )
}

fun SharedPreferences.flowChangesBoolean(key: String, defValue: Boolean = false) = notifyOnChange(
    key
).map { getBoolean(key, defValue) }

fun SharedPreferences.flowChangesFloat(key: String, defValue: Float = 0f) =
    notifyOnChange(key).map {
        getFloat(
            key,
            defValue
        )
    }

fun SharedPreferences.flowChangesString(key: String, defValue: String? = null) =
    notifyOnChange(key).map {
        getString(
            key,
            defValue
        )
    }

fun SharedPreferences.flowChangesStringSet(
    key: String,
    defValue: Set<String>? = null
): Flow<Set<String>?> = notifyOnChange(
    key
).map { getStringSet(key, defValue) }

fun <T> SharedPreferences.flowChangesObjectList(
    key: String,
    defValue: List<T>,
    clazz: Class<T>,
    moshi: Moshi
) = notifyOnChange(
    key
).map { getObjectList(key, defValue, clazz, moshi) }

inline fun <reified T> SharedPreferences.flowChangesObject(
    key: String,
    defValue: T,
    clazz: Class<T>,
    moshi: Moshi
) = notifyOnChange(key).map { getObject(key, defValue, clazz, moshi) }

inline fun <reified T> SharedPreferences.flowChangesNullableObject(
    key: String,
    defValue: T?,
    clazz: Class<T>,
    moshi: Moshi
) = notifyOnChange(key).map { getNullableObject(key, defValue, clazz, moshi) }

class SharedPreferenceProperty<T>(
    private val prefs: SharedPreferences,
    private val defValue: T,
    private val getVal: SharedPreferences.(String, T) -> T,
    private val putVal: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor,
    private val customName: String? = null
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return prefs.getVal(customName ?: property.name, defValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        prefs.edit(commit = true) {
            putVal(customName ?: property.name, value)
        }
    }
}

class SharedPreferenceObjectProperty<T>(
    private val prefs: SharedPreferences,
    private val defValue: T,
    private val clazz: Class<T>,
    private val getVal: SharedPreferences.(String, T, Class<T>, Moshi) -> T,
    private val putVal: SharedPreferences.Editor.(String, T, Class<T>, Moshi) -> SharedPreferences.Editor,
    private val customName: String? = null,
    private val moshi: Moshi = Moshi.Builder().build()
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return prefs.getVal(customName ?: property.name, defValue, clazz, moshi)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        prefs.edit(commit = true) {
            putVal(customName ?: property.name, value, clazz, moshi)
        }
    }
}

class SharedPreferenceNullableProperty<T>(
    private val prefs: SharedPreferences,
    private val defValue: T?,
    private val clazz: Class<T>,
    private val getVal: SharedPreferences.(String, T?, Class<T>, Moshi) -> T?,
    private val putVal: SharedPreferences.Editor.(String, T?, Class<T>, Moshi) -> SharedPreferences.Editor,
    private val customName: String? = null,
    private val moshi: Moshi = Moshi.Builder().build()
) : ReadWriteProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return prefs.getVal(customName ?: property.name, defValue, clazz, moshi)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        prefs.edit(commit = true) {
            putVal(customName ?: property.name, value, clazz, moshi)
        }
    }
}


class SharedPreferenceListProperty<T>(
    private val prefs: SharedPreferences,
    private val defValue: List<T>,
    private val clazz: Class<T>,
    private val getVal: SharedPreferences.(String, List<T>, Class<T>, Moshi) -> List<T>,
    private val putVal: SharedPreferences.Editor.(String, List<T>, Class<T>, Moshi) -> SharedPreferences.Editor,
    private val customName: String? = null,
    private val moshi: Moshi = Moshi.Builder().build()
) : ReadWriteProperty<Any, List<T>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): List<T> {
        return prefs.getVal(customName ?: property.name, defValue, clazz, moshi)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: List<T>) {
        prefs.edit(commit = true) {
            putVal(customName ?: property.name, value, clazz, moshi)
        }
    }
}

class SharedPreferenceObserverProperty<T>(
    private val prefs: SharedPreferences,
    private val defValue: T,
    private val flowVal: SharedPreferences.(String, T) -> Flow<T>,
    private val customName: String? = null
) : ReadOnlyProperty<Any, Flow<T>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Flow<T> {
        return prefs.flowVal(customName ?: property.name, defValue)
    }
}

fun SharedPreferences.intProp(defValue: Int = 0, customName: String? = null) =
    SharedPreferenceProperty(
        this,
        defValue,
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt,
        customName
    )

fun SharedPreferences.longProp(defValue: Long = 0, customName: String? = null) =
    SharedPreferenceProperty(
        this,
        defValue,
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong,
        customName
    )

fun SharedPreferences.booleanProp(defValue: Boolean = false, customName: String? = null) =
    SharedPreferenceProperty(
        this,
        defValue,
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean,
        customName
    )

fun SharedPreferences.floatProp(defValue: Float = 0f, customName: String? = null) =
    SharedPreferenceProperty(
        this,
        defValue,
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat,
        customName
    )

fun SharedPreferences.stringProp(defValue: String? = null, customName: String? = null) =
    SharedPreferenceProperty(
        this,
        defValue,
        SharedPreferences::getString,
        SharedPreferences.Editor::putString,
        customName
    )

fun SharedPreferences.stringSetProp(
    defValue: Set<String>? = null,
    customName: String? = null
): SharedPreferenceProperty<Set<String>?> = SharedPreferenceProperty(
    this,
    defValue,
    SharedPreferences::getStringSet,
    SharedPreferences.Editor::putStringSet,
    customName
)

fun <T> SharedPreferences.objectListProp(
    defValue: List<T> = emptyList(),
    customName: String? = null,
    clazz: Class<T>,
    moshi: Moshi = Moshi.Builder().build()
): SharedPreferenceListProperty<T> {
    return SharedPreferenceListProperty(
        this,
        defValue,
        clazz,
        SharedPreferences::getObjectList,
        SharedPreferences.Editor::setObjectList,
        customName,
        moshi
    )
}

fun <T> SharedPreferences.getObjectList(
    customName: String,
    defValue: List<T> = emptyList(),
    clazz: Class<T>,
    moshi: Moshi = Moshi.Builder().build()
): List<T> {
    val jsonString = this.getString(customName, "") ?: return defValue
    return try {
        val type = Types.newParameterizedType(List::class.java, clazz)
        val adapter = moshi.adapter<List<T>>(type)
        adapter.fromJson(jsonString) as List<T>
    } catch (e: Exception) {
        Timber.d("JSON parsing failed with $e\nNew return value: $defValue")
        defValue
    }
}

fun <T> SharedPreferences.Editor.setObjectList(
    customName: String,
    objects: List<T>,
    clazz: Class<T>,
    moshi: Moshi = Moshi.Builder().build()
): SharedPreferences.Editor {
    val type = Types.newParameterizedType(List::class.java, clazz)
    val adapter = moshi.adapter<List<T>>(type)
    val jsonString = adapter.toJson(objects)
    Timber.d("Setting list: $objects, generated json string: $jsonString")
    return this.putString(customName, jsonString)
}

inline fun <reified T : Any?> SharedPreferences.objectProp(
    defValue: T,
    customName: String? = null,
    clazz: Class<T>,
    moshi: Moshi = Moshi.Builder().build()
): SharedPreferenceObjectProperty<T> {
    return SharedPreferenceObjectProperty(
        this,
        defValue,
        clazz,
        SharedPreferences::getObject,
        SharedPreferences.Editor::setObject,
        customName,
        moshi
    )
}

inline fun <reified T : Any?> SharedPreferences.getObject(
    customName: String,
    defValue: T,
    clazz: Class<T>,
    moshi: Moshi = Moshi.Builder().build()
): T {
    val jsonString = this.getString(customName, "") ?: return defValue
    return try {
        val type = Types.newParameterizedType(clazz)
        val adapter = moshi.adapter<T>(type)
        adapter.fromJson(jsonString) ?: defValue
    } catch (e: Exception) {
        defValue
    }
}

inline fun <reified T> SharedPreferences.objectNullableProp(
    defValue: T?,
    clazz: Class<T>,
    customName: String? = null,
    moshi: Moshi = Moshi.Builder().build()
): SharedPreferenceNullableProperty<T> {
    return SharedPreferenceNullableProperty(
        this,
        defValue,
        clazz,
        SharedPreferences::getNullableObject,
        SharedPreferences.Editor::setObject,
        customName,
        moshi
    )
}

inline fun <reified T> SharedPreferences.getNullableObject(
    customName: String,
    defValue: T?,
    clazz: Class<T>,
    moshi: Moshi = Moshi.Builder().build()
): T? {
    val jsonString = this.getString(customName, "") ?: return defValue
    try {
        val adapter = try {
            val type = Types.newParameterizedType(clazz)
            moshi.adapter<T>(type)
        } catch (e: Exception){
            moshi.adapter(clazz)
        }
        return adapter.fromJson(jsonString) ?: defValue
    } catch (e: Exception) {
        Timber.e(e)
        return defValue
    }
}

inline fun <reified T> SharedPreferences.Editor.setObject(
    customName: String,
    o: T?,
    clazz: Class<T>,
    moshi: Moshi = Moshi.Builder().build()
): SharedPreferences.Editor {
    val adapter = try {
        val type = Types.newParameterizedType(clazz)
        moshi.adapter<T>(type)
    } catch (e: Exception){
        moshi.adapter(clazz)
    }
    val jsonString = adapter.toJson(o)
    return this.putString(customName, jsonString)
}

fun SharedPreferences.observeIntProp(defValue: Int = 0, customName: String? = null) =
    SharedPreferenceObserverProperty(
        this,
        defValue,
        SharedPreferences::flowChangesInt,
        customName
    )

fun SharedPreferences.observeLongProp(defValue: Long = 0, customName: String? = null) =
    SharedPreferenceObserverProperty(
        this,
        defValue,
        SharedPreferences::flowChangesLong,
        customName
    )

fun SharedPreferences.observeBooleanProp(defValue: Boolean = false, customName: String? = null) =
    SharedPreferenceObserverProperty(
        this,
        defValue,
        SharedPreferences::flowChangesBoolean,
        customName
    )

fun SharedPreferences.observeFloatProp(defValue: Float = 0f, customName: String? = null) =
    SharedPreferenceObserverProperty(
        this,
        defValue,
        SharedPreferences::flowChangesFloat,
        customName
    )

fun SharedPreferences.observeStringProp(defValue: String? = null, customName: String? = null) =
    SharedPreferenceObserverProperty(
        this,
        defValue,
        SharedPreferences::flowChangesString,
        customName
    )

fun SharedPreferences.observeStringSetProp(
    defValue: Set<String>? = null,
    customName: String? = null
) = SharedPreferenceObserverProperty(
    this,
    defValue,
    SharedPreferences::flowChangesStringSet,
    customName
)