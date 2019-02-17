package com.project.hc.imtest.model

import android.os.Parcel
import android.os.Parcelable

data class RedPackageInfo(
    var type: String,
    var amount: String,
    var time: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(type)
        writeString(amount)
        writeString(time)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedPackageInfo> = object : Parcelable.Creator<RedPackageInfo> {
            override fun createFromParcel(source: Parcel): RedPackageInfo = RedPackageInfo(source)
            override fun newArray(size: Int): Array<RedPackageInfo?> = arrayOfNulls(size)
        }
    }
}

data class MsgInfo(
    var title: String,
    var time: String,
    var content: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(time)
        writeString(content)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MsgInfo> = object : Parcelable.Creator<MsgInfo> {
            override fun createFromParcel(source: Parcel): MsgInfo = MsgInfo(source)
            override fun newArray(size: Int): Array<MsgInfo?> = arrayOfNulls(size)
        }
    }
}
