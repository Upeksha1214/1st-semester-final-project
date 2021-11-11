package view.TM;

public class OrderTm {
    private String orderId;
    private String refId;
    private String shopId;
    private String itemCode;
    private String unitPrice;
    private String qty;
    private String orderQTY;

    public OrderTm() {
    }

    public OrderTm(String orderId, String refId, String shopId, String itemCode, String unitPrice, String qty, String orderQTY, String total) {
        this.orderId = orderId;
        this.refId = refId;
        this.shopId = shopId;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.orderQTY = orderQTY;
        this.total = total;
    }

    private String total;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getOrderQTY() {
        return orderQTY;
    }

    public void setOrderQTY(String orderQTY) {
        this.orderQTY = orderQTY;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "orderId='" + orderId + '\'' +
                ", refId='" + refId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qty='" + qty + '\'' +
                ", orderQTY='" + orderQTY + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
