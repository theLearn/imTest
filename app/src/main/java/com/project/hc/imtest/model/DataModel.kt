package com.project.hc.imtest.model

import android.os.Parcel
import android.os.Parcelable

data class LoginInfo(
    var token: String,
    var userId: String,
    var hx_pwd: String,
    var nickname: String,
    var photo: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(token)
        writeString(userId)
        writeString(hx_pwd)
        writeString(nickname)
        writeString(photo)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LoginInfo> = object : Parcelable.Creator<LoginInfo> {
            override fun createFromParcel(source: Parcel): LoginInfo = LoginInfo(source)
            override fun newArray(size: Int): Array<LoginInfo?> = arrayOfNulls(size)
        }
    }
}

data class GroupInfo(
    var id: String,
    var gid: String,
    var name: String,
    var type: String,
    var status: String,
    var addTime: String,
    var minMoney: String,
    var maxMoney: String,
    var hb_number: String,
    var pic: String,
    var jl: String,
    var pl: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(gid)
        writeString(name)
        writeString(type)
        writeString(status)
        writeString(addTime)
        writeString(minMoney)
        writeString(maxMoney)
        writeString(hb_number)
        writeString(pic)
        writeString(jl)
        writeString(pl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GroupInfo> = object : Parcelable.Creator<GroupInfo> {
            override fun createFromParcel(source: Parcel): GroupInfo = GroupInfo(source)
            override fun newArray(size: Int): Array<GroupInfo?> = arrayOfNulls(size)
        }
    }
}

data class PointInfo(
    var points: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(points)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PointInfo> = object : Parcelable.Creator<PointInfo> {
            override fun createFromParcel(source: Parcel): PointInfo = PointInfo(source)
            override fun newArray(size: Int): Array<PointInfo?> = arrayOfNulls(size)
        }
    }
}

data class RecordList(
    var count: String,
    var today_profit: String,
    var month_profit: String,
    var data: MutableList<RedPackageInfo>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(RedPackageInfo.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(count)
        writeString(today_profit)
        writeString(month_profit)
        writeTypedList(data)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RecordList> = object : Parcelable.Creator<RecordList> {
            override fun createFromParcel(source: Parcel): RecordList = RecordList(source)
            override fun newArray(size: Int): Array<RecordList?> = arrayOfNulls(size)
        }
    }
}

data class RedPackageInfo(
    var type: String,
    var name: String,
    var mobile: String,
    var money: String,
    var status: String,
    var integral: String,
    var groupname: String,
    var gid: String,
    var add_time: String,
    var serial: String,
    var remark: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(type)
        writeString(name)
        writeString(mobile)
        writeString(money)
        writeString(status)
        writeString(integral)
        writeString(groupname)
        writeString(gid)
        writeString(add_time)
        writeString(serial)
        writeString(remark)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedPackageInfo> = object : Parcelable.Creator<RedPackageInfo> {
            override fun createFromParcel(source: Parcel): RedPackageInfo = RedPackageInfo(source)
            override fun newArray(size: Int): Array<RedPackageInfo?> = arrayOfNulls(size)
        }
    }
}

data class RedPackageDetailInfo(
    var url: String,
    var name: String,
    var amount: String,
    var time: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(url)
        writeString(name)
        writeString(amount)
        writeString(time)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedPackageDetailInfo> = object : Parcelable.Creator<RedPackageDetailInfo> {
            override fun createFromParcel(source: Parcel): RedPackageDetailInfo = RedPackageDetailInfo(source)
            override fun newArray(size: Int): Array<RedPackageDetailInfo?> = arrayOfNulls(size)
        }
    }
}

data class MsgInfo(
    var title: String,
    var add_time: String,
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
        writeString(add_time)
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
