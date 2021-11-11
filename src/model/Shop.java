package model;

public class Shop {
    private String shopId;
    private String shopName;
    private String shopPhoneNumber;
    private String shopAddress;

    public Shop() {
    }

    public Shop(String shopId, String shopName, String shopPhoneNumber, String shopAddress) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopPhoneNumber = shopPhoneNumber;
        this.shopAddress = shopAddress;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        shopName = shopName;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId='" + shopId + '\'' +
                ", ShopName='" + shopName + '\'' +
                ", shopPhoneNumber='" + shopPhoneNumber + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                '}';
    }
}
