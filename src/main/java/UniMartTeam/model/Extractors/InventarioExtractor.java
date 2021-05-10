package UniMartTeam.model.Extractors;

import UniMartTeam.model.Beans.Inventario;
import UniMartTeam.model.Beans.Utente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventarioExtractor
{
   public static Inventario Extract(ResultSet rs, String alias, Utente re) throws SQLException
   {
      if (rs != null)
      {
         Inventario i = new Inventario();

         if (!alias.isEmpty())
            alias += ".";

         i.setCodiceInventario(rs.getInt("codiceInventario"));
         i.setIndirizzo(rs.getString(alias + "indirizzo"));
         i.setRegione(rs.getString(alias + "regione"));
         i.setNome(rs.getString(alias + "nome"));
         i.setNote(rs.getString(alias + "note"));
         i.setResponsabile(re);

         return i;
      }
      return null;
   }
}