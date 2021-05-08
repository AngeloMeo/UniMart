package UniMartTeam.model.Beans;

import java.util.List;

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
}
