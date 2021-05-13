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
   /*TODO
      doUpdate
      doDelete
      doRetriveAll
      doRetriveByCond(codiceInventario, regione, idirizzo, nome, responsabile)
      addProdotto
      deleteProdotto
      updateProdotto
    */

   public static boolean doSave(Inventario inventario) throws SQLException
   {
      if(inventario == null)
         return false;

      try (Connection con = ConPool.getConnection() )
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").insert("indirizzo", "regione", "nome", "note", "cfResponsabile");

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
}