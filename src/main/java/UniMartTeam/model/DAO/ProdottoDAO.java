package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Categoria;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Beans.Views.ProdottoPreferito;
import UniMartTeam.model.Extractors.ProdottoExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO
{
   public static final int PREZZO = 0, PESO = 1, VOLUME = 2;

   public static boolean doSave(Prodotto p) throws SQLException
   {
      if (p == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "p").insert("nome", "prezzo", "peso",
                 "foto", "volumeOccupato", "descrizione", "nomeCategoria");

         return executeStatement(con, p, qb);
      }
   }

   private static boolean executeStatement(Connection con, Prodotto p, QueryBuilder qb) throws SQLException
   {
      if (con == null || p == null || qb == null)
         return false;

      try (PreparedStatement ps = con.prepareStatement(qb.getQuery(), Statement.RETURN_GENERATED_KEYS))
      {
         ps.setString(1, p.getNome());
         ps.setFloat(2, p.getPrezzo());
         ps.setFloat(3, p.getPeso());
         ps.setString(4, p.getFoto());
         ps.setFloat(5, p.getVolumeOccupato());
         ps.setString(6, p.getDescrizione());
         ps.setString(7, p.getCategoria().getNome());

         ps.executeUpdate();
         ResultSet rs = ps.getGeneratedKeys();

         if(rs.next())
            p.setCodiceIAN(rs.getInt(1));

         return true;
      }
   }

   public static boolean doUpdate(Prodotto p) throws SQLException
   {

      if (p == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "").update("nome", "prezzo", "peso", "foto",
                 "volumeOccupato", "descrizione", "nomeCategoria").where("codiceIAN=" + p.getCodiceIAN());

         return executeStatement(con, p, qb);
      }
   }

   public static boolean doDelete(int codiceIAN) throws SQLException
   {
      if (codiceIAN == 0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "").delete().where("codiceIAN=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, codiceIAN);

            if (ps.executeUpdate() == 0)
               return false;

            return true;
         }
      }
   }

   public static Prodotto doRetrieveByID(int codiceIAN) throws SQLException
   {
      if (codiceIAN == 0)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "").select().where("codiceIAN=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, codiceIAN);
            return ListFiller(ps, "").get(0);
         }
      }
   }

   public static List<Prodotto> doRetrieveAll() throws SQLException
   {
      try (Connection con = ConPool.getConnection())
      {
         String alias = "p";
         QueryBuilder qb = new QueryBuilder("prodotto", alias);
         try (PreparedStatement ps = con.prepareStatement(qb.select("*", alias + ".nomeCategoria AS nome").getQuery()))
         {
            return ListFiller(ps, alias);
         }
      }

   }

   public static List<Prodotto> doRetrieveAll(int offset, int size) throws SQLException
   {
      if (offset < 0 || size < 1)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         String alias = "p";
         QueryBuilder qb = new QueryBuilder("prodotto", alias);
         try (PreparedStatement ps = con.prepareStatement(qb.select("*", alias + ".nomeCategoria AS nome").limit(true).getQuery()))
         {
            ps.setInt(1, offset);
            ps.setInt(2, size);

            return ListFiller(ps, alias);

         }
      }
   }

   public static List<Prodotto> doRetrieveNamesLike(String key) throws SQLException{

      if(key.isBlank())
         return null;

      try (Connection con = ConPool.getConnection())
      {
         String alias = "";

         QueryBuilder qb = new QueryBuilder("prodotto", alias).select().where(alias + "codiceIAN like '"+key+"%' OR nome like '"+key+"%'");

         try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
            ResultSet rs = ps.executeQuery();
            ArrayList<Prodotto> list = new ArrayList<>();

            if(!alias.isEmpty() && !alias.contains("."))
               alias+=".";

            while (rs.next())
            {
               Prodotto p = new Prodotto();
               p.setCodiceIAN(rs.getInt(alias+"codiceIAN"));
               p.setNome(rs.getString(alias+"nome"));
               list.add(p);
            }

            if (list.isEmpty())
               list.add(null);

            return list;
         }


      }

   }

   public static List<Prodotto> doRetrieveByCategoria(Categoria c) throws SQLException
   {
      if (c == null)
         return null;

      try (Connection con = ConPool.getConnection())
      {

         String alias = "p";

         QueryBuilder qb = new QueryBuilder("prodotto", alias).select().where(alias + ".nomeCategoria=" + c.getNome());

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps, alias);
         }
      }
   }

   //creo questo metodo per effettuare una query con condizione utilizzando anche un offset e una size
   public static List<Prodotto> doRetrieveByCondLimit(String cond, int offset, int size) throws SQLException
   {
      if (cond == null || cond.isEmpty() || offset < 0 || size < 1)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         String alias = "";

         QueryBuilder qb = new QueryBuilder("prodotto", alias).select().where(alias + cond).limit(true);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, offset);
            ps.setInt(2, size);

            return ListFiller(ps, alias);
         }
      }
   }

   public static List<Prodotto> doRetrieveByCond(int type, String param) throws SQLException
   {
      if (param.isEmpty())
         return null;

      try (Connection con = ConPool.getConnection())
      {

         QueryBuilder qb = new QueryBuilder("prodotto", "p").select();

         switch (type)
         {
            case PREZZO:
               qb.where("p.prezzo " + param);
               break;

            case PESO:
               qb.where("p.peso " + param);
               break;

            case VOLUME:
               qb.where("p.volume " + param);
               break;

            default:
               return null;
         }

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps, "p");
         }
      }
   }

   public static List<Prodotto> prodottiRandom(int size) throws SQLException
   {
      String alias = "p";
      QueryBuilder qb = new QueryBuilder("prodotto", alias).select().orderBy("RAND()").limit(false);

      return exexuteQuery(qb, alias, 0, size);
   }

   public static List<Prodotto> prodottiPiuAcquistati(int size) throws SQLException
   {
      String alias = "p";
      QueryBuilder qb = new QueryBuilder("prodotto", alias).select(alias + ".*").innerJoin("ordine_prodotto", "op");
      qb.on("p.codiceIAN = op.idProdotto").groupBy("p.codiceIAN").orderBy("op.quantita DESC").limit(false);

      return exexuteQuery(qb, alias, 0, size);
   }

   public static List<Prodotto> exexuteQuery(QueryBuilder query, String alias, int offset, int size) throws SQLException
   {
      if ((query != null) && (offset >= 0) && (size > 0) && (alias != null))
      {
         try (Connection con = ConPool.getConnection())
         {
            try (PreparedStatement ps = con.prepareStatement(query.getQuery()))
            {
               ps.setInt(1, size);

               if(offset != 0)
                  ps.setInt(2, offset);

               return ListFiller(ps, alias);
            }
         }
      }

      return null;
   }

   public static ProdottoPreferito getProdottoPreferito() throws SQLException {
      try (Connection connection = ConPool.getConnection()) {
         QueryBuilder qb = new QueryBuilder("prodotto_preferito", "").select();

         try (PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery())) {

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
               Categoria c = new Categoria();
               c.setNome(rs.getString("nomeCategoria"));
               Prodotto p = ProdottoExtractor.Extract(rs, "", c);
               return fill(p, rs);
            }

         }
      }
      return null;
   }

   private static ProdottoPreferito fill(Prodotto p, ResultSet rs) throws SQLException{
      ProdottoPreferito pp = new ProdottoPreferito();

      pp.setCodiceIAN(p.getCodiceIAN());
      pp.setNome(p.getNome());
      pp.setFoto(p.getFoto());
      pp.setDescrizione(p.getDescrizione());
      pp.setPrezzo(p.getPrezzo());
      pp.setPeso(p.getPeso());
      pp.setVolumeOccupato(p.getVolumeOccupato());
      pp.setCategoria(p.getCategoria());
      pp.setPossiedeList(p.getPossiedeList());
      pp.setCompostoList(p.getCompostoList());
      pp.setnAcquisti(rs.getInt("Quantità Totale Acquistata"));
      return pp;
   }

   private static List<Prodotto> ListFiller(PreparedStatement preparedStatement, String alias) throws SQLException
   {
      if (preparedStatement == null)
         return null;

      ResultSet rs = preparedStatement.executeQuery();
      ArrayList<Prodotto> list = new ArrayList<>();

      if(!alias.isEmpty() && !alias.contains("."))
         alias+=".";

      while (rs.next())
      {
         Categoria c = CategoriaDAO.doRetrieveByKey(rs.getString(alias+"nomeCategoria"));
         Prodotto p = ProdottoExtractor.Extract(rs, alias, c);
         list.add(p);
      }

      if (list.isEmpty())
         list.add(null);

      return list;
   }
}