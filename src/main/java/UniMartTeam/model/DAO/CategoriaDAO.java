package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;

public class categoriaDAO
{
   public void doSave(Categoria c) throws SQLException{

      try(Connection con = ConPool.getConnection()){
         QueryBuilder qb = new QueryBuilder("categoria", "cat");

         try(PreparedStatement ps = con.prepareStatement(qb.insert("nome", "aliquota").getQuery()))
         {
            ps.setString(1, c.getNome());
            ps.setFloat(2, c.getAliquota());
            if (ps.executeUpdate() != 1) {
               throw new RuntimeException("INSERT error.");
            }
         }
      }
   }

   public void doSaveOrUpdate(Categoria c) throws SQLException{

      try(Connection con = ConPool.getConnection()){

         QueryBuilder qb = new QueryBuilder("categoria", "cat");

         try(PreparedStatement ps = con.prepareStatement(qb.select().where("cat.nome = " + c.getNome()).getQuery()))
         {
            ResultSet rs = ps.executeQuery();
            //update
            if(rs.next()){
               try(PreparedStatement pss = con.prepareStatement(qb.update("nome", "aliquota").getQuery())){
                  pss.setString(1, c.getNome());
                  pss.setFloat(2, c.getAliquota());
                  if (pss.executeUpdate() != 1) {
                     throw new RuntimeException("UPDATE error.");
                  }
               }
            } else { //doSave
               doSave(c);
            }
         }
      }
   }

   public List<Categoria> doRetrieveAll(int offset, int size) throws SQLException{

      try(Connection con = ConPool.getConnection())
      {

         QueryBuilder qb = new QueryBuilder("categoria", "cat");
         try (PreparedStatement ps = con.prepareStatement(qb.select().limit(true).getQuery()))
         {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> list = new ArrayList<>();

            while (rs.next())
            {

               Categoria c = new Categoria();
               c.setNome(rs.getString(1));
               c.setAliquota(rs.getFloat(2));
               list.add(c);

            }
            return list;
         }
      }
   }

   public List<Categoria> doRetrieveAll() throws SQLException{

      try(Connection con = ConPool.getConnection())
      {

         QueryBuilder qb = new QueryBuilder("categoria", "cat");
         try (PreparedStatement ps = con.prepareStatement(qb.select().getQuery()))
         {

            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> list = new ArrayList<>();

            while (rs.next())
            {

               Categoria c = new Categoria();
               c.setNome(rs.getString(1));
               c.setAliquota(rs.getFloat(2));
               list.add(c);

            }
            return list;
         }
      }
   }

   public Categoria doRetrieveByKey(String categoryName) throws  SQLException{

      try(Connection con = ConPool.getConnection()){

         QueryBuilder qb = new QueryBuilder("categoria", "cat").select().where("cat.nome=?");
         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
               Categoria c = new Categoria();
               c.setNome(rs.getString(1));
               c.setAliquota(rs.getFloat(2));

               return c;
            }
            return null;
         }

      }

   }
   public List<Prodotto> doRetrieveProducts(Categoria c) throws SQLException{

      try(Connection con = ConPool.getConnection()){
         QueryBuilder qb = new QueryBuilder("prodotto", "p").select("p.codiceIAN", "p.nome", "p.prezzo", "p.peso", "p.foto" ).where("p.nomeCategoria=?");
         try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
            ps.setString(1,c.getNome());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
               ArrayList<Prodotto> list = new ArrayList<>();
//retrieve products that belong in c Category
            }

         }

   }
   /*

   doDelete(Key k)
   elimina l'oggetto con key = k
   posso usare anche doDelete(DATA o)

   doRetriveByCond(Condizione)
   restituisce una collezione di oggetti data una condizione
*/
}
