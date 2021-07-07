package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.*;
import UniMartTeam.model.Beans.Views.ProdottiStats;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.Extractors.CompostoExtractor;
import UniMartTeam.model.Extractors.OrdineExtractor;
import UniMartTeam.model.Extractors.ProdottoExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO
{
   public static boolean doSave(Ordine o) throws SQLException
   {
      if (o == null || o.getNumeroOrdine() == 0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").insert("stato",
                 "feedback", "ricevutaPagamento", "dataAcquisto", "cfCliente", "metodoSpedizione");

         boolean esito = executeStatement(con, o, qb);

         qb = new QueryBuilder("coupon_applicato", "").insert("idCoupon", "idOrdine");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, o.getNumeroOrdine());
            ps.setInt(2, o.getCoupon().getNumeroCoupon());

            if(esito == false)
            {
               con.rollback();
               return false;
            }
            else
               return (ps.executeUpdate() == 0);
         }
      }
   }

   public static boolean doUpdate(Ordine o) throws SQLException
   {
      if (o == null || o.getNumeroOrdine() == 0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").update("stato",
                 "feedback", "ricevutaPagamento", "dataAcquisto", "cfCliente", "metodoSpedizione").
                 where("numeroOrdine = " + o.getNumeroOrdine());

         return executeStatement(con, o, qb);
      }
   }

   private static boolean executeStatement(Connection con, Ordine o, QueryBuilder qb) throws SQLException
   {
      try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
      {
         ps.setString(1, o.getStatoOrdine().toString());
         ps.setString(2, o.getFeedback());
         ps.setString(3, o.getRicevutaPagamento());
         ps.setDate(4, Date.valueOf(o.getDataAcquisto()));
         ps.setString(5, o.getCliente().getCF());
         ps.setInt(6, o.getSpedizione().getID());

         return ps.executeUpdate() != 0;
      }
   }

   public static boolean doDelete(Ordine o) throws SQLException
   {
      if (o == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         boolean esito = false;
         QueryBuilder qb = new QueryBuilder("ordine", "").delete().where("numeroOrdine=" + o.getNumeroOrdine());

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
             esito = ps.executeUpdate() != 0;
         }

         qb = new QueryBuilder("coupon_applicato", "").delete().where("idCoupon=" + o.getCoupon().getNumeroCoupon() + " idOrdine=" + o.getNumeroOrdine());

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            if(esito == false)
            {
               con.rollback();
               return false;
            }
            else
               return (ps.executeUpdate() == 0);
         }
      }
   }

   public static List<Ordine> doRetrieveAll() throws SQLException
   {
      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").select("*", "cfCliente AS CF", "metodoSpedizione AS ID");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps);
         }
      }
   }

   public static Ordine doRetiveProducts(Ordine ordine) throws SQLException
   {
      if(ordine != null)
      {
         try (Connection con = ConPool.getConnection())
         {
            QueryBuilder qb = new QueryBuilder("prodotto", "p").select("p.*", "op.prezzoAcquisto", "op.quantita");
            qb.outerJoin("ordine_prodotto", "op", 2).on("p.codiceIAN = op.idProdotto").where("op.idOrdine=?");

            try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
            {
               ps.setInt(1, ordine.getNumeroOrdine());
               ResultSet rs = ps.executeQuery();
               ordine.setCompostoList(new ArrayList<Composto>());

               while(rs.next())
               {
                  Prodotto prodotto = ProdottoExtractor.Extract(rs, "", CategoriaDAO.doRetrieveByKey(rs.getString("nomeCategoria")));
                  ordine.addCompostoList(CompostoExtractor.Extract(rs, "", ordine, prodotto));
               }
            }
         }
      }

      return ordine;
   }

   public static List<Ordine> doRetrieveAll(int offset, int size) throws SQLException
   {
      if (offset < 0 || size < 1)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         String alias = "";
         QueryBuilder qb = new QueryBuilder("ordine", alias).select("*", "cfCliente AS CF", "metodoSpedizione AS ID").limit(true);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            return ListFiller(ps);
         }
      }
   }

   private static List<Ordine> ListFiller(PreparedStatement preparedStatement) throws SQLException
   {
      if (preparedStatement == null)
         return null;

      ResultSet rs = preparedStatement.executeQuery();
      ArrayList<Ordine> list = new ArrayList<>();

      while (rs.next())
      {
         Utente utente = UtenteDAO.doRetrieveByCond(UtenteDAO.CF, "'" + rs.getString("cfCliente") + "'").get(0);
         Spedizione spedizione = SpedizioneDAO.doRetriveById(rs.getInt("ID"));
         Ordine ordine = OrdineExtractor.Extract(rs, "", utente, null, spedizione);
         Coupon coupon = CouponDAO.doRetrieveByCond(ordine);
         ordine.setCoupon(coupon);

         list.add(ordine);
      }

      if (list.isEmpty())
         list.add(null);

      return list;
   }

   public static Ordine doRetrieveByID(int numeroOrdine) throws SQLException
   {
      if (numeroOrdine <= 0)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").select("*", "cfCliente AS CF", "metodoSpedizione AS ID").where("numeroOrdine = ?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, numeroOrdine);

            return ListFiller(ps).get(0);
         }
      }
   }

   public static List<Ordine> doRetrieveByCond(Utente u) throws SQLException
   {
      if (u == null || u.getCF() == null)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").select("*", "cfCliente AS CF", "metodoSpedizione AS ID").where("cfCliente=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, u.getCF());

            return ListFiller(ps);
         }
      }
   }

   public static List<Ordine> doRetrieveByCond(int metodoSpedizione) throws SQLException
   {
      if (metodoSpedizione <= 0)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").select().where("metodoSpedizione=" + metodoSpedizione);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps);
         }
      }
   }

   public static Ordine doRetrieveByCond(Coupon coupon) throws SQLException
   {
      if (coupon == null)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon_applicato", "").select().where("idCoupon=" + coupon.getNumeroCoupon());

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps).get(0);
         }
      }
   }

   public static Ordine doRetrieveByCond(StatoOrdine stato) throws SQLException
   {
      if (stato == null)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").select().where("stato=" + stato);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps).get(0);
         }
      }
   }
   /**
      Sintassi da utilizzare: doRetriveByCond("dataAcquisto > yyyy/mm/dd")
    */
   public static Ordine doRetrieveByCond(String cond) throws SQLException
   {
      if (cond.isEmpty())
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").select().where(cond);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            return ListFiller(ps).get(0);
         }
      }
   }

   public static boolean addProdottoOrdine(Composto composto) throws SQLException
   {
      if(verificaIntegritaOggettoComposto(composto))
      {
         try (Connection con = ConPool.getConnection() )
         {
            QueryBuilder qb = new QueryBuilder("ordine_prodotto", "").insert("idOrdine", "idProdotto", "prezzoAcquisto", "quantita");

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               pss.setInt(1, composto.getOrdine().getNumeroOrdine());
               pss.setInt(2, composto.getProdotto().getCodiceIAN());
               pss.setFloat(3, composto.getPrezzo());
               pss.setFloat(4, composto.getQuantita());

               return pss.executeUpdate() != 0;
            }
         }
      }
      return false;
   }

   private static boolean verificaIntegritaOggettoComposto(Composto composto)
   {
      return (composto != null && composto.getProdotto() != null && composto.getOrdine() != null && composto.getPrezzo() >= 0 && composto.getQuantita() > 0);
   }

   public static boolean updateProdottoOrdine(Composto composto) throws SQLException
   {
      if(verificaIntegritaOggettoComposto(composto))
      {
         try (Connection con = ConPool.getConnection() )
         {
            QueryBuilder qb = new QueryBuilder("ordine_prodotto", "").update("quantita");
            qb.where("idOrdine=" + composto.getOrdine().getNumeroOrdine() + " and idProdotto=" + composto.getProdotto().getCodiceIAN());

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               pss.setFloat(1, composto.getQuantita());

               return pss.executeUpdate() != 0;
            }
         }
      }
      return false;
   }

   public static boolean deleteProdottoOrdine(Composto composto) throws SQLException
   {
      if(verificaIntegritaOggettoComposto(composto))
      {
         try (Connection con = ConPool.getConnection())
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").delete();
            qb.where("idOrdine=" + composto.getOrdine().getNumeroOrdine() + " and idProdotto=" + composto.getProdotto().getCodiceIAN());

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               return pss.executeUpdate() != 0;
            }
         }
      }
      return false;
   }

   public static Composto getInfoProdottoInOrdine(Composto composto) throws SQLException
   {
      if(verificaIntegritaOggettoComposto(composto))
      {
         try (Connection con = ConPool.getConnection() )
         {
            QueryBuilder qb = new QueryBuilder("inventario_prodotto", "").select();
            qb.where("idOrdine=" + composto.getOrdine().getNumeroOrdine() + " and idProdotto=" + composto.getProdotto().getCodiceIAN());

            try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
            {
               ResultSet rs = pss.executeQuery();

               if (rs.next())
                  return CompostoExtractor.Extract(rs, "", OrdineDAO.doRetrieveByCond("idOrdine=" + rs.getInt("idOrdine")), ProdottoDAO.doRetrieveByID(rs.getInt("icProdotto")));

            }
         }
      }
      return null;
   }
   public static int/*TODO*/ productCounter() throws SQLException{

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("we", ""); //TODO



      }
      return 0;
   }

   public static int countOrdiniSalvati() throws SQLException {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "o");
         qb.select("COUNT(*)").where("o.stato = '" + StatoOrdine.Salvato + "'");
         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
               return rs.getInt(1);
         }
      }
      return 0;
   }

   public static int countOrdiniEvasi() throws SQLException {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "o");
         qb.select("COUNT(*)").where("o.stato != '" + StatoOrdine.Salvato + "'");
         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
               return rs.getInt(1);
         }
      }
      return 0;
   }

   public static int countOrdiniTotali() throws SQLException {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "o");
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


   public static ProdottiStats countProdottiVenduti() throws SQLException {
      try(Connection connection = ConPool.getConnection())
      {
         ArrayList<Float> list = new ArrayList<>();

         QueryBuilder query = new QueryBuilder("prodotto", "p");
         query.select("sum(op.quantita) AS 'Quantità Prodotti Venduti', count(op.idProdotto) AS 'Prodotti Venduti', cast(SUM(op.prezzoAcquisto) AS DECIMAL(10,2)) AS 'Incasso Ordini'");
         query.outerJoin("ordine_prodotto", "op", 2).on("p.codiceIAN = op.idProdotto");
         try(PreparedStatement preparedStatement = connection.prepareStatement(query.getQuery()))
         {
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
               ProdottiStats ps = new ProdottiStats();
               ps.setQuantitaProdottiVenduti(rs.getFloat("Quantità Prodotti Venduti"));
               ps.setProdottiVenduti(rs.getInt("Prodotti Venduti"));
               ps.setIncasso(rs.getInt("Incasso Ordini"));
               return ps;
            }
         }
      }
      return null;
   }



}