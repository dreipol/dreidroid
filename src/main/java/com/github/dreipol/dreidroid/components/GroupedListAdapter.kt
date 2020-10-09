package com.github.dreipol.dreidroid.components

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * List Adapter which shows list grouped elements
 *
 * Data: specifies the data type
 * Header: specifies the header type
 * GroupBy: specifies the type of the property to group by
 * HeaderBinding: specifies the binding-type for the group-header elements
 * DataBinding: specifies the binding-type for the data elements
 */
abstract class GroupedListAdapter<Data : Any, Header : Any, GroupBy : Comparable<GroupBy>, HeaderBinding : ViewBinding, DataBinding : ViewBinding> :
    RecyclerView.Adapter<GroupedListAdapter.GroupedListViewHolder>() {

    class GroupedListViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    class GroupedDataItem<Data : Any>(val type: Type, val data: Data) {
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
        var lastGroup: GroupBy? = null
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
            GroupedDataItem.Type.DATA_ITEM -> configureDataItemBinding(holder.binding as DataBinding, groupedDataItem.data as Data)
            GroupedDataItem.Type.HEADER_ITEM -> configureHeaderBinding(holder.binding as HeaderBinding, groupedDataItem.data as Header)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        buildGroupedData()
    }

    protected abstract fun getSortComperator(): Comparator<Data>

    protected abstract fun getData(): List<Data>

    protected abstract fun getGroupByProperty(dataModel: Data): GroupBy

    protected abstract fun getHeaderModel(dataModel: Data): Header

    protected abstract fun createHeaderBinding(parent: ViewGroup): HeaderBinding

    protected abstract fun createDataItemBinding(parent: ViewGroup): DataBinding

    protected abstract fun configureHeaderBinding(binding: HeaderBinding, model: Header)

    protected abstract fun configureDataItemBinding(binding: DataBinding, model: Data)
}