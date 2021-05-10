package UniMartTeam.model.Extractors;

import UniMartTeam.model.Beans.Inventario;
import UniMartTeam.model.Beans.Possiede;
import UniMartTeam.model.Beans.Prodotto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PossiedeExtractor
{
   public static Possiede Extract(ResultSet rs, String alias, Inventario in, Prodotto pr) throws SQLException
   {
      if (rs != null)
      {
         Possiede p = new Possiede();

         if (!alias.isEmpty())
            alias += ".";

         p.setGiacenza(rs.getFloat("giacenza"));
         p.setInventario(in);
         p.setProdotto(pr);

         return p;
      }
      return null;
   }
}