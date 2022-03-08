package com.saimawzc.freight.view.drivermain;

        import com.saimawzc.freight.dto.order.NeedOpenFenceDto;
        import com.saimawzc.freight.dto.order.mainindex.RobOrderDto;
        import com.saimawzc.freight.view.BaseView;

        import java.util.List;

/**
 * Created by Administrator on 2020/8/3.
 */

public interface DriverMainView extends BaseView {
        void getNeedFence(NeedOpenFenceDto dtos);
        void getPlanOrderList(List<RobOrderDto.robOrderData> dtos);
        void stopResh();
        void isLastPage(boolean isLastPage);

}
