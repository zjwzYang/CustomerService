package com.qkd.customerservice.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.qkd.customerservice.R;
import com.qkd.customerservice.audio.AudioPlayManager;
import com.qkd.customerservice.audio.AudioRecordManager;
import com.qkd.customerservice.audio.IAudioPlayListener;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.bean.VoiceMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/4/20 09:11
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TEXT_LEFT = 0;
    private static final int TEXT_RIGHT = 1;
    private static final int VOICE_LEFT = 2;
    private static final int VOICE_RIGHT = 3;
    private static final int IMAGE_LEFT = 4;
    private static final int IMAGE_RIGHT = 5;

    private Context context;
    private List<MsgBean> msgList;
    private String faceUrl;

    private RequestOptions options;
    private OnClickImageListener onClickImageListener;

    public MsgAdapter(Context context, List<MsgBean> msgList, String faceUrl) {
        this.context = context;
        this.msgList = msgList;
        this.faceUrl = faceUrl;
        RoundedCorners roundedCorners = new RoundedCorners(6);
        options = new RequestOptions()
                .transform(new CenterCrop(), roundedCorners)
                .error(R.drawable.ic_image_error);
    }

    @Override
    public int getItemViewType(int position) {
        MsgBean msgBean = msgList.get(position);
        MsgBean.MsgType msgType = msgBean.getMsgType();
        int type = msgBean.getType();
        if (msgType == MsgBean.MsgType.TEXT) {
            if (type == 0) {
                return TEXT_LEFT;
            } else {
                return TEXT_RIGHT;
            }
        } else if (msgType == MsgBean.MsgType.VOICE) {
            if (type == 0) {
                return VOICE_LEFT;
            } else {
                return VOICE_RIGHT;
            }
        } else if (msgType == MsgBean.MsgType.IMAGE) {
            if (type == 0) {
                return IMAGE_LEFT;
            } else {
                return IMAGE_RIGHT;
            }
        } else {
            return TEXT_LEFT;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TEXT_LEFT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_text_left, parent, false);
            return new LeftMsgViewHolder(view);
        } else if (viewType == TEXT_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_text_right, parent, false);
            return new RightMsgViewHolder(view);
        } else if (viewType == VOICE_LEFT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_voice_left, parent, false);
            return new LeftVoiceMsgViewHolder(view);
        } else if (viewType == VOICE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_voice_right, parent, false);
            return new RightVoiceMsgViewHolder(view);
        } else if (viewType == IMAGE_LEFT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_img_left, parent, false);
            return new LeftImageViewHolder(view);
        } else if (viewType == IMAGE_RIGHT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_img_right, parent, false);
            return new RightImageViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_text_left, parent, false);
            return new LeftMsgViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        MsgBean msgBean = msgList.get(position);
        int viewType = getItemViewType(position);
        if (viewType == TEXT_LEFT) {
            TextMsg textMsg = (TextMsg) msgBean;
            LeftMsgViewHolder holder = (LeftMsgViewHolder) viewHolder;
            holder.leftName.setText(textMsg.getNickName());
            holder.leftContent.setText(textMsg.getContent());
            Glide.with(context)
                    .load(faceUrl)
                    .into(holder.leftHead);
        } else if (viewType == TEXT_RIGHT) {
            TextMsg textMsg = (TextMsg) msgBean;
            RightMsgViewHolder holder = (RightMsgViewHolder) viewHolder;
            holder.rightContent.setText(textMsg.getContent());
        } else if (viewType == VOICE_LEFT) {
            final VoiceMsg voiceMsg = (VoiceMsg) msgBean;
            LeftVoiceMsgViewHolder holder = (LeftVoiceMsgViewHolder) viewHolder;
            Glide.with(context)
                    .load(faceUrl)
                    .into(holder.mHeadV);
            holder.mDuringV.setText(String.format("%s\"", voiceMsg.getDuration()));

            int minWidth = 70, maxWidth = 204;
            float scale = context.getResources().getDisplayMetrics().density;
            minWidth = (int) (minWidth * scale + 0.5f);
            maxWidth = (int) (maxWidth * scale + 0.5f);
            int duration = AudioRecordManager.getInstance().getMaxVoiceDuration();
            holder.mVoiceV.getLayoutParams().width = minWidth + (maxWidth - minWidth) / duration * voiceMsg.getDuration();

//            holder.mVoiceV.setScaleType(ImageView.ScaleType.FIT_END);
            holder.mVoiceV.setBackgroundResource(R.drawable.bg_chat_receiver);

            AnimationDrawable animationDrawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.rc_an_voice_receive);
            if (voiceMsg.isPlaying()) {
                holder.mVoiceV.setImageDrawable(animationDrawable);
                if (animationDrawable != null)
                    animationDrawable.start();
            } else {
                holder.mVoiceV.setImageDrawable(holder.mVoiceV.getResources().getDrawable(R.drawable.rc_ic_voice_receive));
                if (animationDrawable != null)
                    animationDrawable.stop();
            }
            holder.mVoiceV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri audioPath = voiceMsg.getAudioPath();
                    Log.i("获取语音", "onClick: " + audioPath.toString());
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
                            voiceMsg.setPlaying(true);
                            notifyItemChanged(position);
                        }

                        @Override
                        public void onStop(Uri uri) {
                            voiceMsg.setPlaying(false);
                            notifyItemChanged(position);
                        }

                        @Override
                        public void onComplete(Uri uri) {
                            voiceMsg.setPlaying(false);
                            notifyItemChanged(position);
                        }
                    });
                }
            });

        } else if (viewType == VOICE_RIGHT) {
            final VoiceMsg voiceMsg = (VoiceMsg) msgBean;
            RightVoiceMsgViewHolder holder = (RightVoiceMsgViewHolder) viewHolder;
            holder.mDuringV.setText(String.format("%s\"", voiceMsg.getDuration()));

            int minWidth = 70, maxWidth = 204;
            float scale = context.getResources().getDisplayMetrics().density;
            minWidth = (int) (minWidth * scale + 0.5f);
            maxWidth = (int) (maxWidth * scale + 0.5f);
            int duration = AudioRecordManager.getInstance().getMaxVoiceDuration();
            holder.mVoiceV.getLayoutParams().width = minWidth + (maxWidth - minWidth) / duration * voiceMsg.getDuration();

            holder.mVoiceV.setScaleType(ImageView.ScaleType.FIT_END);
            holder.mVoiceV.setBackgroundResource(R.drawable.bg_chat_sender);
            AnimationDrawable animationDrawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.rc_an_voice_sent);
            if (voiceMsg.isPlaying()) {
                holder.mVoiceV.setImageDrawable(animationDrawable);
                if (animationDrawable != null)
                    animationDrawable.start();
            } else {
                holder.mVoiceV.setImageDrawable(holder.mVoiceV.getResources().getDrawable(R.drawable.rc_ic_voice_sent));
                if (animationDrawable != null)
                    animationDrawable.stop();
            }
            holder.mVoiceV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri audioPath = voiceMsg.getAudioPath();
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
                            voiceMsg.setPlaying(true);
                            notifyItemChanged(position);
                        }

                        @Override
                        public void onStop(Uri uri) {
                            voiceMsg.setPlaying(false);
                            notifyItemChanged(position);
                        }

                        @Override
                        public void onComplete(Uri uri) {
                            voiceMsg.setPlaying(false);
                            notifyItemChanged(position);
                        }
                    });
                }
            });
        } else if (viewType == IMAGE_LEFT) {
            ImageMsg imageMsg = (ImageMsg) msgBean;
            final LeftImageViewHolder holder = (LeftImageViewHolder) viewHolder;
            holder.mNameV.setText(imageMsg.getNickName());
            Glide.with(context)
                    .load(faceUrl)
                    .into(holder.mHeadV);
            Glide.with(context)
                    .load(imageMsg.getImgPath())
                    .apply(options)
                    .into(holder.mImgV);
            holder.mappingViews.put(0, holder.mImgV);
            final List<String> photoUrlList = new ArrayList<>();
            photoUrlList.add(imageMsg.getImgPath());
            holder.mImgV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickImageListener != null) {
                        onClickImageListener.onClickImage(holder.mImgV, holder.mappingViews, photoUrlList);
                    }
                }
            });

        } else if (viewType == IMAGE_RIGHT) {
            final ImageMsg imageMsg = (ImageMsg) msgBean;
            final RightImageViewHolder holder = (RightImageViewHolder) viewHolder;
            Glide.with(context)
                    .load(imageMsg.getImgPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.i("12345678", "onLoadFailed: " + e.getMessage());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .apply(options)
                    .into(holder.mImgV);
            holder.mappingViews.put(0, holder.mImgV);
            holder.mImgV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> photoUrlList = new ArrayList<>();
                    String imgUrl = imageMsg.getImgUrl();
                    if (TextUtils.isEmpty(imgUrl)) {
                        photoUrlList.add(imageMsg.getImgPath());
                    } else {
                        photoUrlList.add(imgUrl);
                    }
                    if (onClickImageListener != null) {
                        onClickImageListener.onClickImage(holder.mImgV, holder.mappingViews, photoUrlList);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public void addMsg(MsgBean msgBean) {
        this.msgList.add(msgBean);
        notifyDataSetChanged();
    }

    public void addMsgTop(MsgBean msgBean) {
        this.msgList.add(0, msgBean);
        notifyDataSetChanged();
    }

    public void notifyImageItem(String path, String url) {
        for (int i = 0; i < msgList.size(); i++) {
            MsgBean msgBean = msgList.get(i);
            if (msgBean instanceof ImageMsg) {
                ImageMsg imageMsg = (ImageMsg) msgBean;
                if (path.equals(imageMsg.getImgPath())) {
                    imageMsg.setImgUrl(url);
                    break;
                }
            }
        }
    }

    public void setOnClickImageListener(OnClickImageListener onClickImageListener) {
        this.onClickImageListener = onClickImageListener;
    }

    public interface OnClickImageListener {
        void onClickImage(ImageView imageView, SparseArray<ImageView> mappingViews, List<String> urlList);
    }

    static class LeftMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView leftHead;
        private TextView leftName;
        private TextView leftContent;

        public LeftMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            leftHead = itemView.findViewById(R.id.iv_head_left);
            leftName = itemView.findViewById(R.id.tv_nickname_left);
            leftContent = itemView.findViewById(R.id.tv_content_left);
        }
    }

    static class RightMsgViewHolder extends RecyclerView.ViewHolder {

        private ImageView rightHead;
        private TextView rightContent;

        public RightMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            rightHead = itemView.findViewById(R.id.iv_head_right);
            rightContent = itemView.findViewById(R.id.tv_content_right);
        }
    }

    static class LeftVoiceMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private ImageView mVoiceV;
        private TextView mDuringV;

        public LeftVoiceMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_left);
            mVoiceV = itemView.findViewById(R.id.rc_img_left);
            mDuringV = itemView.findViewById(R.id.rc_during_left);

        }
    }

    static class RightVoiceMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private ImageView mVoiceV;
        private TextView mDuringV;

        public RightVoiceMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_right);
            mVoiceV = itemView.findViewById(R.id.rc_img_right);
            mDuringV = itemView.findViewById(R.id.rc_during_right);
        }
    }

    static class LeftImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private TextView mNameV;
        private ImageView mImgV;
        private SparseArray<ImageView> mappingViews;

        public LeftImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_left);
            mNameV = itemView.findViewById(R.id.tv_nickname_left);
            mImgV = itemView.findViewById(R.id.tv_image_left);
            mappingViews = new SparseArray<>();
        }
    }

    static class RightImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private ImageView mImgV;
        private SparseArray<ImageView> mappingViews;

        public RightImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_right);
            mImgV = itemView.findViewById(R.id.tv_image_right);
            mappingViews = new SparseArray<>();
        }
    }
}