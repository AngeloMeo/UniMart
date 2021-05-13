package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Composto;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Extractors.CategoriaExtractor;
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

    public static final int PREZZO=0, PESO=1, VOLUME=2;

    public static boolean doSave(Prodotto p) throws SQLException{

        if(p == null)
            return false;

        try (Connection con = ConPool.getConnection() ) {
            QueryBuilder qb = new QueryBuilder("prodotto", "p").insert("nome", "prezzo", "peso",
            "foto", "volumeOccupato", "descrizione", "nomeCategoria");

            return executeStatement(con, p, qb);
        }
    }

    private static boolean executeStatement(Connection con, Prodotto p, QueryBuilder qb) throws SQLException
    {
        if(con == null || p == null || qb == null)
            return false;

        try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
        {
            ps.setString(1, p.getNome());
            ps.setFloat(2, p.getPrezzo());
            ps.setFloat(3, p.getPeso());
            ps.setString(4, p.getFoto());
            ps.setFloat(5, p.getVolumeOccupato());
            ps.setString(6, p.getDescrizione());
            ps.setString(7, p.getCategoria().getNome());

            return (ps.executeUpdate() == 0) ? false :  true;
        }
    }

    public static boolean doUpdate(Prodotto p) throws SQLException {

        if(p == null)
            return false;

        try (Connection con = ConPool.getConnection()) {
            QueryBuilder qb = new QueryBuilder("prodotto", "").update("nome", "prezzo", "peso", "foto",
                    "volumeOccupato", "descrizione", "nomeCategoria").where("codiceIAN=" + p.getCodiceIAN());

            return executeStatement(con, p, qb);
        }
    }

    public static boolean doDelete(int codiceIAN) throws SQLException{

        if(codiceIAN == 0)
            return false;

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

    public static Prodotto doRetrieveByID(int codiceIAN) throws SQLException{

        if(codiceIAN == 0)
            return null;

        try(Connection con = ConPool.getConnection())
        {
            QueryBuilder qb = new QueryBuilder("prodotto", "").select().where("codiceIAN=?");

            try(PreparedStatement ps = con.prepareStatement(qb.toString()))
            {
                ps.setInt(1, codiceIAN);
                return ListFiller(ps, "").get(0);
            }
        }
    }

    public static List<Prodotto> doRetrieveAll() throws SQLException{

        try (Connection con = ConPool.getConnection())
        {
            String alias = "p";
            QueryBuilder qb = new QueryBuilder("prodotto", alias);
            try (PreparedStatement ps = con.prepareStatement(qb.select("*", alias + ".nomeCategoria AS nome").getQuery()))//TODO check
            {
                return ListFiller(ps, alias);
            }
        }

    }

    public static List<Prodotto> doRetrieveAll(int offset, int size) throws SQLException{

        if(offset<1 || size<1)
            return null;

        try (Connection con = ConPool.getConnection())
        {
            String alias = "p";
            QueryBuilder qb = new QueryBuilder("prodotto", alias);
            try (PreparedStatement ps = con.prepareStatement(qb.select("*", alias + ".nomeCategoria AS nome").limit(true).getQuery()))
            {
                ps.setInt(1, offset);
                ps.setInt(2, size);

                return ListFiller(ps, alias);

            }
        }
    }

    public static List<Prodotto> doRetrieveByCategoria(Categoria c) throws SQLException{

        if(c == null)
            return null;

        try(Connection con = ConPool.getConnection()){

            String alias = "p";

            QueryBuilder qb = new QueryBuilder("prodotto", alias).select().where(alias+".nomeCategoria="+c.getNome());

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
                return ListFiller(ps, alias);
            }

        }

    }

    public static List<Prodotto> doRetrieveByLimit(int type, String param) throws SQLException{

        if(type < 0 || type > 2 || param.isEmpty())
            return null;

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("prodotto", "p").select();

            switch(type){

                case PREZZO:
                    qb.where("p.prezzo "+param);
                break;

                case PESO:
                    qb.where("p.peso "+param);
                break;

                case VOLUME:
                    qb.where("p.volume "+param);
                break;

                default:
                    return null;
            }

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
                return ListFiller(ps, "p");
            }

        }

    }

    public static List<Composto> doRetrieveCompostoList() throws SQLException{
        //TODO quando Facciamo ordineDAO
    }

    public static List<Composto> doRetrievePossiedeList() throws SQLException{
        //TODO quando Facciamo inventarioDAO
    }


    private static List<Prodotto> ListFiller(PreparedStatement preparedStatement, String alias) throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Prodotto> list = new ArrayList<>();

        while(rs.next())
        {
            Categoria c = CategoriaExtractor.Extract(rs, "");//TODO check
            Prodotto p = ProdottoExtractor.Extract(rs, alias, c);
            list.add(p);
        }

        if(list.isEmpty())
            list.add(new Prodotto());

        return list;
    }

}
