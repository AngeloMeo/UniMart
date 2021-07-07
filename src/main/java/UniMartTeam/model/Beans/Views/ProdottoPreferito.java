package UniMartTeam.model.Beans.Views;

import UniMartTeam.model.Beans.Prodotto;

public class ProdottoPreferito extends Prodotto {

    private int nAcquisti;

    public int getnAcquisti() {
        return nAcquisti;
    }

    public void setnAcquisti(int nAcquisti) {
        this.nAcquisti = nAcquisti;
    }
}
