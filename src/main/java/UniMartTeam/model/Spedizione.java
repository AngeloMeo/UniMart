package UniMartTeam.model;

public class Spedizione
{
   private int id;
   private String nome;
   private float costo;

   public int getID(){
      return id;
   }

   public void setID(int id){
      this.id = id;
   }

   public String getNome(){
      return nome;
   }

   public void setNome(String nome){
      this.nome=nome;
   }

   public float getCosto(){
      return costo;
   }

   public void setCosto(float costo){

      this.costo = Math.abs(costo);

   }
}