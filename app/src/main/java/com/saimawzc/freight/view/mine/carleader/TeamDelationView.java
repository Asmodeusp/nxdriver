package com.saimawzc.freight.view.mine.carleader;

import com.saimawzc.freight.dto.face.FaceQueryDto;
import com.saimawzc.freight.dto.my.carleader.TeamDelationDto;
import com.saimawzc.freight.view.BaseView;

public interface TeamDelationView extends BaseView {
    void getDelation(TeamDelationDto delationDto);

    void getPersonifo(FaceQueryDto.Facedata facedata,String id);


}
