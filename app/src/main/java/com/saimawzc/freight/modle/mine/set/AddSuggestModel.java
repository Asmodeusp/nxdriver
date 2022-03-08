package com.saimawzc.freight.modle.mine.set;


import android.content.Context;

import com.saimawzc.freight.view.mine.set.AddSuggetsView;

/**
 * Created by Administrator on 2020/8/13.
 */

public interface AddSuggestModel {
    void submit(AddSuggetsView view, String miaoshu,String img);
    void showCamera(AddSuggetsView view, Context context);
}
