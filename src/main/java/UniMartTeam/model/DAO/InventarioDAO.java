package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.*;
import UniMartTeam.model.Extractors.InventarioExtractor;
import UniMartTeam.model.Extractors.PossiedeExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO
{
   public static final int CODICE_INVENTARIO = 0, REGIONE = 1, INDIRIZZO = 2, NOME = 3, RESPONSABILE = 4;

   public static int doSave(Inventario inventario) throws SQLException
   {
      if(inventario == null)
         return 0;

      try (Connection con = ConPool.getConnection() )
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").insert("indirizzo", "regione", "nome", "note", "cfResponsabile");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery(), Statement.RETURN_GENERATED_KEYS))
         {
            ps.setString(1, inventario.getIndirizzo());
            ps.setString(2, inventario.getRegione());
            ps.setString(3, inventario.getNome());
            ps.setString(4, inventario.getNote());
            ps.setString(5, inventario.getResponsabile().getCF());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next())
               return rs.getInt(1);

            return -1;
         }
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

         return ps.executeUpdate() != 0;
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

            return ps.executeUpdate() != 0;
         }
      }
   }

   public static List<Inventario> doRetrieveAll() throws SQLException
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

   public static List<Inventario> doRetrieveAll(int offset, int size) throws SQLException
   {
      if(offset < 0 || size < 1)
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
      {
         Inventario inventario = InventarioExtractor.Extract(rs, "", null);
         Utente utente = UtenteDAO.doRetrieveByCond(UtenteDAO.CF, "'" + rs.getString("CF") + "'").get(0);

         inventario.setResponsabile(utente);

         list.add(inventario);
      }

      if(list.isEmpty())
         list.add(null);

      return list;
   }

   /**
    * il parametro mode specifica l'attributo su cui eseguire il filtraggio
    * la stringa param costituisce una condizione nel caso in cui il mode=CODICE_INVENTARIO,
    * negli altri caso costituisce il valore di confronto
    */
   public static List<Inventario> doRetrieveByCond(int mode, String param) throws SQLException
   {
      try (Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("inventario", "").select("*", "cfResponsabile AS CF");

         switch (mode)
         {
            case CODICE_INVENTARIO:
               qb.where("codiceInventario=" + param);
               break;

            case REGIONE:
               qb.where("regione=" + param);
               break;
            case NOME:
               qb.where("nome=" + param);
               break;

            case INDIRIZZO:
               qb.where("indirizzo=" + param);
               break;

            case RESPONSABILE:
               qb.where("cfResponsabile=" + param);
               break;

            default:
               return null;
         }

         try (PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static boolean addProdottoInventario(Possiede possiede) throws SQLException
   {
      if(possiede != null && possiede.getProdotto() != null && possiede.getGiacenza() > 0 && possiede.getInventario() != null)
      {
         try (Connection con = ConPool.getConnection() )
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").insert("idInventario", "idProdotto", "giacenza");

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               pss.setInt(1, possiede.getInventario().getCodiceInventario());
               pss.setInt(2, possiede.getProdotto().getCodiceIAN());
               pss.setFloat(3, possiede.getGiacenza());

               return pss.executeUpdate() != 0;
            }
         }
      }
      return false;
   }

   public static boolean infoQuantitaProdottoInventario(Composto composto) throws SQLException
   {
      if(composto != null && composto.getQuantita() >= 0)
      {
         try (Connection con = ConPool.getConnection())
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").select("idProdotto AS 'IAN', SUM(giacenza) AS 'giacenza'");
            qb.where("idProdotto=" + composto.getProdotto().getCodiceIAN()).groupBy("idProdotto").having("giacenza >= " + composto.getQuantita());

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               return pss.executeQuery().next();
            }
         }
      }
      return false;
   }

   public static List<Possiede> doRetrieveInventarioProducts(Composto composto) throws SQLException
   {
      if(composto != null && composto.getQuantita() >= 0)
      {
         try (Connection con = ConPool.getConnection())
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").select();
            qb.where("idProdotto=" + composto.getProdotto().getCodiceIAN()).orderBy("giacenza desc");

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               ResultSet rs = pss.executeQuery();
               List<Possiede> list = new ArrayList<>();

               while(rs.next())
               {
                  Inventario inventario = new Inventario();
                  Prodotto prodotto = new Prodotto();
                  prodotto.setCodiceIAN(rs.getInt("idProdotto"));
                  inventario.setCodiceInventario(rs.getInt("idInventario"));

                  list.add(PossiedeExtractor.Extract(rs, "", inventario, prodotto));
               }

               return list;
            }
         }
      }
      return null;
   }

   public static boolean updateProdottoInventario(Possiede possiede) throws SQLException
   {
      if(possiede != null && possiede.getProdotto() != null && possiede.getGiacenza() >= 0 && possiede.getInventario() != null)
      {
         try (Connection con = ConPool.getConnection() )
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").update("giacenza");
            qb.where("idInventario=" + possiede.getInventario().getCodiceInventario() + " and idProdotto=" + possiede.getProdotto().getCodiceIAN());

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               pss.setFloat(1, possiede.getGiacenza());

               return pss.executeUpdate() != 0;
            }
         }
      }
      return false;
   }

   public static boolean updateQuantitaProdottoInventario(Possiede possiede) throws SQLException
   {
      if(possiede != null && possiede.getProdotto() != null && possiede.getGiacenza() >= 0)
      {
         try (Connection con = ConPool.getConnection())
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").select();
            qb.where("idProdotto=" + possiede.getProdotto().getCodiceIAN()).orderBy("giacenza").limit(false);

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               pss.setInt(1, 1);

               ResultSet rs = pss.executeQuery();

               if(rs.next())
               {
                  Inventario inventario = new Inventario();
                  inventario.setCodiceInventario(rs.getInt("idInventario"));
                  possiede.setGiacenza(possiede.getGiacenza() + rs.getFloat("giacenza"));
                  possiede.setInventario(inventario);

                  return updateProdottoInventario(possiede);
               }
            }

            Inventario inventario = new Inventario();
            inventario.setCodiceInventario(getCodiceInventarioRandom());
            possiede.setInventario(inventario);
            return addProdottoInventario(possiede);
         }
      }
      return false;
   }

   public static int getCodiceInventarioRandom() throws SQLException
   {
      try (Connection con = ConPool.getConnection() )
      {
         QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").select("idInventario").orderBy("RAND()").limit(false);

         try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
         {
            pss.setInt(1, 1);

            ResultSet rs = pss.executeQuery();

            if(rs.next())
               return rs.getInt(1);
         }
      }

      return 0;
   }

   public static boolean deleteProdottoInventario(Possiede possiede) throws SQLException
   {
      if(possiede != null)
      {
         try (Connection con = ConPool.getConnection() )
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").delete();
            qb.where("idInventario=" + possiede.getInventario().getCodiceInventario() + " and idProdotto=" + possiede.getProdotto().getCodiceIAN());

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               return pss.executeUpdate() != 0;
            }
         }
      }
      return false;
   }

   public static int countInventari() throws SQLException {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("inventario", "i");
         qb.select("COUNT(*)");
         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
               return rs.getInt(1);
         }
      }
      return 0;
   }

   public static boolean getProdottoInventarioStock(Possiede possiede) throws SQLException
   {
      if(possiede != null && possiede.getProdotto() != null && possiede.getInventario() != null)
      {
         try (Connection con = ConPool.getConnection() )
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").select("giacenza");
            qb.where("idInventario=" + possiede.getInventario().getCodiceInventario() + " and idProdotto=" + possiede.getProdotto().getCodiceIAN());

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               ResultSet rs = pss.executeQuery();

               if (rs.next())
               {
                  possiede.setGiacenza(rs.getFloat("giacenza"));
                  return true;
               }
            }
         }
      }

      return false;
   }
}