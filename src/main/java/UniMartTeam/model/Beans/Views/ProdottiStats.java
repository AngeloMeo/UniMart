package UniMartTeam.model.Beans.Views;


public class ProdottiStats {

    private float qpv, incasso;
    private int pv;

    public float getQuantitaProdottiVenduti() {
        return qpv;
    }

    public void setQuantitaProdottiVenduti(float qpv) {
        this.qpv = qpv;
    }

    public float getIncasso() {
        return incasso;
    }

    public void setIncasso(float incasso) {
        this.incasso = incasso;
    }

    public int getProdottiVenduti() {
        return pv;
    }

    public void setProdottiVenduti(int pv) {
        this.pv = pv;
    }
}
