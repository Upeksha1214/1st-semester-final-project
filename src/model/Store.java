package model;

public class Store {
    private String itemCode;
    private String description;
    private String unitPrice;
    private String qtyOnHand;
    private String disconnect;

    public Store(String itemCode, String description, String unitPrice, String qtyOnHand, String disconnect) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.disconnect = disconnect;
    }



    public Store() {

    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(String disconnect) {
        this.disconnect = disconnect;
    }
    @Override
    public String toString() {
        return "Store{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qtyOnHand='" + qtyOnHand + '\'' +
                ", disconnect='" + disconnect + '\'' +
                '}';
    }
}
