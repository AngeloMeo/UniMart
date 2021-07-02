package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Utente;
import UniMartTeam.model.EnumForBeans.StatoCoupon;
import UniMartTeam.model.Extractors.CouponExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponDAO
{
   public static int doSave(Coupon coupon) throws SQLException
   {
      if(coupon == null)
         return 0;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c").insert("cfCreatore", "sconto");

         try (PreparedStatement pss = con.prepareStatement(qb.getQuery(), Statement.RETURN_GENERATED_KEYS))
         {
            pss.setString(1, coupon.getCreatore().getCF());
            pss.setFloat(2, coupon.getSconto());

            pss.executeUpdate();
            ResultSet rs = pss.getGeneratedKeys();

            if(rs.next())
               return rs.getInt(1);

            return -1;
         }
      }
   }

   public static boolean doUpdate(Coupon coupon) throws SQLException
   {
      if(coupon == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "").update("cfCreatore", "sconto", "stato");
         qb.where("numeroCoupon=" + coupon.getNumeroCoupon());

         try (PreparedStatement pss = con.prepareStatement(qb.getQuery()))
         {
            pss.setString(1, coupon.getCreatore().getCF());
            pss.setFloat(2, coupon.getSconto());
            pss.setString(3, coupon.getStatoCoupon().toString());

            return (pss.executeUpdate() == 0) ? false : true;
         }
      }
   }

   public static boolean doDelete(int numeroCoupon) throws SQLException
   {

      if(numeroCoupon == 0)
         return false;

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
      if(offset<1 || size<1)
         return null;

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF", "ca.idOrdine AS numeroOrdine");
         qb.outerJoin("coupon_applicato", "ca",1).on("ca.idCoupon=c.numeroCoupon").limit(true);

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
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

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
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

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static Coupon doRetrieveByCond(Ordine o) throws SQLException
   {
      if(o == null || o.getNumeroOrdine() == 0)
         return null;

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF", "ca.idOrdine AS numeroOrdine");
         qb.outerJoin("coupon_applicato", "ca",1).on("ca.idCoupon=c.numeroCoupon").where("ca.idOrdine='" + o.getNumeroOrdine()+"'");

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            return ListFiller(preparedStatement).get(0);
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

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            return ListFiller(preparedStatement).get(0);
         }
      }
   }

   private static List<Coupon> ListFiller(PreparedStatement preparedStatement) throws SQLException
   {
      if(preparedStatement == null)
         return null;

      ResultSet rs = preparedStatement.executeQuery();
      ArrayList<Coupon> list = new ArrayList<>();

      while(rs.next())
      {
         Utente utente = new Utente();
         utente.setCF(rs.getString("CF"));
         Ordine ordine = new Ordine();
         ordine.setNumeroOrdine(rs.getInt("numeroOrdine"));

         Coupon coupon = CouponExtractor.Extract(rs, "c", utente, ordine);

         list.add(coupon);
      }

      if(list.isEmpty())
         list.add(null);

      return list;
   }

   public static int countClaimedCoupon() throws SQLException{

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
         qb.select("COUNT(*)").where("c.stato = '" + StatoCoupon.Riscattato + "'");
         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
               return rs.getInt(1);
         }
      }
      return 0;
   }

   public static int countCoupon() throws SQLException{

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");
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



}