package UniMartTeam.model.Extractors;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.EnumForBeans.TipoUtente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteExtractor
{
   public static Utente Extract(ResultSet rs, String alias) throws SQLException
   {
      if (rs != null)
      {
         Utente u = new Utente();

         if (!alias.isEmpty())
            alias += ".";

         u.setCF(rs.getString(alias + "CF"));
         u.setNome(rs.getString(alias + "nome"));
         u.setCognome(rs.getString(alias + "cognome"));
         u.setViaCivico(rs.getString(alias + "viaCivico"));
         u.setFotoProfilo(rs.getString(alias + "fotoProfilo"));
         u.setCitta(rs.getString(alias + "citta"));
         u.setRegione(rs.getString(alias + "regione"));
         u.setTelefono(rs.getString(alias + "telefono"));
         u.setEmail(alias + "email");
         u.setToken(alias + "token");
         u.setTipo(TipoUtente.StringToEnum(rs.getString(alias + "tipo")));

         if(rs.getDate(alias + "dataDiNascita") != null)
            u.setDataDiNascita(rs.getDate(alias + "dataDiNascita").toLocalDate());

         return u;
      }
      return null;
   }
}