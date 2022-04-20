package weaver.interfaces.zb.zt.dto;

/**
 * 店面新修改
 * Created with IntelliJ IDEA.
 * User: 张远
 * Date: 2021/6/8
 * Time: 22:59
 * Description: No Description
 */
public class ZTStoreModifyBill {
    /**加盟商ID*/private String custAccountId;
    /**店面id*/private String shopId;
    /**来源系统名称*/private String sourceSystem;
    /**下单系统唯一标识码*/private String sourceid;
    /**品牌id*/private String unitId;
    /**店面名称*/private String shopFullName;
    /**店面简称*/private String shopShortName;
    /**店面类型：店面类型 (1 沿街店  2 建材市场店 3 Shoppingmall店 4 写字楼店  7 卖场店  9 家装店  10 社区店)*/private String shopType;
    /**开展业务*/private String businessChannel;
    /**运营类型 ：（1 独立店  2 店中店(志法)  3 店中店(其他) 4 综合店）*/private String operateType;
    /**运营状态 :（1开业  2装修  3整顿  4改造 5运营  6 终止  ）*/private String operationStatus;
    /**展厅地址*/private String shopAddress;
    /**店面负责人*/private String shopOwner;
    /**负责人电话*/private String shopOwnerPhone;
    /**店面电话*/private String shopPhone;
    /**净面积*/private String shopUseArea;
    /**开业日期*/private String openDate;
    /**终止日期*/private String terminateDate;
    /**备注*/private String remarks;
    /**实体店面编码*/private String entityNo;
    /**统计调整状态 ：1 新增 2 持续  3 撤销  4 不计入*/private String statisticsAdjust;
    /**统计调整开始时间*/private String adjustStartDate;
    /**统计调整结束时间*/private String adjustEndDate;
    /**卖场名称*/private String storeName;

    public String getCustAccountId() {
        return custAccountId;
    }

    public void setCustAccountId(String custAccountId) {
        this.custAccountId = custAccountId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getShopFullName() {
        return shopFullName;
    }

    public void setShopFullName(String shopFullName) {
        this.shopFullName = shopFullName;
    }

    public String getShopShortName() {
        return shopShortName;
    }

    public void setShopShortName(String shopShortName) {
        this.shopShortName = shopShortName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getBusinessChannel() {
        return businessChannel;
    }

    public void setBusinessChannel(String businessChannel) {
        this.businessChannel = businessChannel;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public String getShopOwnerPhone() {
        return shopOwnerPhone;
    }

    public void setShopOwnerPhone(String shopOwnerPhone) {
        this.shopOwnerPhone = shopOwnerPhone;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopUseArea() {
        return shopUseArea;
    }

    public void setShopUseArea(String shopUseArea) {
        this.shopUseArea = shopUseArea;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(String terminateDate) {
        this.terminateDate = terminateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEntityNo() {
        return entityNo;
    }

    public void setEntityNo(String entityNo) {
        this.entityNo = entityNo;
    }

    public String getStatisticsAdjust() {
        return statisticsAdjust;
    }

    public void setStatisticsAdjust(String statisticsAdjust) {
        this.statisticsAdjust = statisticsAdjust;
    }

    public String getAdjustStartDate() {
        return adjustStartDate;
    }

    public void setAdjustStartDate(String adjustStartDate) {
        this.adjustStartDate = adjustStartDate;
    }

    public String getAdjustEndDate() {
        return adjustEndDate;
    }

    public void setAdjustEndDate(String adjustEndDate) {
        this.adjustEndDate = adjustEndDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
