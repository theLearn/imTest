package com.project.hc.imtest.model

import android.os.Parcel
import android.os.Parcelable

data class LoginInfo(
    var token: String = "",
    var mobile: String = "",
    var userId: String = "",
    var hx_pwd: String = "",
    var nickname: String = "",
    var photo: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(token)
        writeString(mobile)
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

data class SMSTime(var time: String = "") : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(time)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SMSTime> = object : Parcelable.Creator<SMSTime> {
            override fun createFromParcel(source: Parcel): SMSTime = SMSTime(source)
            override fun newArray(size: Int): Array<SMSTime?> = arrayOfNulls(size)
        }
    }
}

data class KFInfo(
    var mid: String = "",
    var nickname: String = "",
    var pwd: String = "",
    var name: String = "",
    var email: String = "",
    var mobile: String = "",
    var lucky: String = "",
    var birthday: String = "",
    var litpic: String = "",
    var province: String = "",
    var city: String = "",
    var cardid: String = "",
    var equipment_id: String = "",
    var points: String = "",
    var if_robot: String = "",
    var if_service: String = "",
    var status: String = "",
    var login_time: String = "",
    var add_time: String = "",
    var hx_pwd: String = "",
    var photo: String = ""
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
        writeString(mid)
        writeString(nickname)
        writeString(pwd)
        writeString(name)
        writeString(email)
        writeString(mobile)
        writeString(lucky)
        writeString(birthday)
        writeString(litpic)
        writeString(province)
        writeString(city)
        writeString(cardid)
        writeString(equipment_id)
        writeString(points)
        writeString(if_robot)
        writeString(if_service)
        writeString(status)
        writeString(login_time)
        writeString(add_time)
        writeString(hx_pwd)
        writeString(photo)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<KFInfo> = object : Parcelable.Creator<KFInfo> {
            override fun createFromParcel(source: Parcel): KFInfo = KFInfo(source)
            override fun newArray(size: Int): Array<KFInfo?> = arrayOfNulls(size)
        }
    }
}

data class GroupInfo(
    var id: String = "",
    var gid: String = "",
    var name: String = "",
    var type: String = "",
    var status: String = "",
    var addTime: String = "",
    var minMoney: String = "",
    var maxMoney: String = "",
    var hb_number: String = "",
    var pic: String = "",
    var jl: String = "",
    var pl: String = ""
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

data class MsgInfo(
    var title: String = "",
    var add_time: String = "",
    var content: String = ""
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

data class PointInfo(
    var points: String = ""
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
    var count: String = "",
    var today_profit: String = "",
    var month_profit: String = "",
    var data: MutableList<RedPackageInfo> = arrayListOf()
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
    var type: String = "",
    var name: String = "",
    var mobile: String = "",
    var money: String = "",
    var status: String = "",
    var integral: String = "",
    var groupname: String = "",
    var gid: String = "",
    var add_time: String = "",
    var serial: String = "",
    var remark: String = ""
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

data class RedDetailInfo(
    var hb_id: String = "",
    var id: String = "",
    var mid: String = "",
    var money: String = "",
    var add_time: String = "",
    var litpic: String = "",
    var nickname: String = "",
    var if_do: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
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
        writeString(hb_id)
        writeString(id)
        writeString(mid)
        writeString(money)
        writeString(add_time)
        writeString(litpic)
        writeString(nickname)
        writeString(if_do)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedDetailInfo> = object : Parcelable.Creator<RedDetailInfo> {
            override fun createFromParcel(source: Parcel): RedDetailInfo = RedDetailInfo(source)
            override fun newArray(size: Int): Array<RedDetailInfo?> = arrayOfNulls(size)
        }
    }
}

data class RedPackageDetailInfo(
    var hb_data: RedDataInfo = RedDataInfo(),
    var data: MutableList<RedDetailInfo> = arrayListOf(),
    var type: String = "",
    var take: String = "",
    var takeMoney: String = "",
    var count: String = "",
    var if_end: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readParcelable<RedDataInfo>(RedDataInfo::class.java.classLoader),
        source.createTypedArrayList(RedDetailInfo.CREATOR),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(hb_data, 0)
        writeTypedList(data)
        writeString(type)
        writeString(take)
        writeString(takeMoney)
        writeString(count)
        writeString(if_end)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedPackageDetailInfo> = object : Parcelable.Creator<RedPackageDetailInfo> {
            override fun createFromParcel(source: Parcel): RedPackageDetailInfo = RedPackageDetailInfo(source)
            override fun newArray(size: Int): Array<RedPackageDetailInfo?> = arrayOfNulls(size)
        }
    }
}

data class RedDataInfo(
    var id: String = "",
    var name: String = "",
    var mobile: String = "",
    var money: String = "",
    var type: String = "",
    var status: String = "",
    var integral: String = "",
    var groupname: String = "",
    var gid: String = "",
    var add_time: String = "",
    var mid: String = "",
    var serial: String = "",
    var remark: String = "",
    var thunder: String = "",
    var hb_number: String = "",
    var end_time: String = "",
    var nickname: String = "",
    var pic: String = ""
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
        writeString(name)
        writeString(mobile)
        writeString(money)
        writeString(type)
        writeString(status)
        writeString(integral)
        writeString(groupname)
        writeString(gid)
        writeString(add_time)
        writeString(mid)
        writeString(serial)
        writeString(remark)
        writeString(thunder)
        writeString(hb_number)
        writeString(end_time)
        writeString(nickname)
        writeString(pic)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedDataInfo> = object : Parcelable.Creator<RedDataInfo> {
            override fun createFromParcel(source: Parcel): RedDataInfo = RedDataInfo(source)
            override fun newArray(size: Int): Array<RedDataInfo?> = arrayOfNulls(size)
        }
    }
}

data class SendRedInfo(
    var mobile: String = "",
    var nickname: String = "",
    var type: String = "",
    var photo: String = "",
    var redCode: String = ""
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
        writeString(mobile)
        writeString(nickname)
        writeString(type)
        writeString(photo)
        writeString(redCode)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SendRedInfo> = object : Parcelable.Creator<SendRedInfo> {
            override fun createFromParcel(source: Parcel): SendRedInfo = SendRedInfo(source)
            override fun newArray(size: Int): Array<SendRedInfo?> = arrayOfNulls(size)
        }
    }
}
