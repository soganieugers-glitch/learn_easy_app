package com.learneasy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learneasy.R;
import com.learneasy.models.QuizResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.ResultViewHolder> {

    private List<QuizResult> results;

    public QuizResultAdapter(List<QuizResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        QuizResult result = results.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryText;
        private TextView scoreText;
        private TextView percentageText;
        private TextView dateText;
        private ProgressBar scoreProgress;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.category_text);
            scoreText = itemView.findViewById(R.id.score_text);
            percentageText = itemView.findViewById(R.id.percentage_text);
            dateText = itemView.findViewById(R.id.date_text);
            scoreProgress = itemView.findViewById(R.id.score_progress);
        }

        public void bind(QuizResult result) {
            categoryText.setText(result.getCategory());
            scoreText.setText("Score: " + result.getScore() + "/" + result.getTotalQuestions());
            int percentage = result.getPercentage();
            percentageText.setText(percentage + "%");
            scoreProgress.setProgress(percentage);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String dateString = dateFormat.format(new Date(result.getTimestamp()));
            dateText.setText(dateString);
        }
    }
}
