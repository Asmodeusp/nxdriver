package com.saimawzc.freight.modle.mine.person;

import com.saimawzc.freight.base.BaseListener;
import com.saimawzc.freight.view.login.LoginView;
import com.saimawzc.freight.view.mine.person.ChangeRoleView;

/**
 * Created by Administrator on 2020/8/13.
 */

public interface ChangeRoleModel {
    void changeRole(ChangeRoleView view, BaseListener listener, int role);
}
