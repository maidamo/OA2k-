package weaver.formmode.customjavacode.modeexpand.market;

import weaver.formmode.customjavacode.AbstractModeExpandJavaCodeNew;
import weaver.formmode.customjavacode.modeexpand.market.service.InsertService;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;

public class CreateOrgOperation extends AbstractModeExpandJavaCodeNew {
    @Override
    public Map<String, String> doModeExpand(Map<String, Object> param) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            User user = (User)param.get("user");
            int billid = -1;//数据id
            int modeid = -1;//模块id
            RequestInfo requestInfo = (RequestInfo)param.get("RequestInfo");
            if(requestInfo!=null){
                billid = Util.getIntValue(requestInfo.getRequestid());
                modeid = Util.getIntValue(requestInfo.getWorkflowid());
                if(billid>0&&modeid>0){
                    //------请在下面编写业务逻辑代码------
                    System.out.println("数据id为: "+billid);
                    InsertService insertService=new InsertService();
                    insertService.insert(billid+"");
                }
            }
        } catch (Exception e) {
            result.put("errmsg","自定义出错信息");
            result.put("flag", "false");
        }
        return result;
    }
}
