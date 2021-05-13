package UniMartTeam.model.DAO;



import UniMartTeam.model.Beans.*;
import UniMartTeam.model.Extractors.CategoriaExtractor;
import UniMartTeam.model.Extractors.OrdineExtractor;
import UniMartTeam.model.Extractors.UtenteExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class OrdineDAO {


    public static boolean doSave(Ordine o) throws SQLException{

        if(o == null || o.getNumeroOrdine()==0)
            return false;


        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("ordine", "").insert("stato",
                    "feedback", "ricevutaPagamento", "dataAcquisto", "cfCliente", "metodoSpedizione");
            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){

                ps.setString(1, o.getStatoOrdine().toString());
                ps.setString(2, o.getFeedback());
                ps.setString(3, o.getRicevutaPagamento());
                ps.setDate(4, Date.valueOf(o.getDataAcquisto()));
                ps.setString(5, o.getCliente().getCF());
                ps.setInt(6, o.getSpedizione().getID());

                return (ps.executeUpdate() == 0) ? false : true;

            }


        }

    }

    public static boolean doUpdate(Ordine o) throws SQLException{

        if(o == null || o.getNumeroOrdine()==0)
            return false;

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("Ordine", "").update("stato",
                    "feedback", "ricevutaPagamento", "dataAcquisto", "cfCliente", "metodoSpedizione").
                    where("numeroOrdine = "+o.getNumeroOrdine());

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){

                ps.setString(1, o.getStatoOrdine().toString());
                ps.setString(2, o.getFeedback());
                ps.setString(3, o.getRicevutaPagamento());
                ps.setDate(4, Date.valueOf(o.getDataAcquisto()));
                ps.setString(5, o.getCliente().getCF());
                ps.setInt(6, o.getSpedizione().getID());

                return (ps.executeUpdate() == 0) ? false : true;

            }

        }


    }

    public static boolean doDelete(int numeroOrdine) throws SQLException{

        if(numeroOrdine == 0)
            return false;

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("ordine", "").delete().where("numeroOrdine="+numeroOrdine);

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
                return (ps.executeUpdate() == 0) ? true : false;
            }

        }

    }

    public static List<Ordine> doRetrieveAll() throws SQLException{

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("ordine", "").select();

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){

                return ListFiller(ps);

            }

        }

    }

    public static List<Ordine> doRetrieveAll(int offset, int size) throws SQLException{
        if(offset<0 || size<1)
            return null;

        try (Connection con = ConPool.getConnection())
        {
            String alias = "";
            QueryBuilder qb = new QueryBuilder("ordine", alias).select().limit(true);

            try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
            {
                ps.setInt(1, offset);
                ps.setInt(2, size);
                return ListFiller(ps);
            }
        }
    }

    public static Ordine doRetrieveByID(int numeroOrdine) throws SQLException{

        if(numeroOrdine == 0)
            return null;

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("ordine", "").select().where("numeroOrdine = "+numeroOrdine);

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
                return ListFiller(ps).get(0);
            }

        }

    }

    public static List<Ordine> doRetrieveByCond(String cfCliente) throws SQLException{

        if(cfCliente.isEmpty())
            return null;

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("ordine", "").select().where("cfCliente="+cfCliente);

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
                return ListFiller(ps);
            }

        }

    }

    public static List<Ordine> doRetrieveByCond(int metodoSpedizione) throws SQLException{

        if(metodoSpedizione == 0)
            return null;

        try(Connection con = ConPool.getConnection()){

            QueryBuilder qb = new QueryBuilder("ordine", "").select().where("metodoSpedizione="+metodoSpedizione);

            try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
                return ListFiller(ps);
            }

        }

    }

    private static List<Ordine> ListFiller(PreparedStatement preparedStatement) throws SQLException
    {
        if(preparedStatement == null)
            return null;

        ResultSet rs = preparedStatement.executeQuery();
        ArrayList<Ordine> list = new ArrayList<>();

        while(rs.next())
        {
            Ordine o1 = new Ordine();
            o1.setNumeroOrdine(rs.getInt("numeroOrdine"));
            Utente u1 = new Utente();
            u1.setCF(rs.getString("cfCliente"));
            Spedizione s1 = new Spedizione();
            s1.setID(rs.getInt("metodoSpedizione"));
            Ordine ordine = OrdineExtractor.Extract(rs, "", u1,
                    CouponDAO.doRetrieveByCond(o1),
                    s1);

            list.add(ordine);
        }

        if(list.isEmpty())
            list.add(new Ordine());

        return list;
    }

}
