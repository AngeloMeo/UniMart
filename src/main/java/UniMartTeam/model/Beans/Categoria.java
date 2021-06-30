package UniMartTeam.model.Beans;

import java.util.List;
import java.util.Objects;

public class Categoria
{
   private String nome;
   private float aliquota;
   private List<Prodotto> prodottoList;

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public float getAliquota()
   {
      return aliquota;
   }

   public void setAliquota(float aliquota)
   {
      this.aliquota = aliquota;
   }

   public void setProdottoList(List<Prodotto> list)
   {
      this.prodottoList = list;
   }

   public void addProdottoList(Prodotto element)
   {
      if (element != null)
         prodottoList.add(element);
   }

   public List<Prodotto> getProdottoList()
   {
      return prodottoList;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Categoria categoria = (Categoria) o;
      return Float.compare(categoria.aliquota, aliquota) == 0 && Objects.equals(nome, categoria.nome) && Objects.equals(prodottoList, categoria.prodottoList);
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(nome);
   }
}
