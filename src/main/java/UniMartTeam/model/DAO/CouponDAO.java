package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Spedizione;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.Extractors.CouponExtractor;
import UniMartTeam.model.Extractors.OrdineExtractor;
import UniMartTeam.model.Extractors.UtenteExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO
{
   public static boolean doSave(Coupon coupon) throws SQLException
   {

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c").insert("numeroCoupon", "stato", "sconto", "cfCreatore");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {

            ps.setInt(1, coupon.getNumeroCoupon());
            ps.setString(2, coupon.getStatoCoupon().toString());
            ps.setFloat(3, coupon.getSconto());
            ps.setString(4, coupon.getCreatore().getCF());

            if (ps.executeUpdate() == 0) {
               return false;
            }
            return true;
         }
      }
   }

   public static boolean doUpdate(Coupon coupon) throws SQLException
   {
      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "");

         try (PreparedStatement ps = con.prepareStatement(qb.select().where("numeroCoupon = " + coupon.getNumeroCoupon()).getQuery()))
         {
            ResultSet rs = ps.executeQuery();
            //update
            try (PreparedStatement pss = con.prepareStatement(qb.update("stato", "sconto", "cfCreatore").where("numeroCoupon=" + coupon.getNumeroCoupon()).getQuery()))
            {
               pss.setString(1, coupon.getStatoCoupon().toString());
               pss.setFloat(2, coupon.getSconto());
               pss.setString(3, coupon.getCreatore().getCF());

               if (pss.executeUpdate() == 0)
               {
                  return false;
               }
               return true;
            }

         }
      }

   }

   public static boolean doDelete(int numeroCoupon) throws SQLException
   {
      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "").delete().where("numeroCoupon=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setInt(1, numeroCoupon);

            if (ps.executeUpdate() == 0)
               return false;

            return true;
         }
      }
   }

   public static List<Coupon> doRetrieveAll(int offset, int size) throws SQLException
   {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF", "ca.idOrdine AS numeroOrdine");
         qb.outerJoin("coupon_applicato", "ca",1).on("ca.idCoupon=c.numeroCoupon").limit(true);

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.toString()))
         {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, size);

            return ListFiller(preparedStatement);
         }
      }
   }

   public static List<Coupon> doRetrieveAll() throws SQLException
   {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF", "ca.idOrdine AS numeroOrdine");
         qb.outerJoin("coupon_applicato", "ca",1).on("ca.idCoupon=c.numeroCoupon");

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.toString()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static List<Coupon> doRetrieveByCond(Utente u) throws SQLException
   {
      if(u == null || u.getCF() == null)
         return null;

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF", "ca.idOrdine AS numeroOrdine");
         qb.outerJoin("coupon_applicato", "ca",1).on("ca.idCoupon=c.numeroCoupon").where("CF=" + u.getCF());

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.toString()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static List<Coupon> doRetrieveByCond(Ordine o) throws SQLException
   {
      if(o == null || o.getNumeroOrdine() == 0)
         return null;

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF", "ca.idOrdine AS numeroOrdine");
         qb.outerJoin("coupon_applicato", "ca",1).on("ca.idCoupon=c.numeroCoupon").where("numeroOrdine=" + o.getNumeroOrdine());

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.toString()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static Coupon doRetrieveById(int id) throws SQLException
   {
      if(id == 0)
         return null;

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF", "ca.idOrdine AS numeroOrdine");
         qb.outerJoin("coupon_applicato", "ca",1).on("ca.idCoupon=c.numeroCoupon").where("c.numeroCoupon=" + id);

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.toString()))
         {
            return ListFiller(preparedStatement).get(0);
         }
      }
   }

   private static List<Coupon> ListFiller(PreparedStatement preparedStatement) throws SQLException
   {
      ResultSet rs = preparedStatement.executeQuery();
      ArrayList<Coupon> list = new ArrayList<>();

      while(rs.next())
      {
         Utente utente = UtenteExtractor.Extract(rs, "");
         Coupon coupon = CouponExtractor.Extract(rs, "c", utente, null);
         Ordine ordine = OrdineExtractor.Extract(rs, "", utente, null, null);

         coupon.setOrdine(ordine);
         ordine.setCoupon(coupon);

         list.add(coupon);
      }

      if(list.isEmpty())
         list.add(new Coupon());

      return list;
   }
}