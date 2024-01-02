package de.chrisander.wishare.local.prefhelper

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.local.moshiadapters.MoshiUUIDAdapter
import de.chrisander.wishare.local.prefhelper.PreferenceConstants.PREFERENCES_NAME
import de.chrisander.wishare.local.prefhelper.PreferenceConstants.SHARED_PREFS_KEY_APP_OWNER
import de.chrisander.wishare.local.prefhelper.PreferenceConstants.SHARED_PREFS_KEY_FAMILIES
import de.chrisander.wishare.local.prefhelper.PreferenceConstants.SHARED_PREFS_KEY_MEMBERS
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class PreferenceHelper(
    private val c: Context
) : IPreferenceHelper {

    private val moshi: Moshi = Moshi.Builder()
        .add(MoshiUUIDAdapter())
        .add(
            PolymorphicJsonAdapterFactory.of(WishState::class.java, "WishState")
                .withSubtype(WishState.Open::class.java, "Open")
                .withSubtype(WishState.Reserved::class.java, "Reserved")
                .withSubtype(WishState.Bought::class.java, "Bought")
                .withSubtype(WishState.HandedOver::class.java, "HandedOver")
        )
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private fun Context.confStore() = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override var families: List<Family> by c.confStore().objectListProp(
        customName = SHARED_PREFS_KEY_FAMILIES,
        clazz = Family::class.java,
        moshi = moshi
    )
    override val familiesFlow: Flow<List<Family>> = c.confStore().flowChangesObjectList(
        key = SHARED_PREFS_KEY_FAMILIES,
        defValue = emptyList(),
        clazz = Family::class.java,
        moshi = moshi
    )

    override var members: List<FamilyMember> by c.confStore().objectListProp(
        defValue = listOf(),
        customName = SHARED_PREFS_KEY_MEMBERS,
        clazz = FamilyMember::class.java,
        moshi = moshi
    )
    override val membersFlow: Flow<List<FamilyMember>> = c.confStore().flowChangesObjectList(
        key = SHARED_PREFS_KEY_MEMBERS,
        defValue = listOf(),
        clazz = FamilyMember::class.java,
        moshi = moshi
    )

    override var appOwnerId: UUID? by c.confStore().objectNullableProp(
        defValue = null,
        customName = SHARED_PREFS_KEY_APP_OWNER,
        clazz = UUID::class.java,
        moshi = moshi
    )
    override val appOwnerIdFlow: Flow<UUID?> = c.confStore().flowChangesNullableObject(
        key = SHARED_PREFS_KEY_APP_OWNER,
        defValue = null,
        clazz = UUID::class.java,
        moshi = moshi
    )
}