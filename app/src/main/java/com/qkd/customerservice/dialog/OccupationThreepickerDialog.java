package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.OccuptionThreeOneAdapter;
import com.qkd.customerservice.adapter.OccuptionThreeThreeAdapter;
import com.qkd.customerservice.adapter.OccuptionThreeTwoAdapter;
import com.qkd.customerservice.bean.OccupationBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2021/5/28 08:36
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class OccupationThreepickerDialog extends DialogFragment implements OccuptionThreeOneAdapter.OnClickOneItem,
        OccuptionThreeTwoAdapter.OnClickTwoItem, OccuptionThreeThreeAdapter.OnClickThreeItem {

    private List<OccupationBean> detail;

    private RecyclerView occuption_recy_one;
    private RecyclerView occuption_recy_two;
    private RecyclerView occuption_recy_three;
    private RecyclerView occuption_recy_four;

    private OccuptionThreeOneAdapter oneAdapter;
    private OccuptionThreeTwoAdapter twoAdapter;
    private OccuptionThreeThreeAdapter threeAdapter;

    private OnSelectOccpationListener mOnSelectOccpationListener;

    public void setOnSelectOccpationListener(OnSelectOccpationListener onSelectOccpationListener) {
        mOnSelectOccpationListener = onSelectOccpationListener;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_occupation_picker, container, false);
        Bundle bundle = getArguments();
        detail = bundle.getParcelableArrayList("detailList");
        occuption_recy_one = view.findViewById(R.id.occuption_recy_one);
        occuption_recy_two = view.findViewById(R.id.occuption_recy_two);
        occuption_recy_three = view.findViewById(R.id.occuption_recy_three);
        occuption_recy_four = view.findViewById(R.id.occuption_recy_four);
        occuption_recy_four.setVisibility(View.GONE);
        initView();
        return view;
    }

    private void initView() {
        occuption_recy_one.setLayoutManager(new LinearLayoutManager(getContext()));
        oneAdapter = new OccuptionThreeOneAdapter(getContext(), detail);
        oneAdapter.setOnClickOneItem(this);
        occuption_recy_one.setAdapter(oneAdapter);

        if (detail == null || detail.size() == 0) {
            return;
        }

        twoAdapter = new OccuptionThreeTwoAdapter(getContext());
        twoAdapter.setOnClickTwoItem(this);
        occuption_recy_two.setLayoutManager(new LinearLayoutManager(getContext()));
        occuption_recy_two.setAdapter(twoAdapter);
        OccupationBean oneBean = detail.get(0);
        twoAdapter.addAll(oneBean.getChildren());

        threeAdapter = new OccuptionThreeThreeAdapter(getContext());
        threeAdapter.setOnClickThreeItem(this);
        occuption_recy_three.setLayoutManager(new LinearLayoutManager(getContext()));
        occuption_recy_three.setAdapter(threeAdapter);
        if (oneBean.getChildren() == null || oneBean.getChildren().size() == 0) {
            return;
        }
        OccupationBean.ChildrenDTOX twoBean = oneBean.getChildren().get(0);
        threeAdapter.addAll(twoBean.getChildren());

    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setWindowAnimations(R.style.animate_dialog);
            setCancelable(true);
        }
    }

    @Override
    public void onClickOne(OccupationBean bean) {
        List<OccupationBean.ChildrenDTOX> list = bean.getChildren();
        if (list != null && list.size() > 0) {
            twoAdapter.addAll(list);
            occuption_recy_two.scrollToPosition(0);
            List<OccupationBean.ChildrenDTOX.ChildrenDTO> list1 = list.get(0).getChildren();
            if (list1 != null && list1.size() > 0) {
                threeAdapter.addAll(list1);
                occuption_recy_three.scrollToPosition(0);
            }
        }
    }

    @Override
    public void onClickTwo(OccupationBean.ChildrenDTOX bean) {
        List<OccupationBean.ChildrenDTOX.ChildrenDTO> list = bean.getChildren();
        if (list != null && list.size() > 0) {
            threeAdapter.addAll(list);
            occuption_recy_three.scrollToPosition(0);
        }
    }

    @Override
    public void onClickThree(OccupationBean.ChildrenDTOX.ChildrenDTO threeBean) {
        OccupationBean oneBean = oneAdapter.getCurrBean();
        OccupationBean.ChildrenDTOX twoBean = twoAdapter.getCurrBean();
        Map<String, Object> oneMap = new HashMap<>();
        oneMap.put("code", oneBean.getCode());
        oneMap.put("name", oneBean.getName());

        Map<String, Object> twoMap = new HashMap<>();
        twoMap.put("code", twoBean.getCode());
        twoMap.put("name", twoBean.getName());

        Map<String, Object> threeMap = new HashMap<>();
        threeMap.put("code", threeBean.getCode());
        threeMap.put("name", threeBean.getName());

        List<Map<String, Object>> maps = new ArrayList<>();
        maps.add(oneMap);
        maps.add(twoMap);
        maps.add(threeMap);

        if (mOnSelectOccpationListener != null) {
            mOnSelectOccpationListener.selectOccpation(maps);
            dismiss();
        }
    }

    public interface OnSelectOccpationListener {
        void selectOccpation(List<Map<String, Object>> maps);
    }
}