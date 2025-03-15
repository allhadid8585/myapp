package com.example.layaout.myIncomeExpence.screen

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.red
import androidx.core.view.marginTop
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.recyclerview.widget.*
import com.example.layaout.R
import com.example.layaout.delete.DeleteItem
import com.example.layaout.myIncomeExpence.dataClasess.Transaction
import com.example.layaout.myIncomeExpence.viewModel.TransactionViewModel
import com.example.layaout.ui.theme.Red
import java.lang.Integer.max
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Composable
fun NativeChatList(
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    transactions: List<Transaction>,
    modifier: Modifier = Modifier

) {

    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }
    var swipedPosition by remember { mutableStateOf<Int?>(null) }

    val groupedTransactions = transactions.groupBy { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(it.date) }
    val adapterr = remember { ChatAdapter(transactionViewModel) }

    LaunchedEffect(transactions) {
        adapterr.submitList(
            groupedTransactions.toList()
                .sortedByDescending { it.first } // مرتب‌سازی نزولی بر اساس کلید
                .flatMap { listOf(it.first) + it.second }
        )    }

    swipedPosition?.let { adapterr.notifyItemChanged(it) }


    if (isDialogOpen) {
        DeleteItem(
            onDismissRequest = {
                isDialogOpen = false
            },
            onConfirmRequest = {
                selectedTransaction?.id?.let { transactionViewModel.deleteTransaction(it) }
                isDialogOpen = false
            }
        )
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val recyclerView = RecyclerView(context).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterr

                addItemDecoration(StickyHeaderDecoration(adapterr))
                addItemDecoration(StickyHeaderDecoration1(adapterr))
            }




            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false // No drag & drop support

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (adapterr.getItemViewType(position) == ChatAdapter.TYPE_TRANSACTION) {
                        selectedTransaction = adapterr.getTransactionAt(position)
                        swipedPosition = position
                        isDialogOpen = true
                    } else {
                        adapterr.notifyItemChanged(position)
                    }
                }

                override fun onChildDraw(
                    c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    val paint = Paint().apply { color = Color.RED }
                    val icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.trash_solid)

                    c.drawRect(
                        itemView.left.toFloat(), itemView.top.toFloat(),
                        itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                    )

                    icon?.setBounds(
                        itemView.right - 130, itemView.top + 40,
                        itemView.right - 50, itemView.bottom - 40
                    )
                    icon?.draw(c)

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            })

            itemTouchHelper.attachToRecyclerView(recyclerView)
            recyclerView

            itemTouchHelper.attachToRecyclerView(recyclerView)
            recyclerView
        },

    )
}

class ChatAdapter (private val  transactionViewModel: TransactionViewModel): ListAdapter<Any, RecyclerView.ViewHolder>(ChatDiffCallback()) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_TRANSACTION = 1
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.headerText)
    }

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val transactionAmountView: TextView = view.findViewById(R.id.transactionAmountText)
        val categoryView: TextView = view.findViewById(R.id.categoryText)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is String) TYPE_HEADER else TYPE_TRANSACTION
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.xml.item_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.xml.item_transaction, parent, false)
            TransactionViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.dateText.text = ""
        } else if (holder is TransactionViewHolder) {
            val transaction = getItem(position) as Transaction
            holder.transactionAmountView.text = transaction.amount.toString()
            holder.categoryView.text = transactionViewModel.categories.value.find { it.id == transaction.categoryId }?.name


            if (transaction.type == "income"){
                holder.transactionAmountView.setTextColor(Color.GREEN)

            }else{
                holder.transactionAmountView.setTextColor(Color.RED)
            }
        }
    }



    fun getTransactionAt(position: Int): Transaction? {
        return if (getItemViewType(position) == TYPE_TRANSACTION) getItem(position) as Transaction else null
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is Transaction && newItem is Transaction) {
            oldItem.id == newItem.id
        } else oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}

class StickyHeaderDecoration1(private val adapter: ChatAdapter) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        color = android.graphics.Color.LTGRAY
        textSize = 40f
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as? LinearLayoutManager ?: return
        val childCount = parent.childCount

        if (childCount == 0) return

        var previousHeader: String? = null

        for (i in 1 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)

            if (position == RecyclerView.NO_POSITION) continue

            val item = adapter.currentList.getOrNull(position) ?: continue

            if (item is String) {  // This is a header item
                val top = max(view.top, 0).toFloat()

                if (previousHeader != item) {

                    // Draw the header text
                    c.drawText(item, parent.width.toFloat()/2 - 150f, top + 90, Paint().apply {
                        color = android.graphics.Color.LTGRAY
                        textSize = 60f
                        isAntiAlias = true
                        typeface = android.graphics.Typeface.DEFAULT_BOLD
                    })

                    previousHeader = item
                }
            }
        }
    }
}



class StickyHeaderDecoration(private val adapter: ChatAdapter) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        color = android.graphics.Color.LTGRAY
        textSize = 40f
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as? LinearLayoutManager ?: return
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

        if (firstVisiblePosition == RecyclerView.NO_POSITION) return

        var headerText: String? = null
        var nextHeaderY: Float? = null

        for (i in firstVisiblePosition downTo 0) {
            if (adapter.getItemViewType(i) == ChatAdapter.TYPE_HEADER) {
                headerText = adapter.currentList[i] as String
                break
            }
        }

        for (i in firstVisiblePosition + 1 until adapter.currentList.size) {
            if (adapter.getItemViewType(i) == ChatAdapter.TYPE_HEADER) {
                val view = layoutManager.findViewByPosition(i)
                if (view != null) {
                    nextHeaderY = view.top.toFloat()
                }
                break
            }
        }





        if (headerText != null) {
            drawStickyHeader(c, parent, headerText, nextHeaderY)
        }
    }

    private fun drawStickyHeader(c: Canvas, parent: RecyclerView, headerText: String, nextHeaderY: Float?) {
        val headerHeight = 100f  // ارتفاع هدر
        val top = 0f
        var bottom = headerHeight

        // 🔹 پس‌زمینه‌ی هدر را رسم کن

        if (nextHeaderY != null && nextHeaderY < bottom) {
            bottom = nextHeaderY // Push the current header up when the next one arrives
        }

        // Draw the header text
        c.drawText(headerText, parent.width.toFloat()/2 - 150f, bottom - 30, Paint().apply {
            color = android.graphics.Color.LTGRAY
            textSize = 60f
            isAntiAlias = true
            typeface = android.graphics.Typeface.DEFAULT_BOLD
        })
    }
}
