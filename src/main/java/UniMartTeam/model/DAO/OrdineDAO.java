package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.*;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.Extractors.CompostoExtractor;
import UniMartTeam.model.Extractors.OrdineExtractor;
import UniMartTeam.model.Extractors.SpedizioneExtractor;
import UniMartTeam.model.Extractors.UtenteExtractor;
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
         Utente utente = UtenteExtractor.Extract(rs, "");
         Spedizione spedizione = SpedizioneExtractor.Extract(rs, "");
         Ordine ordine = OrdineExtractor.Extract(rs, "", utente, null, spedizione);
         Coupon coupon = CouponDAO.doRetrieveByCond(ordine);

         ordine.setCoupon(coupon);
         list.add(ordine);
      }

      if (list.isEmpty())
         list.add(new Ordine());

      return list;
   }

   public static Ordine doRetrieveByID(int numeroOrdine) throws SQLException
   {
      if (numeroOrdine <= 0)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("ordine", "").select().where("numeroOrdine = " + numeroOrdine);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
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
         QueryBuilder qb = new QueryBuilder("ordine", "").select().where("cfCliente=" + u.getCF());

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
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
}