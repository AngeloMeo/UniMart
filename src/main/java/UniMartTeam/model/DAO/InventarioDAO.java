package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Inventario;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

   /*TODO
      doRetriveAll
      doRetriveByCond(codiceInventario, regione, idirizzo, nome, responsabile)
      addProdotto
      deleteProdotto
      updateProdotto
    */
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
}