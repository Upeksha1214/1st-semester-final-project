package view.TM;

public class ShopTm {
    private String shopId;
    private String shopName;
    private String shopAddress;
    private String shopPhoneNumber;

    public ShopTm() {
    }


    public ShopTm(String shopId, String shopName, String shopPhoneNumber, String shopAddress) {
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
        return "ShopTm{" +
                "shopId='" + shopId + '\'' +
                ", ShopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopPhoneNumber='" + shopPhoneNumber + '\'' +
                '}';
    }
}
