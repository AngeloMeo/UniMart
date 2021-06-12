package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Spedizione;
import UniMartTeam.model.Extractors.OrdineExtractor;
import UniMartTeam.model.Extractors.SpedizioneExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SpedizioneDAO
{
   public static List<Spedizione> doRetriveAll() throws SQLException
   {
      try(Connection con = ConPool.getConnection())
      {
         QueryBuilder query = new QueryBuilder("spedizione", "s");

         try(PreparedStatement ps = con.prepareStatement(query.select().getQuery()))
         {
            ResultSet rs = ps.executeQuery();
            List<Spedizione> results = new ArrayList<>();

            while(rs.next())
            {
               Spedizione s = SpedizioneExtractor.Extract(rs, "s");
               results.add(s);
            }

            return results;
         }
      }
   }

   public static List<Spedizione> doRetriveOrdineList() throws SQLException
   {
      try(Connection con = ConPool.getConnection())
      {
         QueryBuilder query = new QueryBuilder("spedizione", "s").select().outerJoin("ordine", "o", 1).on("s.ID = o.metodoSpedizione");

         try(PreparedStatement ps = con.prepareStatement(query.getQuery()))
         {
            ResultSet rs = ps.executeQuery();
            List<Spedizione> results = new ArrayList<>();

            while(rs.next())
            {
               Spedizione sTmp = SpedizioneExtractor.Extract(rs, "s");
               Ordine o = OrdineExtractor.Extract(rs, "o", null, null, sTmp);
               int index = results.indexOf(sTmp);
               //eseguo questo blocco se la spedizione non è ancora presente nel mio arrayList
               if(index == -1)
               {
                  sTmp.setOrdineList(new ArrayList<Ordine>());
                  sTmp.addOrdineList(o);
                  results.add(sTmp);
               }
               else //eseguo questo blocco se è già presente la spedizione
                  results.get(index).addOrdineList(o);
            }
            return results;
         }
      }
   }

   public static Spedizione doRetriveOrdineListById(int id) throws SQLException
   {
      if(id==0)
         return null;

      try(Connection con = ConPool.getConnection())
      {
         QueryBuilder query = new QueryBuilder("spedizione", "s").select().outerJoin("ordine", "o", 1).on("s.ID = o.metodoSpedizione").where("S.ID = " + Integer.toString(id));

         try(PreparedStatement ps = con.prepareStatement(query.getQuery()))
         {
            ResultSet rs = ps.executeQuery();
            Spedizione results = null;

            if(rs.next())
            {
               results = SpedizioneExtractor.Extract(rs, "s");
               results.setOrdineList(new ArrayList<Ordine>());
               while(rs.next())
               {
                  Ordine o = OrdineExtractor.Extract(rs, "o", null, null, results);

                  results.addOrdineList(o);
                  results.addOrdineList(o);
               }
            }
            return results;
         }
      }
   }

   public static boolean doSave(Spedizione s) throws SQLException
   {
      if(s == null || s.getID()==0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("spedizione", "s");

         try (PreparedStatement ps = con.prepareStatement(qb.insert("nome", "costo").getQuery()))
         {
            ps.setString(1, s.getNome());
            ps.setFloat(2, s.getCosto());

            if (ps.executeUpdate() == 0)
               return false;
         }
      }
      return true;
   }

   public static boolean doUpdate(Spedizione s) throws SQLException
   {
      if(s == null || s.getID() == 0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("spedizione", "").update("nome", "costo").where("ID=" + s.getID());
         String query = qb.getQuery();

         try (PreparedStatement pss = con.prepareStatement(query))
         {
            pss.setString(1, s.getNome());
            pss.setFloat(2, s.getCosto());

            if (pss.executeUpdate() == 0)
               return false;
         }
      }
      return true;
   }

   public static boolean doDelete(int id) throws SQLException
   {
      if(id == 0)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("spedizione", "").delete().where("ID=" + Integer.toString(id));

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            if (ps.executeUpdate() == 0)
               return false;
         }
      }
      return true;
   }

   public static Spedizione doRetriveById(int id) throws SQLException
   {
      try(Connection con = ConPool.getConnection())
      {
         QueryBuilder query = new QueryBuilder("spedizione", "s").select().where("ID=?");

         try(PreparedStatement ps = con.prepareStatement(query.getQuery()))
         {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
               Spedizione s = SpedizioneExtractor.Extract(rs, "s");
               return s;
            }

            return null;
         }
      }
   }
}