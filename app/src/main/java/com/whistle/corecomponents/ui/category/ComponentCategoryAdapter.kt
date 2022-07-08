package com.whistle.corecomponents.ui.category

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.whistle.corecomponents.R
import com.whistle.corecomponents.data.ComponentModel

class ComponentCategoryAdapter(@LayoutRes layoutId: Int, items: MutableList<ComponentModel>? = null) :
    BaseQuickAdapter<ComponentModel, BaseViewHolder>(layoutId, items) {
    override fun convert(holder: BaseViewHolder, item: ComponentModel) {
        holder.getView<SimpleDraweeView>(R.id.iv_component_icon).setActualImageResource(item.iconRes)
        holder.setText(R.id.tv_component_title, item.name)
    }
}