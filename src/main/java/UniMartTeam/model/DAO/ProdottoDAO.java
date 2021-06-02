package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Extractors.CategoriaExtractor;
import UniMartTeam.model.Extractors.ProdottoExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO
{
   public static final int PREZZO = 0, PESO = 1, VOLUME = 2;

   public static boolean doSave(Prodotto p) throws SQLException
   {
      if (p == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "p").insert("nome", "prezzo", "peso",
                 "foto", "volumeOccupato", "descrizione", "nomeCategoria");

         return executeStatement(con, p, qb);
      }
   }

   private static boolean executeStatement(Connection con, Prodotto p, QueryBuilder qb) throws SQLException
   {
      if (con == null || p == null || qb == null)
         return false;

      try (PreparedStatement ps = con.prepareStatement(qb.getQuery(), Statement.RETURN_GENERATED_KEYS))
      {
         ps.setString(1, p.getNome());
         ps.setFloat(2, p.getPrezzo());
         ps.setFloat(3, p.getPeso());
         ps.setString(4, p.getFoto());
         ps.setFloat(5, p.getVolumeOccupato());
         ps.setString(6, p.getDescrizione());
         ps.setString(7, p.getCategoria().getNome());

         ps.executeUpdate();
         ResultSet rs = ps.getGeneratedKeys();

         if(rs.next())
            p.setCodiceIAN(rs.getInt(1));

         return true;
      }
   }

   public static boolean doUpdate(Prodotto p) throws SQLException
   {

      if (p == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "").update("nome", "prezzo", "peso", "foto",
                 "volumeOccupato", "descrizione", "nomeCategoria").where("codiceIAN=" + p.getCodiceIAN());

         return executeStatement(con, p, qb);
      }
   }

   public static boolean doDelete(int codiceIAN) throws SQLException
   {
      if (codiceIAN == 0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
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

   public static Prodotto doRetrieveByID(int codiceIAN) throws SQLException
   {
      if (codiceIAN == 0)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "").select().where("codiceIAN=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, codiceIAN);
            return ListFiller(ps, "").get(0);
         }
      }
   }

   public static List<Prodotto> doRetrieveAll() throws SQLException
   {
      try (Connection con = ConPool.getConnection())
      {
         String alias = "p";
         QueryBuilder qb = new QueryBuilder("prodotto", alias);
         try (PreparedStatement ps = con.prepareStatement(qb.select("*", alias + ".nomeCategoria AS nome").getQuery()))
         {
            return ListFiller(ps, alias);
         }
      }

   }

   public static List<Prodotto> doRetrieveAll(int offset, int size) throws SQLException
   {
      if (offset < 0 || size < 1)
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

   public static List<Prodotto> doRetrieveByCategoria(Categoria c) throws SQLException
   {
      if (c == null)
         return null;

      try (Connection con = ConPool.getConnection())
      {

         String alias = "p";

         QueryBuilder qb = new QueryBuilder("prodotto", alias).select().where(alias + ".nomeCategoria=" + c.getNome());

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps, alias);
         }
      }
   }

   //creo questo metodo per effettuare una query con condizione utilizzando anche un offset e una size
   public static List<Prodotto> doRetrieveByCondLimit(String cond, int offset, int size) throws SQLException
   {
      if (cond == null || cond.isEmpty() || offset < 0 || size < 1)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         String alias = "";

         QueryBuilder qb = new QueryBuilder("prodotto", alias).select().where(alias + cond).limit(true);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, offset);
            ps.setInt(2, size);

            return ListFiller(ps, alias);
         }
      }
   }
//TODO case? che roba è?
   public static List<Prodotto> doRetrieveByCond(int type, String param) throws SQLException
   {
      if (param.isEmpty())
         return null;

      try (Connection con = ConPool.getConnection())
      {

         QueryBuilder qb = new QueryBuilder("prodotto", "p").select();

         switch (type)
         {
            case PREZZO:
               qb.where("p.prezzo " + param);
               break;

            case PESO:
               qb.where("p.peso " + param);
               break;

            case VOLUME:
               qb.where("p.volume " + param);
               break;

            default:
               return null;
         }

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps, "p");
         }
      }
   }

   private static List<Prodotto> ListFiller(PreparedStatement preparedStatement, String alias) throws SQLException
   {
      if (preparedStatement == null)
         return null;

      ResultSet rs = preparedStatement.executeQuery();
      ArrayList<Prodotto> list = new ArrayList<>();

      if(!alias.isEmpty() && !alias.contains("."))
         alias+=".";

      while (rs.next())
      {
         Categoria c = new Categoria();
         c.setNome(rs.getString(alias+"nomeCategoria"));
         Prodotto p = ProdottoExtractor.Extract(rs, alias, c);
         list.add(p);
      }

      if (list.isEmpty())
         list.add(null);

      return list;
   }
}