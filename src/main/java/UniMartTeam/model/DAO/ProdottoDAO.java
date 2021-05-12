package UniMartTeam.model.DAO;

/*
* TODO:
*  doRetrieveByID
*  doRetrieveAll
*  doRetrieveAll(limit)
*  doRetrieveByCategoria(Categoria c)
*  doRetrieveByLimit(float limit, Const ProdottoDAO.prezzo/.peso/.volumeOccupato)
*/

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Extractors.ProdottoExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public static boolean doSave(Prodotto p) throws SQLException{

        try (Connection con = ConPool.getConnection() ) {
            QueryBuilder qb = new QueryBuilder("prodotto", "p").insert("codiceIAN", "nome", "prezzo", "peso",
            "foto", "volumeOccupato", "descrizione", "nomeCategoria");

            try (PreparedStatement ps = con.prepareStatement(qb.getQuery())) {

                ps.setInt(1, p.getCodiceIAN());
                ps.setString(2, p.getNome());
                ps.setFloat(3, p.getPrezzo());
                ps.setFloat(4, p.getPeso());
                ps.setString(5, p.getFoto());
                ps.setFloat(6, p.getVolumeOccupato());
                ps.setString(7, p.getDescrizione());
                ps.setString(8, p.getCategoria().getNome());

                if (ps.executeUpdate() == 0)
                    return false;

                return true;
            }
        }

    }

    public static boolean doUpdate(Coupon coupon) throws SQLException {
        try (Connection con = ConPool.getConnection()) {

            QueryBuilder qb = new QueryBuilder("coupon", "");

            try (PreparedStatement ps = con.prepareStatement(qb.select().where("numeroCoupon = " + coupon.getNumeroCoupon()).getQuery())) {
                ResultSet rs = ps.executeQuery();
                //update
                try (PreparedStatement pss = con.prepareStatement(qb.update("stato", "sconto", "cfCreatore").where("numeroCoupon=" + coupon.getNumeroCoupon()).getQuery()))
                {
                    pss.setString(1, coupon.getStatoCoupon().toString());
                    pss.setFloat(2, coupon.getSconto());
                    pss.setString(3, coupon.getCreatore().getCF());

                    if (pss.executeUpdate() == 0)
                        return false;

                    return true;
                }

            }
        }

    }

    public static boolean doDelete(int codiceIAN) throws SQLException{

        try (Connection con = ConPool.getConnection()) {
            QueryBuilder qb = new QueryBuilder("prodotto", "").delete().where("codiceIAN=?");

            try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
            {
                ps.setInt(1, codiceIAN);

                if (ps.executeUpdate() == 0)
                    return false;

                return true;
            }
        }
    }

    public static boolean doRetrieveByID(int codiceIAN) throws SQLException{

        if(codiceIAN == 0)
            return false;

        try(Connection con = ConPool.getConnection())
        {
            QueryBuilder qb = new QueryBuilder("prodotto", "").select().where("codiceIAN=?");

            try(PreparedStatement ps = con.prepareStatement(qb.toString()))
            {
                return
            }
        }
    }

    private static List<Prodotto> ListFiller(PreparedStatement preparedStatement, String alias) throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Prodotto> list = new ArrayList<>();

        while(rs.next())
        {
            Prodotto p = ProdottoExtractor.Extract(rs, alias);
            list.add(p);
        }

        if(list.isEmpty())
            list.add(new Prodotto());

        return list;
    }

}
