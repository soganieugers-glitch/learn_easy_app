package com.learneasy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learneasy.R;
import com.learneasy.models.Lesson;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private List<Lesson> lessons;
    private OnLessonClickListener listener;

    public interface OnLessonClickListener {
        void onLessonClick(Lesson lesson);
    }

    public LessonAdapter(List<Lesson> lessons, OnLessonClickListener listener) {
        this.lessons = lessons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.bind(lesson, listener);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView category;
        private TextView duration;
        private ImageView icon;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lesson_title);
            category = itemView.findViewById(R.id.lesson_category);
            duration = itemView.findViewById(R.id.lesson_duration);
            icon = itemView.findViewById(R.id.lesson_icon);
        }

        public void bind(Lesson lesson, OnLessonClickListener listener) {
            title.setText(lesson.getTitle());
            category.setText(lesson.getCategory());
            duration.setText(lesson.getDuration() + " min");

            int imageResourceId = itemView.getContext().getResources().getIdentifier(
                    lesson.getImageUrl(), "drawable", itemView.getContext().getPackageName());
            if (imageResourceId != 0) {
                icon.setImageResource(imageResourceId);
            }

            itemView.setOnClickListener(v -> listener.onLessonClick(lesson));
        }
    }
}
