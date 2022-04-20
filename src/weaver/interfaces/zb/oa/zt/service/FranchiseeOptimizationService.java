package weaver.interfaces.zb.oa.zt.service;

import org.apache.commons.lang.StringUtils;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.Util;
import weaver.interfaces.zb.oa.zt.bill.*;
import weaver.interfaces.zb.oa.zt.dao.BaseDao;
import weaver.interfaces.zb.oa.zt.dao.FranchiseeChangeApplicationDao;
import weaver.interfaces.zb.zt.dto.ZTModifyOptimizeBill;
import weaver.interfaces.zb.zt.dto.ZTModifyOptimizeDto;
import weaver.interfaces.zb.zt.service.ZTService;

import java.util.ArrayList;
import java.util.List;

public class FranchiseeOptimizationService {
    BaseDao baseDao=new BaseDao();

    FranchiseeChangeApplicationDao franchiseeChangeApplicationDao = new FranchiseeChangeApplicationDao();

    public void exec(String requestid, String tableName) throws Exception {
        FranchiseeChangeApplicationBill franchiseeChangeApplicationBill = franchiseeChangeApplicationDao.getFranchiseeChangeApplicationBill(requestid, tableName);
        List<FranchiseeChangeApplicationDto> franchiseeChangeApplicationDtos = franchiseeChangeApplicationDao.getFranchiseeChangeApplicationDtos(franchiseeChangeApplicationBill.getId(), tableName);
        ZTModifyOptimizeBill ztModifyOptimizeBill = getZTModifyOptimizeBill(franchiseeChangeApplicationBill);
        ZTModifyOptimizeDto ztModifyOptimizeDtos = getZTModifyOptimizeDtos(franchiseeChangeApplicationDtos);
        String oldFNumber = franchiseeChangeApplicationBill.getBgjmsfm();
        ztModifyOptimizeBill.setSitesAll(ztModifyOptimizeDtos);
        ZTService ztService = new ZTService();
        String fNumber = ztService.modifyOptimize(ztModifyOptimizeBill);
        System.out.println("加盟商变更发送后返回:" + fNumber);
        if(!"".equals(fNumber)){
            boolean isSuccess=baseDao.updateFNumber(requestid,tableName,fNumber);
            System.out.println("跟新F码是否成功: "+isSuccess);

        }
        FranchiseeInformationService franchiseeInformationService = new FranchiseeInformationService();
        StoreInformationService storeInformationService = new StoreInformationService();

        if(!"".equals(oldFNumber)){
            franchiseeInformationService.exec(oldFNumber);
            String oldcustAccountId = baseDao.getCustAccountId(oldFNumber);
            if (!"".equals(oldcustAccountId)) {
                List<String> oldaccountSiteId = baseDao.getAccountSiteId(oldcustAccountId);
                for (String str : oldaccountSiteId) {
                    CategoryInformationService categoryInformationService = new CategoryInformationService();
                    categoryInformationService.exec(str);

                }

                String oldaccountSiteIdonly=baseDao.getAccountSiteIdOnly(oldcustAccountId,franchiseeChangeApplicationBill.getPl());
                String oldAccountNumber=baseDao.getOldAccountNumber(oldaccountSiteIdonly);
                boolean isSuccess8=baseDao.updateOldAccountNumber(oldAccountNumber,oldaccountSiteIdonly);
                System.out.println("旧跟新建模F码是否成功: "+isSuccess8);

                List<String> oldshopId = baseDao.getShopId(oldcustAccountId);
                storeInformationService.exec(oldshopId);
            }
        }

        franchiseeInformationService.exec(fNumber);
        String custAccountId = baseDao.getCustAccountId(fNumber);
        if (!"".equals(custAccountId)) {
            List<String> accountSiteId = baseDao.getAccountSiteId(custAccountId);
            for (String str : accountSiteId) {
                CategoryInformationService categoryInformationService = new CategoryInformationService();
                categoryInformationService.exec(str);
            }

            String accountSiteIdonly=baseDao.getAccountSiteIdOnly(custAccountId,franchiseeChangeApplicationBill.getPl());
            String oldAccountNumber=baseDao.getOldAccountNumber(accountSiteIdonly);
            boolean isSuccess8=baseDao.updateOldAccountNumber(oldAccountNumber,accountSiteIdonly);
            System.out.println("新跟新建模F码是否成功: "+isSuccess8);

            List<String> shopId = baseDao.getShopId(custAccountId);

            storeInformationService.exec(shopId);

            String zrpl=franchiseeChangeApplicationBill.getZrpl();
            boolean isSuccess2=baseDao.updateCategoryStatus(zrpl,"3");
            System.out.println("跟新转让品类的统计调整状态是否成功: "+isSuccess2);

            String uniteId=franchiseeChangeApplicationDtos.get(0).getPlid();
            String accountSiteIdNew=baseDao.getAccountSiteId(custAccountId,uniteId);
            boolean isSuccess3=baseDao.updateCategoryMes(franchiseeChangeApplicationDtos.get(0).getTjdzzt(),franchiseeChangeApplicationDtos.get(0).getTjdzkssj(),franchiseeChangeApplicationDtos.get(0).getTjdzjssj(),accountSiteIdNew);
            System.out.println("跟新品类状态开始时间结束时间是否成功: "+isSuccess3);

        }


        if(!"".equals(fNumber)){
            ModeRightInfo ModeRightInfo=new ModeRightInfo();
            ModeRightInfo.setNewRight(true);

            boolean isTrueContract=true;
            boolean isTrueLetter=true;
            String bgkzxx=franchiseeChangeApplicationBill.getBgkzxx();
            if(!"".equals(bgkzxx)){
                String pl=franchiseeChangeApplicationBill.getPl();
                //签约信息
                if(bgkzxx.contains("0")){
                    List<ContractEntity> list=baseDao.getContractMes(oldFNumber);
                    System.out.println("list大小为: "+list.size());
                    for(int i=0;i<list.size();i++){
                        if(pl.equals(list.get(i).getPlmc())){
                            System.out.println("签约信息"+i+": "+list.get(i));
                            boolean isSuccessContract=baseDao.insertContract(list.get(i),fNumber);
                            System.out.println("合同插入是否成功: "+isSuccessContract);
                            if(!isSuccessContract){
                                isTrueContract=false;
                            }
                        }
                    }
                    if(isTrueContract){
                        List<PermissionReconstructionEntity> permissionList=baseDao.getContract("uf_jmhtxx",fNumber);
                        for(PermissionReconstructionEntity permissionReconstructionEntity:permissionList){
                            String createrId=permissionReconstructionEntity.getCreaterid();
                            String formmodeId=permissionReconstructionEntity.getFormmodeid();
                            String billId=permissionReconstructionEntity.getBillid();
                            ModeRightInfo.editModeDataShare(Util.getIntValue(createrId),Util.getIntValue(formmodeId),Util.getIntValue(billId));
                        }
                    }
                }
                //函件信息
                if(bgkzxx.contains("1")){
                    List<LetterEntity> list=baseDao.getLetterMes(oldFNumber);
                    FranchiseeEntity franchiseeEntity=baseDao.getFranchiseeMes(fNumber);
                    for(int i=0;i<list.size();i++){
                        if(pl.equals(list.get(i).getUNIT_ID())){
                            boolean isSuccessLetter=baseDao.insertLetter(list.get(i),franchiseeEntity);
                            System.out.println("函件信息插入是否成功: "+isSuccessLetter);
                            if(!isSuccessLetter){
                                isTrueLetter=false;
                            }
                        }
                    }
                    if(isTrueLetter){
                        List<PermissionReconstructionEntity> permissionList=baseDao.getLetter("uf_jmwlhj",fNumber);
                        for(PermissionReconstructionEntity permissionReconstructionEntity:permissionList){
                            String createrId=permissionReconstructionEntity.getCreaterid();
                            String formmodeId=permissionReconstructionEntity.getFormmodeid();
                            String billId=permissionReconstructionEntity.getBillid();
                            ModeRightInfo.editModeDataShare(Util.getIntValue(createrId),Util.getIntValue(formmodeId),Util.getIntValue(billId));
                        }
                    }
                }
            }

        }

    }

    private ZTModifyOptimizeBill getZTModifyOptimizeBill(FranchiseeChangeApplicationBill franchiseeChangeApplicationBill){
        ZTModifyOptimizeBill ztModifyOptimizeBill = new ZTModifyOptimizeBill();
        ztModifyOptimizeBill.setOrganizationType(franchiseeChangeApplicationBill.getZzlx());
        ztModifyOptimizeBill.setCountry(franchiseeChangeApplicationBill.getGj());
        ztModifyOptimizeBill.setProvince(franchiseeChangeApplicationBill.getSf());
        ztModifyOptimizeBill.setCity(franchiseeChangeApplicationBill.getCs());
        ztModifyOptimizeBill.setCounty(franchiseeChangeApplicationBill.getQx());
        ztModifyOptimizeBill.setAccountNumber(franchiseeChangeApplicationBill.getJmsfm());
        ztModifyOptimizeBill.setAccountName(franchiseeChangeApplicationBill.getKhmc());
        ztModifyOptimizeBill.setAccountShortName(franchiseeChangeApplicationBill.getKhjc());
        ztModifyOptimizeBill.setTaxRegistrationNum(franchiseeChangeApplicationBill.getTyshxydmzch());
        ztModifyOptimizeBill.setLegalPerson(franchiseeChangeApplicationBill.getFrjyz());
        ztModifyOptimizeBill.setLegalIdentification(franchiseeChangeApplicationBill.getFrjyzsfz());
        ztModifyOptimizeBill.setRegistrationAddress(franchiseeChangeApplicationBill.getYyzzzcdz());
        ztModifyOptimizeBill.setContactPhone(franchiseeChangeApplicationBill.getFrjyzsjh());
        ztModifyOptimizeBill.setAccountOwner(franchiseeChangeApplicationBill.getSjkzrgs());
        ztModifyOptimizeBill.setSiteCode(franchiseeChangeApplicationBill.getZrpl());
        ztModifyOptimizeBill.setOldcustAccountId(franchiseeChangeApplicationBill.getXzzrkh());
        ztModifyOptimizeBill.setOldAccountNumber(franchiseeChangeApplicationBill.getBgjmsfm());
        ztModifyOptimizeBill.setDyAccountSiteId(franchiseeChangeApplicationBill.getBghyhdjmsppid());
        List<String> copyStrList = new ArrayList<>();
        String[] bgkzxxs = franchiseeChangeApplicationBill.getBgkzxx().split(",");
        for (String str : bgkzxxs){
            if (str.equals("0")){
                copyStrList.add("QY");
            }else if (str.equals("1")){
                copyStrList.add("DM");
            }else if (str.equals("2")){
                copyStrList.add("HJ");
            }else if (str.equals("3")){
                copyStrList.add("MB");
            }else if (str.equals("4")){
                copyStrList.add("YP");
            }
        }

        ztModifyOptimizeBill.setCopyStr(StringUtils.join(copyStrList.toArray(), ",")+"," );
        ztModifyOptimizeBill.setCustAccountId(franchiseeChangeApplicationBill.getXzjms());
        ztModifyOptimizeBill.setUnitId(franchiseeChangeApplicationBill.getPl());

        ztModifyOptimizeBill.setAccountLevel(franchiseeChangeApplicationBill.getXzqlb());
        ztModifyOptimizeBill.setAdministrativeArea(franchiseeChangeApplicationBill.getXzqy());
        ztModifyOptimizeBill.setAccountIdentification(franchiseeChangeApplicationBill.getSjkzrsfz());

        ztModifyOptimizeBill.setTown(franchiseeChangeApplicationBill.getZ());
        return ztModifyOptimizeBill;
    }

    private ZTModifyOptimizeDto getZTModifyOptimizeDtos( List<FranchiseeChangeApplicationDto> franchiseeChangeApplicationDtos) throws Exception {

//        ZTModifyOptimizeDto ztModifyOptimizeDtos = new ZTModifyOptimizeDto();
        ZTModifyOptimizeDto ztModifyOptimizeDto = new ZTModifyOptimizeDto();

        for (FranchiseeChangeApplicationDto franchiseeChangeApplicationDto : franchiseeChangeApplicationDtos){

            ztModifyOptimizeDto.setOperationStatus(franchiseeChangeApplicationDto.getYwzt());
            ztModifyOptimizeDto.setProvince(franchiseeChangeApplicationDto.getSf());
            ztModifyOptimizeDto.setCity(franchiseeChangeApplicationDto.getCs());
            ztModifyOptimizeDto.setCountry(franchiseeChangeApplicationDto.getGj());
            ztModifyOptimizeDto.setCounty(franchiseeChangeApplicationDto.getQx());
            ztModifyOptimizeDto.setSiteCode(franchiseeChangeApplicationDto.getKhlx());
            ztModifyOptimizeDto.setUnitId(franchiseeChangeApplicationDto.getPlid());
            ztModifyOptimizeDto.setStatisticsAdjust(franchiseeChangeApplicationDto.getTjdzzt());
            ztModifyOptimizeDto.setAdjustStartDate(franchiseeChangeApplicationDto.getTjdzkssj());
            ztModifyOptimizeDto.setAdjustEndDate(franchiseeChangeApplicationDto.getTjdzjssj());

            ztModifyOptimizeDto.setAuthorizationNo(franchiseeChangeApplicationDto.getAUTHORIZATION_NO());
            ztModifyOptimizeDto.setRemarks(franchiseeChangeApplicationDto.getREMARKS());
            ztModifyOptimizeDto.setShipToPerson(franchiseeChangeApplicationDto.getSHIP_TO_PERSON());
            ztModifyOptimizeDto.setShipToAddress(franchiseeChangeApplicationDto.getSHIP_TO_ADDRESS());
            ztModifyOptimizeDto.setShipToPhone(franchiseeChangeApplicationDto.getSHIP_TO_PHONE());
            ztModifyOptimizeDto.setCreditMode(franchiseeChangeApplicationDto.getCREDIT_MODE());
            ztModifyOptimizeDto.setCreditAmount(franchiseeChangeApplicationDto.getCREDIT_AMOUNT());
            ztModifyOptimizeDto.setCurrencyCode(franchiseeChangeApplicationDto.getCURRENCY_CODE());
            if(!"".equals(franchiseeChangeApplicationDto.getTERRITORY_ID())){
                String ztTerritory=baseDao.getTerritoryId(franchiseeChangeApplicationDto.getTERRITORY_ID());
                if("".equals(ztTerritory)){
                    throw new Exception("营销区域转换失败!");
                }else{
                    ztModifyOptimizeDto.setTerritoryId(ztTerritory);
                }
            }
            ztModifyOptimizeDto.setJoinDate(franchiseeChangeApplicationDto.getJOIN_DATE());
            ztModifyOptimizeDto.setIsRecord(franchiseeChangeApplicationDto.getIS_RECORD());
            ztModifyOptimizeDto.setRecordNumber(franchiseeChangeApplicationDto.getRECORD_NUMBER());
            ztModifyOptimizeDto.setRecordDate(franchiseeChangeApplicationDto.getRECORD_DATE());
            ztModifyOptimizeDto.setBankAccountName(franchiseeChangeApplicationDto.getBANK_ACCOUNT_NAME());
            ztModifyOptimizeDto.setBankBranchName(franchiseeChangeApplicationDto.getBANK_BRANCH_NAME());
            ztModifyOptimizeDto.setBankAccountNum(franchiseeChangeApplicationDto.getBANK_ACCOUNT_NUM());

            ztModifyOptimizeDto.setCityAddress(franchiseeChangeApplicationDto.getCITY_ADDRESS());
            ztModifyOptimizeDto.setRelevance("4");
        }

        return ztModifyOptimizeDto;

    }
}
