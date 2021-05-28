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
import com.qkd.customerservice.adapter.OccuptionFourAdapter;
import com.qkd.customerservice.adapter.OccuptionOneAdapter;
import com.qkd.customerservice.adapter.OccuptionThreeAdapter;
import com.qkd.customerservice.adapter.OccuptionTwoAdapter;
import com.qkd.customerservice.bean.TrialOccupationBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/5/28 08:36
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class OccupationpickerDialog extends DialogFragment implements OccuptionOneAdapter.OnClickOneItem,
        OccuptionTwoAdapter.OnClickTwoItem, OccuptionThreeAdapter.OnClickThreeItem, OccuptionFourAdapter.OnClickFourItem {

    private List<TrialOccupationBean.DetailDTO> detail;

    private RecyclerView occuption_recy_one;
    private RecyclerView occuption_recy_two;
    private RecyclerView occuption_recy_three;
    private RecyclerView occuption_recy_four;

    private OccuptionOneAdapter oneAdapter;
    private OccuptionTwoAdapter twoAdapter;
    private OccuptionThreeAdapter threeAdapter;
    private OccuptionFourAdapter fourAdapter;

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
        initView();
        return view;
    }

    private void initView() {
        occuption_recy_one.setLayoutManager(new LinearLayoutManager(getContext()));
        oneAdapter = new OccuptionOneAdapter(getContext(), detail);
        oneAdapter.setOnClickOneItem(this);
        occuption_recy_one.setAdapter(oneAdapter);

        if (detail == null || detail.size() == 0) {
            return;
        }

        twoAdapter = new OccuptionTwoAdapter(getContext());
        twoAdapter.setOnClickTwoItem(this);
        occuption_recy_two.setLayoutManager(new LinearLayoutManager(getContext()));
        occuption_recy_two.setAdapter(twoAdapter);
        TrialOccupationBean.DetailDTO oneBean = detail.get(0);
        twoAdapter.addAll(oneBean.getChildren());

        threeAdapter = new OccuptionThreeAdapter(getContext());
        threeAdapter.setOnClickThreeItem(this);
        occuption_recy_three.setLayoutManager(new LinearLayoutManager(getContext()));
        occuption_recy_three.setAdapter(threeAdapter);
        if (oneBean.getChildren() == null || oneBean.getChildren().size() == 0) {
            return;
        }
        TrialOccupationBean.DetailDTO.ChildrenDTOXX twoBean = oneBean.getChildren().get(0);
        threeAdapter.addAll(twoBean.getChildren());

        fourAdapter = new OccuptionFourAdapter(getContext());
        fourAdapter.setOnClickFourItem(this);
        occuption_recy_four.setLayoutManager(new LinearLayoutManager(getContext()));
        occuption_recy_four.setAdapter(fourAdapter);
        if (twoBean.getChildren() == null || twoBean.getChildren().size() == 0) {
            return;
        }
        TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX threeBean = twoBean.getChildren().get(0);
        fourAdapter.addAll(threeBean.getChildren());
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
    public void onClickOne(TrialOccupationBean.DetailDTO bean) {
        List<TrialOccupationBean.DetailDTO.ChildrenDTOXX> list = bean.getChildren();
        if (list != null && list.size() > 0) {
            twoAdapter.addAll(list);
            occuption_recy_two.scrollToPosition(0);
            List<TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX> list1 = list.get(0).getChildren();
            if (list1 != null && list1.size() > 0) {
                threeAdapter.addAll(list1);
                occuption_recy_three.scrollToPosition(0);
                List<TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO> list2 = list1.get(0).getChildren();
                if (list2 != null && list2.size() > 0) {
                    fourAdapter.addAll(list2);
                    occuption_recy_four.scrollToPosition(0);
                }
            }
        }
    }

    @Override
    public void onClickTwo(TrialOccupationBean.DetailDTO.ChildrenDTOXX bean) {
        List<TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX> list = bean.getChildren();
        if (list != null && list.size() > 0) {
            threeAdapter.addAll(list);
            occuption_recy_three.scrollToPosition(0);
            List<TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO> list1 = list.get(0).getChildren();
            if (list1 != null && list1.size() > 0) {
                fourAdapter.addAll(list1);
                occuption_recy_four.scrollToPosition(0);
            }
        }
    }

    @Override
    public void onClickThree(TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX bean) {
        List<TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO> list = bean.getChildren();
        if (list != null && list.size() > 0) {
            fourAdapter.addAll(list);
            occuption_recy_four.scrollToPosition(0);
        }
    }

    @Override
    public void onClickFour(TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO bean) {
        TrialOccupationBean.ValueBean valueBean = new TrialOccupationBean.ValueBean();
        valueBean.setLevel(bean.getLevel());
        valueBean.setLabel(bean.getLabelX());
        valueBean.setValue(bean.getValue());

        List<TrialOccupationBean.ValueBean.ParentsDTO> parents = new ArrayList<>();

        TrialOccupationBean.DetailDTO bean1 = oneAdapter.getCurrBean();
        TrialOccupationBean.ValueBean.ParentsDTO parents1 = new TrialOccupationBean.ValueBean.ParentsDTO();
        parents1.setLabel(bean1.getLabelX());
        parents1.setValue(bean1.getValue());
        parents.add(parents1);

        TrialOccupationBean.DetailDTO.ChildrenDTOXX bean2 = twoAdapter.getCurrBean();
        TrialOccupationBean.ValueBean.ParentsDTO parents2 = new TrialOccupationBean.ValueBean.ParentsDTO();
        parents2.setLabel(bean2.getLabelX());
        parents2.setValue(bean2.getValue());
        parents.add(parents2);

        TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX bean3 = threeAdapter.getCurrBean();
        TrialOccupationBean.ValueBean.ParentsDTO parents3 = new TrialOccupationBean.ValueBean.ParentsDTO();
        parents3.setLabel(bean3.getLabelX());
        parents3.setValue(bean3.getValue());
        parents.add(parents3);

        valueBean.setParents(parents);

        if (mOnSelectOccpationListener != null) {
            mOnSelectOccpationListener.selectOccpation(valueBean);
            dismiss();
        }
    }

    public interface OnSelectOccpationListener {
        void selectOccpation(TrialOccupationBean.ValueBean valueBean);
    }
}