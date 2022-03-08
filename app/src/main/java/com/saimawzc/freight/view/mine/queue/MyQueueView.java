package com.saimawzc.freight.view.mine.queue;

import com.saimawzc.freight.dto.my.queue.CarQueueDto;
import com.saimawzc.freight.view.BaseView;

/**
 * Created by Administrator on 2020/8/10.
 */

public interface MyQueueView extends BaseView {
    void getQueue(CarQueueDto queueDto);
    void stopRefresh();
}
