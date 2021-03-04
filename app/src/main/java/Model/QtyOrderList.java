package Model;

public class QtyOrderList {
    private String qtyFE;
    private String qtyBE;

    public QtyOrderList() {
    }

    public QtyOrderList(String qtyFE, String qtyBE) {
        this.qtyFE = qtyFE;
        this.qtyBE = qtyBE;
    }

    public String getQtyFE() {
        return qtyFE;
    }

    public void setQtyFE(String qtyFE) {
        this.qtyFE = qtyFE;
    }

    public String getQtyBE() {
        return qtyBE;
    }

    public void setQtyBE(String qtyBE) {
        this.qtyBE = qtyBE;
    }
}
