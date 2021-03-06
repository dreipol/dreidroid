package com.github.dreipol.dreidroid.components

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * List Adapter which shows grouped elements
 *
 * @param Data specifies the data type
 * @param Header specifies the header type
 * @param GroupBy specifies the type of the property to group by
 * @param HeaderBinding specifies the binding-type for the group-header elements
 * @param DataBinding specifies the binding-type for the data elements
 */
public abstract class GroupedListAdapter<Data : Any, Header : Any, GroupBy : Comparable<GroupBy>, HeaderBinding : ViewBinding, DataBinding : ViewBinding> :
    RecyclerView.Adapter<GroupedListAdapter.GroupedListViewHolder>() {

    public class GroupedListViewHolder(internal val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    public class GroupedDataItem<Data : Any>(public val type: Type, public val data: Data) {
        public enum class Type(public val viewType: Int) {
            DATA_ITEM(1),
            HEADER_ITEM(2);

            internal companion object {
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

    /**
     * builds the grouped data, must be called if data changed before calling [notifyDataSetChanged]
     */
    public fun buildGroupedData() {
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

    /**
     * Comparator used to sort data before grouping
     */
    protected abstract fun getSortComperator(): Comparator<Data>

    /**
     * returns the which should be displayed in the list
     */
    protected abstract fun getData(): List<Data>

    /**
     * returns the property on which the the data is grouped by
     */
    protected abstract fun getGroupByProperty(dataModel: Data): GroupBy

    /**
     * returns the property which is used for the header
     */
    protected abstract fun getHeaderModel(dataModel: Data): Header

    /**
     * creates the header item binding with parent
     */
    protected abstract fun createHeaderBinding(parent: ViewGroup): HeaderBinding

    /**
     * creates the data item binding with parent
     */
    protected abstract fun createDataItemBinding(parent: ViewGroup): DataBinding

    /**
     * configures header item binding with model
     */
    protected abstract fun configureHeaderBinding(binding: HeaderBinding, model: Header)

    /**
     * configures data item binding with model
     */
    protected abstract fun configureDataItemBinding(binding: DataBinding, model: Data)
}
