package UniMartTeam.model.DAO;

/*
* TODO:
*  PossiedeList CompostoList
*/

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

    public static boolean doUpdate(Prodotto p) throws SQLException {

        if(p == null)
            return false;

        try (Connection con = ConPool.getConnection()) {

            QueryBuilder qb = new QueryBuilder("prodotto", "");


            try (PreparedStatement ps = con.prepareStatement(qb.update("codiceIAN", "nome", "prezzo", "peso", "foto",
                    "volumeOccupato", "descrizione", "nomeCategoria").where("codiceIAN=" + p.getCodiceIAN()).getQuery()))
            {
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
            try (PreparedStatement ps = con.prepareStatement(qb.select("*", "p.nomeCategoria AS nome").getQuery()))//TODO check
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
            String alias = "cat";
            QueryBuilder qb = new QueryBuilder("categoria", alias);
            try (PreparedStatement ps = con.prepareStatement(qb.select("*", "p.nomeCategoria AS nome").limit(true).getQuery()))
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

    public static List<Prodotto> doRetrieveByLimit(int type, float value) throws SQLException{

        if(type < 0 || type > 2 || value < 0)
            return null;

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("prodotto", "p").select();

            switch(type){

                case PREZZO:
                    qb.where("p.prezzo < ?");
                break;

                case PESO:
                    qb.where("p.peso < ?");
                break;

                case VOLUME:
                    qb.where("p.volume < ?");
                break;

                default:
                    return null;
            }

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
                ps.setFloat(1, value);
                return ListFiller(ps, "p");
            }

        }

    }

    public static List<Composto> doRetrieveCompostoList() throws SQLException{



    }

//todo: riga 220 funziona con gli altri metodi? è stata scritta apposta per doRetrieveByCategoria
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
