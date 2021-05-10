package UniMartTeam.model.Extractors;

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Spedizione;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.EnumForBeans.StatoOrdine;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdineExtractor {

    public static Ordine Extract(ResultSet rs, String alias, Utente ut, Coupon coupon, Spedizione sped) throws SQLException {

        if(rs != null){
            Ordine o = new Ordine();

            if(!alias.isEmpty())
                alias+=".";

            o.setNumeroOrdine(rs.getInt(alias+"numeroOrdine"));
            o.setStatoOrdine(StatoOrdine.StringToEnum(rs.getString(alias+"stato")));
            o.setFeedback(rs.getString(alias+"feedback"));
            o.setRicevutaPagamento(rs.getString(alias+"metodoDiPagamento"));
            if(rs.getDate(alias + "dataDiNascita") != null)
                o.setDataAcquisto(rs.getDate(alias+"dataAcquisto").toLocalDate());
            o.setCliente(ut);
            o.setCoupon(coupon);
            o.setSpedizione(sped);


            return o;
        }
        return null;
    }

}
