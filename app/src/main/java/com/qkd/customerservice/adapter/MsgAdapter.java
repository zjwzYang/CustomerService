package com.qkd.customerservice.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.audio.AudioPlayManager;
import com.qkd.customerservice.audio.AudioRecordManager;
import com.qkd.customerservice.audio.IAudioPlayListener;
import com.qkd.customerservice.bean.ArticleMsg;
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
 * @org 趣看点
 */
public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TEXT_LEFT = 0;
    private static final int TEXT_RIGHT = 1;
    private static final int VOICE_LEFT = 2;
    private static final int VOICE_RIGHT = 3;
    private static final int IMAGE_LEFT = 4;
    private static final int IMAGE_RIGHT = 5;
    private static final int TEXT_CENTER = 6;
    private static final int ARTICLE_RIGHT = 7;

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
            } else if (type == 1) {
                return TEXT_RIGHT;
            } else {
                return TEXT_CENTER;
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
        } else if (msgType == MsgBean.MsgType.ARTICLE) {
            return ARTICLE_RIGHT;
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
        } else if (viewType == TEXT_CENTER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_text_center, parent, false);
            return new CenterMsgViewHolder(view);
        } else if (viewType == VOICE_LEFT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_voice_left, parent, false);
            return new LeftVoiceMsgViewHolder(view);
        } else if (viewType == VOICE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_voice_right, parent, false);
            return new RightVoiceMsgViewHolder(view);
        } else if (viewType == IMAGE_LEFT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_img_left, parent, false);
            return new LeftImageViewHolder(view);
        } else if (viewType == IMAGE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_img_right, parent, false);
            return new RightImageViewHolder(view);
        } else if (viewType == ARTICLE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_article_right, parent, false);
            return new RightArticleViewHolder(view);
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
            holder.leftTime.setText(textMsg.getSendTime());
            Glide.with(context)
                    .load(faceUrl)
                    .into(holder.leftHead);
        } else if (viewType == TEXT_RIGHT) {
            TextMsg textMsg = (TextMsg) msgBean;
            RightMsgViewHolder holder = (RightMsgViewHolder) viewHolder;
            holder.rightContent.setText(textMsg.getContent());
            holder.rightTime.setText(textMsg.getSendTime());
        } else if (viewType == TEXT_CENTER) {
            TextMsg textMsg = (TextMsg) msgBean;
            CenterMsgViewHolder holder = (CenterMsgViewHolder) viewHolder;
            holder.centerTime.setText(textMsg.getSendTime());
            String content = textMsg.getContent();
            int index = content.lastIndexOf(Constant.TEXT_END_FLAG);
            content = content.substring(0, index);
            holder.centerContent.setText(content);

        } else if (viewType == VOICE_LEFT) {
            final VoiceMsg voiceMsg = (VoiceMsg) msgBean;
            LeftVoiceMsgViewHolder holder = (LeftVoiceMsgViewHolder) viewHolder;
            Glide.with(context)
                    .load(faceUrl)
                    .into(holder.mHeadV);
            holder.mDuringV.setText(String.format("%s\"", voiceMsg.getDuration()));
            holder.leftTime.setText(voiceMsg.getSendTime());
            holder.leftName.setText(voiceMsg.getNickName());

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
            int rightDuration = voiceMsg.getDuration();
            if (rightDuration == -1 || rightDuration == 0) {
                holder.mDuringV.setText("");
                rightDuration = 15;
            } else {
                holder.mDuringV.setText(String.format("%s\"", rightDuration));
            }
            holder.rightTime.setText(voiceMsg.getSendTime());

            int minWidth = 70, maxWidth = 204;
            float scale = context.getResources().getDisplayMetrics().density;
            minWidth = (int) (minWidth * scale + 0.5f);
            maxWidth = (int) (maxWidth * scale + 0.5f);
            int duration = AudioRecordManager.getInstance().getMaxVoiceDuration();
            holder.mVoiceV.getLayoutParams().width = minWidth + (maxWidth - minWidth) / duration * rightDuration;

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
            holder.leftTime.setText(imageMsg.getSendTime());
            Glide.with(context)
                    .load(faceUrl)
                    .into(holder.mHeadV);
            Glide.with(context)
                    .load(imageMsg.getImgPath())
//                    .apply(options)
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
            holder.rightTime.setText(imageMsg.getSendTime());
            Glide.with(context)
                    .load(imageMsg.getImgPath())
                    //.apply(options)
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
        } else if (viewType == ARTICLE_RIGHT) {
            RightArticleViewHolder holder = (RightArticleViewHolder) viewHolder;
            final ArticleMsg articleMsg = (ArticleMsg) msgBean;
            Glide.with(context)
                    .load(articleMsg.getPicUrl())
                    .apply(options)
                    .into(holder.mImageView);
            holder.artTitle.setText(articleMsg.getTitle());
            holder.artDesc.setText(articleMsg.getDescription());
            holder.rightTime.setText(articleMsg.getSendTime());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", articleMsg.getUrl());
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                    return false;
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

    public void removeTop() {
        this.msgList.remove(0);
        notifyItemRemoved(0);
        notifyItemRangeChanged(0, this.msgList.size());
    }

//    public boolean hasAddMsg(MsgBean msgBean) {
//        boolean added = false;
//        for (MsgBean bean : this.msgList) {
//            String msgId = bean.getMsgId();
//            if (!TextUtils.isEmpty(msgId)) {
//                if (msgId.equals(msgBean.getMsgId())) {
//                    added = true;
//                    break;
//                }
//            }
//        }
//        return added;
//    }

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
        private TextView leftTime;

        public LeftMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            leftTime = itemView.findViewById(R.id.text_left_time);
            leftHead = itemView.findViewById(R.id.iv_head_left);
            leftName = itemView.findViewById(R.id.tv_nickname_left);
            leftContent = itemView.findViewById(R.id.tv_content_left);
        }
    }

    static class RightMsgViewHolder extends RecyclerView.ViewHolder {

        private ImageView rightHead;
        private TextView rightContent;
        private TextView rightTime;

        public RightMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            rightHead = itemView.findViewById(R.id.iv_head_right);
            rightContent = itemView.findViewById(R.id.tv_content_right);
            rightTime = itemView.findViewById(R.id.text_right_time);
        }
    }

    static class CenterMsgViewHolder extends RecyclerView.ViewHolder {
        private TextView centerTime;
        private TextView centerContent;

        public CenterMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            centerTime = itemView.findViewById(R.id.text_center_time);
            centerContent = itemView.findViewById(R.id.text_center_content);
        }
    }

    static class LeftVoiceMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private ImageView mVoiceV;
        private TextView mDuringV;
        private TextView leftTime;
        private TextView leftName;

        public LeftVoiceMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_left);
            mVoiceV = itemView.findViewById(R.id.rc_img_left);
            mDuringV = itemView.findViewById(R.id.rc_during_left);
            leftTime = itemView.findViewById(R.id.voice_left_time);
            leftName = itemView.findViewById(R.id.tv_nickname_left);
        }
    }

    static class RightVoiceMsgViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private ImageView mVoiceV;
        private TextView mDuringV;
        private TextView rightTime;

        public RightVoiceMsgViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_right);
            mVoiceV = itemView.findViewById(R.id.rc_img_right);
            mDuringV = itemView.findViewById(R.id.rc_during_right);
            rightTime = itemView.findViewById(R.id.voice_right_time);
        }
    }

    static class LeftImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private TextView mNameV;
        private ImageView mImgV;
        private SparseArray<ImageView> mappingViews;
        private TextView leftTime;

        public LeftImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_left);
            mNameV = itemView.findViewById(R.id.tv_nickname_left);
            mImgV = itemView.findViewById(R.id.tv_image_left);
            mappingViews = new SparseArray<>();
            leftTime = itemView.findViewById(R.id.img_left_time);
        }
    }

    static class RightImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private ImageView mImgV;
        private SparseArray<ImageView> mappingViews;
        private TextView rightTime;

        public RightImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.iv_head_right);
            mImgV = itemView.findViewById(R.id.tv_image_right);
            mappingViews = new SparseArray<>();
            rightTime = itemView.findViewById(R.id.img_right_time);
        }
    }

    static class RightArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView rightTime;
        private TextView artTitle;
        private TextView artDesc;
        private ImageView mImageView;
        private LinearLayout mLinearLayout;

        public RightArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            rightTime = itemView.findViewById(R.id.article_right_time);
            artTitle = itemView.findViewById(R.id.article_right_title);
            artDesc = itemView.findViewById(R.id.article_right_desc);
            mImageView = itemView.findViewById(R.id.article_right_pic);
            mLinearLayout = itemView.findViewById(R.id.article_right_linear);
        }
    }
}