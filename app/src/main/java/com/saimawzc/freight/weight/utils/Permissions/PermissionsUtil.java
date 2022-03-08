package com.saimawzc.freight.weight.utils.Permissions;

import android.app.Activity;
import android.content.Context;

import com.saimawzc.freight.R;
import com.saimawzc.freight.base.BaseActivity;
import com.werb.permissionschecker.PermissionChecker;

public class PermissionsUtil {




    public static boolean getPermission(BaseActivity context, String[] PERMISSIONS,
                           PermissionChecker permissionChecker){

        if(PERMISSIONS==null||PERMISSIONS.length>=0){
            context.showMessage("请配置权限");
            return false;

        }
        if(permissionChecker==null){
            permissionChecker=new PermissionChecker(context);
            permissionChecker.setTitle("权限提示");
            permissionChecker.setMessage("未获得应用运行所需的基本权限，请在设置中开启权限后再使用");
        }

        return  permissionChecker.isLackPermissions(PERMISSIONS);


    }

}
