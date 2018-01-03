package sample;

public class RowObj {
     String chainname;
     String shopcode;
     String code;
     String barcode;
     String barname;
     String itemweight;
     String itemtype;
     String manufactur;
     String price;
     double salesvalues;
     double salesitems;
     String promo;
     String prlabel;

    public RowObj(String chainname, String shopcode, String code, String barcode, String barname,
                  String itemweight, String itemtype, String manufactur, String price, double salesvalues, double salesitems, String promo, String prlabel) {
        this.chainname = chainname;
        this.shopcode = shopcode;
        this.code = code;
        this.barcode = barcode;
        this.barname = barname;
        this.itemweight = itemweight;
        this.itemtype = itemtype;
        this.manufactur = manufactur;
        this.price = price;
        this.salesvalues = salesvalues;
        this.salesitems = salesitems;
        this.promo = promo;
        this.prlabel = prlabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RowObj rowObj = (RowObj) o;

        if (shopcode != null ? !shopcode.equals(rowObj.shopcode) : rowObj.shopcode != null) return false;
        if (code != null ? !code.equals(rowObj.code) : rowObj.code != null) return false;
        return barcode != null ? barcode.equals(rowObj.barcode) : rowObj.barcode == null;
    }

    @Override
    public int hashCode() {
        int result = shopcode != null ? shopcode.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return
                 chainname + '\t' +
                 shopcode + '\t' +
                 code + '\t' +
                 barcode + '\t' +
                 barname + '\t' +
                 itemweight + '\t' +
                 itemtype + '\t' +
                 manufactur + '\t' +
                 price + '\t' +
                 salesitems + '\t' +
                 salesvalues + '\t' +
                 promo + '\t' +
                 prlabel + '\t'
                ;
    }
}

