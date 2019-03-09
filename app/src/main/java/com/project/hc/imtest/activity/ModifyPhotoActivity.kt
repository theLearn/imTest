package com.project.hc.imtest.activity

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseSubscriber
import com.example.hongcheng.data.retrofit.RetrofitClient
import com.example.hongcheng.data.retrofit.RetrofitManager
import com.project.hc.imtest.R
import com.project.hc.imtest.adapter.PhotoAdapter
import com.project.hc.imtest.api.ApiRetrofit
import com.project.hc.imtest.application.BaseApplication
import kotlinx.android.synthetic.main.body_photo_list.*


class ModifyPhotoActivity : AppCommonActivity(), BaseListAdapter.OnItemClickListener {

    private lateinit var mAdapter: PhotoAdapter
    private var dataList : MutableList<String> = arrayListOf()

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.title_modify_photo
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_photo_list
    }

    override fun initBodyView(view: View) {
        rv_photo_list.layoutManager = StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL)
        rv_photo_list.itemAnimator = DefaultItemAnimator()
        mAdapter = PhotoAdapter()
        mAdapter.onItemClickListener = this
        rv_photo_list.adapter = mAdapter
        getData()
    }

    private fun getData() {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<MutableList<String>>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .allPhotos, object : BaseSubscriber<MutableList<String>>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : MutableList<String>) {
                        operateLoadingDialog(false)
                        dataList.addAll(obj)
                        mAdapter.data = dataList
                        mAdapter.notifyDataSetChanged()
                    }
                }))
    }

    override fun onItemClick(position: Int) {
        operateLoadingDialog(true)
        compositeDisposable.add(
            RetrofitClient.getInstance().map<Any>(
                RetrofitManager.createRetrofit<ApiRetrofit>(BaseApplication.getInstance(), ApiRetrofit::class.java)
                    .changeInfo(BaseApplication.getInstance()?.loginInfo?.nickname, dataList[position]), object : BaseSubscriber<Any>() {
                    override fun onError(e: ActionException) {
                        operateLoadingDialog(false)
                        ToastUtils.show(BaseApplication.getInstance(), e.message)
                    }

                    override fun onBaseNext(obj : Any) {
                        operateLoadingDialog(false)
                        BaseApplication.getInstance()?.loginInfo?.photo = dataList[position]
                        ToastUtils.show(BaseApplication.getInstance(), "修改成功")
                        finish()
                    }
                }))
    }

    override fun onItemLongClick(position: Int) {
    }
}