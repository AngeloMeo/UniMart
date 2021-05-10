package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Spedizione;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
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
   public List<Spedizione> doRetriveAll() throws SQLException
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

   public List<Spedizione> doRetriveOrdineList() throws SQLException
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

   public Spedizione doRetriveOrdineListById(int id) throws SQLException
   {
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
}