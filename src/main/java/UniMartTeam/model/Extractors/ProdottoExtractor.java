package UniMartTeam.model.Extractors;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.DAO.CategoriaDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdottoExtractor {

    public static Prodotto Extract(ResultSet rs, String alias, Categoria categoria) throws SQLException {

        if(rs != null) {
            Prodotto p = new Prodotto();

            if(!alias.isEmpty() && !alias.contains("."))
                alias+=".";
            p.setCodiceIAN(rs.getInt(alias+"codiceIAN"));
            p.setNome(rs.getString(alias+"nome"));
            p.setPrezzo(rs.getFloat(alias+"prezzo"));
            p.setPeso(rs.getFloat(alias+"peso"));
            p.setFoto(rs.getString(alias+"foto"));
            p.setVolumeOccupato(rs.getFloat(alias+"volumeOccupato"));
            p.setDescrizione(rs.getString(alias+"descrizione"));
            p.setCategoria(categoria);

            return p;
        }
        return null;
    }

}
