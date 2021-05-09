package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Ordine;
import UniMartTeam.model.Beans.Spedizione;
import UniMartTeam.model.EnumForBeans.StatoOrdine;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class spedizioneDAO
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
               Spedizione s = new Spedizione();

               s.setID(rs.getInt("id"));
               s.setNome(rs.getString("nome"));
               s.setCosto(rs.getFloat("costo"));
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
         QueryBuilder query = new QueryBuilder("spedizione", "s").select("s.ID","s.nome","s.costo","o.numeroOrdine","o.stato","o.feedback","o.ricevutaPagamento","o.dataAcquisto").outerJoin("ordine", "o", 1).on("s.ID = o.metodoSpedizione");

         try(PreparedStatement ps = con.prepareStatement(query.select().getQuery()))
         {
            ResultSet rs = ps.executeQuery();
            List<Spedizione> results = new ArrayList<>();

            while(rs.next())
            {
               Spedizione sTmp = new Spedizione();
               Ordine o = new Ordine();

               o.setNumeroOrdine(rs.getInt("o.numeroOrdine"));
               o.setStatoOrdine(StatoOrdine.StringToEnum(rs.getString("o.stato")));
               o.setFeedback(rs.getString("o.feedback"));
               o.setRicevutaPagamento(rs.getString("o.ricevutaPagamento"));
               o.setDataAcquisto(rs.getDate("o.dataAcquisto").toLocalDate());
               sTmp.setID(rs.getInt("s.ID"));

               int index = results.indexOf(sTmp);
               //eseguo questo blocco se la spedizione non è ancora presente nel mio arrayList
               if(index == -1)
               {
                  sTmp.setCosto(rs.getFloat("s.costo"));
                  sTmp.setNome(rs.getString("s.nome"));
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

   public List<Spedizione> doRetriveOrdineListById(int id)
   {
      if(id >= 0)
         return null;
      else
         return null;
   }
}