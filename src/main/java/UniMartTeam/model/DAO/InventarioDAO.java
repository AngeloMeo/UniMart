package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Beans.Inventario;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.Extractors.CouponExtractor;
import UniMartTeam.model.Extractors.InventarioExtractor;
import UniMartTeam.model.Extractors.OrdineExtractor;
import UniMartTeam.model.Extractors.UtenteExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO
{
   public static boolean doSave(Inventario inventario) throws SQLException
   {
      if(inventario == null)
         return false;

      try (Connection con = ConPool.getConnection() )
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").insert("indirizzo", "regione", "nome", "note", "cfResponsabile");

         return executeStatement(con, inventario, qb);
      }
   }

   public static boolean doUpdate(Inventario inventario) throws SQLException
   {
      if(inventario == null)
         return false;

      try (Connection con = ConPool.getConnection() )
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").update("indirizzo", "regione", "nome", "note", "cfResponsabile");
         qb.where("codiceInventario=" + inventario.getCodiceInventario());

         return executeStatement(con, inventario, qb);
      }
   }

   private static boolean executeStatement(Connection con, Inventario inventario, QueryBuilder qb) throws SQLException
   {
      if(con == null || inventario == null || qb == null)
         return false;

      try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
      {
         ps.setString(1, inventario.getIndirizzo());
         ps.setString(2, inventario.getRegione());
         ps.setString(3, inventario.getNome());
         ps.setString(4, inventario.getNote());
         ps.setString(5, inventario.getResponsabile().getCF());

         return (ps.executeUpdate() == 0) ? false : true;
      }
   }

   public static boolean doDelete(int codiceInventario) throws SQLException
   {
      if(codiceInventario <= 0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").delete().where("codiceInventario=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, codiceInventario);

            return (ps.executeUpdate() == 0) ? false : true;
         }
      }
   }

   public static List<Inventario> doRetriveAll() throws SQLException
   {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").select("*", "cfResponsabile AS CF");

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static List<Inventario> doRetriveAll(int offset, int size) throws SQLException
   {
      if(offset<1 || size<1)
         return null;

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").select("*", "cfResponsabile AS CF").limit(true);

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, size);

            return ListFiller(preparedStatement);
         }
      }
   }

   private static List<Inventario> ListFiller(PreparedStatement preparedStatement) throws SQLException
   {
      if(preparedStatement == null)
         return null;

      ResultSet rs = preparedStatement.executeQuery();
      ArrayList<Inventario> list = new ArrayList<>();

      while(rs.next())
         list.add(InventarioExtractor.Extract(rs, "", UtenteExtractor.Extract(rs, "")));

      if(list.isEmpty())
         list.add(new Inventario());

      return list;
   }

  /*TODO
   doRetriveByCond(codiceInventario, regione, idirizzo, nome, responsabile)
   addProdotto
   deleteProdotto
   updateProdotto
 */
}