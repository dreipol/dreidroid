package com.github.dreipol.dreidroid.components

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * List Adapter which shows list grouped elements
 *
 * DT: specifies the data type
 * HT: specifies the header type
 * GT: specifies the type of the property to group by
 * HBT: specifies the binding-type for the group-header elements
 * DBT: specifies the binding-type for the data elements
 */
abstract class GroupedListAdapter<DT : Any, HT : Any, GT : Comparable<GT>, HBT : ViewDataBinding, DBT : ViewDataBinding> :
    RecyclerView.Adapter<GroupedListAdapter.GroupedListViewHolder>() {

    class GroupedListViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    class GroupedDataItem<DT : Any>(val type: Type, val data: DT) {
        enum class Type(val viewType: Int) {
            DATA_ITEM(1),
            HEADER_ITEM(2);

            companion object {
                private val map = values().associateBy(Type::viewType)
                fun fromInt(type: Int) = map[type]
            }
        }
    }

    private var groupedData: MutableList<GroupedDataItem<Any>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupedListViewHolder {
        return when (GroupedDataItem.Type.fromInt(viewType)) {
            GroupedDataItem.Type.DATA_ITEM -> GroupedListViewHolder(createDataItemBinding(parent))
            GroupedDataItem.Type.HEADER_ITEM -> GroupedListViewHolder(createHeaderBinding(parent))
            null -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        return groupedData.size
    }

    override fun getItemViewType(position: Int): Int {
        return groupedData[position].type.viewType
    }

    fun buildGroupedData() {
        groupedData.clear()
        var lastGroup: GT? = null
        getData().sortedWith(getSortComperator()).forEach { dataModel ->
            val groupByProperty = getGroupByProperty(dataModel)
            if (lastGroup != groupByProperty) {
                lastGroup = groupByProperty
                groupedData.add(GroupedDataItem(GroupedDataItem.Type.HEADER_ITEM, getHeaderModel(dataModel)))
            }
            groupedData.add(GroupedDataItem(GroupedDataItem.Type.DATA_ITEM, dataModel))
        }
    }

    override fun onBindViewHolder(holder: GroupedListViewHolder, position: Int) {
        val groupedDataItem = groupedData[position]
        when (groupedDataItem.type) {
            GroupedDataItem.Type.DATA_ITEM -> configureDataItemBinding(holder.binding as DBT, groupedDataItem.data as DT)
            GroupedDataItem.Type.HEADER_ITEM -> configureHeaderBinding(holder.binding as HBT, groupedDataItem.data as HT)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        buildGroupedData()
    }

    protected abstract fun getSortComperator(): Comparator<DT>

    protected abstract fun getData(): List<DT>

    protected abstract fun getGroupByProperty(dataModel: DT): GT

    protected abstract fun getHeaderModel(dataModel: DT): HT

    protected abstract fun createHeaderBinding(parent: ViewGroup): HBT

    protected abstract fun createDataItemBinding(parent: ViewGroup): DBT

    protected abstract fun configureHeaderBinding(binding: HBT, model: HT)

    protected abstract fun configureDataItemBinding(binding: DBT, model: DT)
}