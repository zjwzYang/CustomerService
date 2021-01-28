package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.audio.AudioPlayManager;
import com.qkd.customerservice.audio.IAudioPlayListener;
import com.qkd.customerservice.bean.AudioDuraingBean;
import com.qkd.customerservice.bean.KnowledgeOutput;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/4/21 10:54
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExchangeTextAdapter extends RecyclerView.Adapter<ExchangeTextAdapter.ExchangeTextViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<KnowledgeOutput.DataBean.ListBean> dataList;
    private int mediaType;

    public ExchangeTextAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    @NonNull
    @Override
    public ExchangeTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_exchange_text, parent, false);
        return new ExchangeTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExchangeTextViewHolder holder, final int position) {
        final KnowledgeOutput.DataBean.ListBean bean = dataList.get(position);
        final String text = bean.getMediaContent();
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示")
                        .setMessage("确定删除？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (onClickDeleteListener != null) {
                                    onClickDeleteListener.onDelete(bean.getId(), position);
                                }
                            }
                        });
                builder.create().show();
            }
        });
        if (mediaType == 3) {
            holder.mTextView.setText(bean.getMediaPurpose());
            holder.used_purpose.setVisibility(View.GONE);
            holder.mYuYinLinear.setVisibility(View.VISIBLE);
            int duraing = bean.getDuraing();
            if (duraing == 0) {
//                duraing = getDuring(text);
//                bean.setDuraing(duraing);
                getDuring(text, position);
            }
            holder.mYuyinLength.setText(duraing + "秒");
            holder.mYuYinLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri audioPath = Uri.parse(text);
                    if (AudioPlayManager.getInstance().isPlaying()) {
                        if (AudioPlayManager.getInstance().getPlayingUri().equals(audioPath)) {
                            AudioPlayManager.getInstance().stopPlay();
                            return;
                        }
                        AudioPlayManager.getInstance().stopPlay();
                    }
                    if (!AudioPlayManager.getInstance().isInNormalMode(view.getContext()) && AudioPlayManager.getInstance().isInVOIPMode(view.getContext())) {
                        Toast.makeText(context, "声音通道被占用，请稍后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AudioPlayManager.getInstance().startPlay(context, audioPath, new IAudioPlayListener() {
                        @Override
                        public void onStart(Uri uri) {
                            holder.mYuYinStatus.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onStop(Uri uri) {
                            holder.mYuYinStatus.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onComplete(Uri uri) {
                            holder.mYuYinStatus.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });
        } else {
            holder.mYuYinLinear.setVisibility(View.GONE);
            holder.mTextView.setText(text);
            holder.used_purpose.setText(bean.getMediaPurpose());
            holder.used_purpose.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // 获得音频长度
    private void getDuring(final String audioUrl, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int during = 0;
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(audioUrl);
                    mediaPlayer.prepare();
                    during = mediaPlayer.getDuration() / 1000;
                    //记得释放资源
                    mediaPlayer.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AudioDuraingBean bean = new AudioDuraingBean();
                bean.setDuraing(during);
                bean.setPosition(position);
                EventBus.getDefault().post(bean);
            }
        }).start();
    }

    public void addAll(List<KnowledgeOutput.DataBean.ListBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.dataList.clear();
    }

    public void delete(int position) {
        this.dataList.remove(position);
        notifyItemRemoved(position);
        if (position != dataList.size()) {
            notifyItemRangeChanged(position, dataList.size() - position);
        }
    }

    public void refreshPosition(AudioDuraingBean bean) {
        if (bean.getDuraing() != 0) {
            this.dataList.get(bean.getPosition()).setDuraing(bean.getDuraing());
            notifyItemChanged(bean.getPosition());
        }
    }

    static class ExchangeTextViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mYuYinLinear;
        private TextView mYuyinLength;
        private TextView mYuYinStatus;
        private TextView mTextView;
        private TextView used_purpose;
        private ImageView mDelete;

        public ExchangeTextViewHolder(@NonNull View itemView) {
            super(itemView);
            mYuYinLinear = itemView.findViewById(R.id.used_yuyin_linear);
            mYuyinLength = itemView.findViewById(R.id.used_yuyin_length);
            mYuYinStatus = itemView.findViewById(R.id.used_yuyin_status);
            mTextView = itemView.findViewById(R.id.used_content);
            used_purpose = itemView.findViewById(R.id.used_purpose);
            mDelete = itemView.findViewById(R.id.exchange_delete);
        }
    }

    private OnClickDeleteListener onClickDeleteListener;

    public void setOnClickDeleteListener(OnClickDeleteListener onClickDeleteListener) {
        this.onClickDeleteListener = onClickDeleteListener;
    }

    public interface OnClickDeleteListener {
        void onDelete(int mediaId, int position);
    }
}