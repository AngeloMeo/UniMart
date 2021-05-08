package UniMartTeam.model.Beans;

import java.util.List;

public class Spedizione
{
   private int id;
   private String nome;
   private float costo;
   private List<Ordine> ordineList;

   public int getID()
   {
      return id;
   }

   public void setID(int id)
   {
      this.id = id;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public float getCosto()
   {
      return costo;
   }

   public void setCosto(float costo)
   {

      this.costo = Math.abs(costo);
   }

   public void setOrdineList(List<Ordine> list)
   {
      this.ordineList = list;
   }

   public void addOrdineList(Ordine element)
   {
      if (element != null)
         ordineList.add(element);
   }

   public List<Ordine> getOrdineList()
   {
      return ordineList;
   }
}