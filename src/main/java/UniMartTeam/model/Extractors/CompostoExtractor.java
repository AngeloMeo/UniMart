package UniMartTeam.model.Extractors;

import UniMartTeam.model.Beans.Composto;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Prodotto;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompostoExtractor
{
   public static Composto Extract(ResultSet rs, String alias, Ordine or, Prodotto pr) throws SQLException
   {//TODO mod
      if (rs != null)
      {
         Composto c = new Composto();

         if (!alias.isEmpty())
            alias += ".";

         c.setPrezzo(rs.getFloat("prezzoAcquisto"));
         c.setQuantita(rs.getFloat("quantita"));
         c.setOrdine(or);
         c.setProdotto(pr);

         return c;
      }
      return null;
   }
}