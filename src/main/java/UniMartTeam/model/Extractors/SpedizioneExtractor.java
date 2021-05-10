package UniMartTeam.model.Extractors;

import UniMartTeam.model.Beans.Spedizione;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpedizioneExtractor
{
    public static Spedizione Extract(ResultSet rs, String alias) throws SQLException
    {
        if(rs != null)
        {
            Spedizione s = new Spedizione();

            if(!alias.isEmpty())
                alias += ".";

            s.setID(rs.getInt("ID"));
            s.setNome(rs.getString("nome"));
            s.setCosto(rs.getFloat("costo"));

            return s;
        }
        return null;
    }
}