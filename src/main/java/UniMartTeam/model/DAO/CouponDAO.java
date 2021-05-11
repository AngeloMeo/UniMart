package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Coupon;
import UniMartTeam.model.Extractors.CouponExtractor;
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
            if (ps.executeUpdate() == 0)
            {
               throw new RuntimeException("INSERT error.");
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
                  throw new RuntimeException("UPDATE error.");
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
               throw new RuntimeException("UPDATE Error");
            return true;
         }
      }
   }
/*
   public static List<Coupon> doRetrieveAll() throws SQLException
   {
      try(Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("coupon", "c");

         try(PreparedStatement ps = con.prepareStatement(qb.select("c.numeroCoupon", "c.stato", "c.sconto", "c.cfCreatore AS CF").toString()))
         {
            ResultSet rs = ps.executeQuery();
            ArrayList<Coupon> list = new ArrayList<>();

            while(rs.next())
            {
               list.add(CouponExtractor.Extract(rs, "c", UtenteExtractor.Extract(rs, ""), null));
            }
         }
      }
   }
 */
}
/*
    TODO:
     doRetrieveAll()
     doRetrieveByCond(Utente CF) // UtenteDAO.doRetrieveByID(Utente ID)
     doRetrieveByCond(Ordine ID) // OrdineDAO.doRetrieveByID(Ordine ID)
     doRetrieveById(id)
*/