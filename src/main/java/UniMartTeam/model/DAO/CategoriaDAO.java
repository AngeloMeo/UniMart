package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Extractors.CategoriaExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;

public class CategoriaDAO
{
   public void doSave(Categoria c) throws SQLException{

      try(Connection con = ConPool.getConnection()){
         QueryBuilder qb = new QueryBuilder("categoria", "cat");

         try(PreparedStatement ps = con.prepareStatement(qb.insert("nome", "aliquota").getQuery()))
         {
            ps.setString(1, c.getNome());
            ps.setFloat(2, c.getAliquota());
            if (ps.executeUpdate() == 0) {
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
                  if (pss.executeUpdate() == 0) {
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
         String alias = "cat";
         QueryBuilder qb = new QueryBuilder("categoria", alias);
         try (PreparedStatement ps = con.prepareStatement(qb.select().limit(true).getQuery()))
         {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> list = new ArrayList<>();

            while (rs.next())
            {
               list.add(CategoriaExtractor.Extract(rs, alias));
            }
            return list;
         }
      }
   }

   public List<Categoria> doRetrieveAll() throws SQLException{

      try(Connection con = ConPool.getConnection())
      {

         String alias = "cat";
         QueryBuilder qb = new QueryBuilder("categoria", alias);
         try (PreparedStatement ps = con.prepareStatement(qb.select().getQuery()))
         {

            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> list = new ArrayList<>();

            while (rs.next())
            {
               list.add(CategoriaExtractor.Extract(rs, alias));

            }
            return list;
         }
      }
   }

   public Categoria doRetrieveByKey(String categoryName) throws  SQLException{

      try(Connection con = ConPool.getConnection()){
         String alias = "cat";
         QueryBuilder qb = new QueryBuilder("categoria", alias).select().where("cat.nome=?");
         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
               return CategoriaExtractor.Extract(rs, alias);
            }
            return null;
         }

      }

   }
   public List<Prodotto> doRetrieveProducts(Categoria c) throws SQLException {

      try (Connection con = ConPool.getConnection()) {

         QueryBuilder qb = new QueryBuilder("prodotto", "p").select("p.codiceIAN", "p.nome", "p.prezzo", "p.peso", "p.foto").where("p.nomeCategoria=?");
         try (PreparedStatement ps = con.prepareStatement(qb.getQuery())) {
            ps.setString(1, c.getNome());
            ResultSet rs = ps.executeQuery();
            ArrayList<Prodotto> list = new ArrayList<>();

            while (rs.next()) {

               Prodotto p = new Prodotto();
               p.setCodiceIAN(rs.getInt("p.codiceIAN"));
               p.setNome(rs.getString("p.nome"));
               p.setPrezzo(rs.getFloat("p.prezzo"));
               p.setPeso(rs.getFloat("p.peso"));
               p.setFoto(rs.getString("p.foto"));
               list.add(p);

            }
            return list;
         }

      }
   }

   public void doDelete(String name) throws SQLException{

      try(Connection con = ConPool.getConnection()) {

         QueryBuilder qb = new QueryBuilder("categoria", "c").delete().where("c.nome=?");

         try(PreparedStatement ps = con.prepareStatement(qb.getQuery())){
            ps.setString(1, name);
            if(ps.executeUpdate() == 0)
               throw new RuntimeException("UPDATE Error");
         }

      }

   }

}
