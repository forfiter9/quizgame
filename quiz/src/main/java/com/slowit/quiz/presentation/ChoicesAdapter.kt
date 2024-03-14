package com.slowit.quiz.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slowit.quiz.R
import com.slowit.quiz.domain.model.Choice

class ChoicesAdapter : ListAdapter<Choice, ChoicesAdapter.ChoiceViewHolder>(
    diffCallback,
) {
    var onChoiceClickListener: OnChoiceClickListener? = null
    private var selectedChoice: Choice? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChoiceViewHolder =
        ChoiceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_choice, parent, false),
        )

    override fun onBindViewHolder(
        holder: ChoiceViewHolder,
        position: Int,
    ) {
        val currentChoice = getItem(position)

        changeCorrectImageConstraint(holder.correctAnswerImage, position)

        when (position) {
            0 -> {
                holder.figureImage.setImageResource(R.drawable.ic_triangle)
                setBackgroundTintForItemView(
                    holder,
                    com.slowit.core.R.color.venetian_red,
                    currentChoice,
                )
            }

            1 -> {
                holder.figureImage.setImageResource(R.drawable.ic_diamond)
                setBackgroundTintForItemView(
                    holder,
                    com.slowit.core.R.color.bright_navy_blue,
                    currentChoice,
                )
            }

            2 -> {
                holder.figureImage.setImageResource(R.drawable.ic_circle)
                setBackgroundTintForItemView(
                    holder,
                    com.slowit.core.R.color.chinese_gold,
                    currentChoice,
                )
            }

            3 -> {
                holder.figureImage.setImageResource(R.drawable.ic_square)
                setBackgroundTintForItemView(
                    holder,
                    com.slowit.core.R.color.verse_green,
                    currentChoice,
                )
            }

            else -> {
                holder.figureImage.setImageResource(R.drawable.ic_triangle)
                setBackgroundTintForItemView(
                    holder,
                    com.slowit.core.R.color.verse_green,
                    currentChoice,
                )
            }
        }

        holder.answer.text = currentChoice.answer

        holder.itemView.setOnClickListener {
            onChoiceClickListener?.onChoiceClick(currentChoice)
        }
    }

    private fun changeCorrectImageConstraint(
        correctAnswerImage: ImageView,
        position: Int,
    ) {
        val layoutParams = correctAnswerImage.layoutParams as ConstraintLayout.LayoutParams
        if (position % 2 == 0) {
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        } else {
            layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        }

        correctAnswerImage.layoutParams = layoutParams
    }

    private fun setBackgroundTintForItemView(
        holder: ChoiceViewHolder,
        colorId: Int,
        currentChoice: Choice,
    ) {
        val tintId =
            when {
                selectedChoice == null -> colorId
                currentChoice.isCorrectChoice -> com.slowit.core.R.color.green_ryb
                selectedChoice == currentChoice -> com.slowit.core.R.color.venetian_red
                else -> com.slowit.core.R.color.baker_miller_pink
            }

        holder.apply {
            itemBackground.background.setTint(
                ContextCompat.getColor(
                    holder.itemView.context,
                    tintId,
                ),
            )
            correctAnswerImage.isVisible = selectedChoice != null
            figureImage.isVisible = selectedChoice == null
            correctAnswerImage.setImageResource(if (currentChoice.isCorrectChoice) R.drawable.ic_correct else R.drawable.ic_wrong)
        }
    }

    fun setSelectedChoice(choice: Choice?) {
        selectedChoice = choice
        notifyDataSetChanged()
    }

    inner class ChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val answer: TextView = itemView.findViewById(R.id.item_choice_answer)
        val figureImage: ImageView = itemView.findViewById(R.id.item_figure_image)
        val correctAnswerImage: ImageView = itemView.findViewById(R.id.item_correct_icon)
        val itemBackground: View = itemView.findViewById(R.id.item_background)
        val itemRoot: ConstraintLayout = itemView.findViewById(R.id.item_view_root)
    }

    fun interface OnChoiceClickListener {
        fun onChoiceClick(choice: Choice)
    }

    companion object {
        private val diffCallback =
            object : DiffUtil.ItemCallback<Choice>() {
                override fun areItemsTheSame(
                    oldItem: Choice,
                    newItem: Choice,
                ) = oldItem.answer == newItem.answer

                override fun areContentsTheSame(
                    oldItem: Choice,
                    newItem: Choice,
                ) = oldItem.hashCode() == newItem.hashCode()
            }
    }
}
