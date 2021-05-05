package UniMartTeam.model;

public class Prodotto
{
   private int codiceIAN;
   private String nome, foto, descrizione;
   private float prezzo, peso, volumeOccupato;
   private Categoria categoria;

   public int getCodiceIAN()
   {
      return codiceIAN;
   }

   public void setCodiceIAN(int codiceIAN)
   {
      this.codiceIAN = codiceIAN;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public String getFoto()
   {
      return foto;
   }

   public void setFoto(String foto)
   {
      this.foto = foto;
   }

   public String getDescrizione()
   {
      return descrizione;
   }

   public void setDescrizione(String descrizione)
   {
      this.descrizione = descrizione;
   }

   public float getPrezzo()
   {
      return prezzo;
   }

   public void setPrezzo(float prezzo)
   {
      this.prezzo = prezzo;
   }

   public float getPeso()
   {
      return peso;
   }

   public void setPeso(float peso)
   {
      this.peso = peso;
   }

   public float getVolumeOccupato()
   {
      return volumeOccupato;
   }

   public void setVolumeOccupato(float volumeOccupato)
   {
      this.volumeOccupato = volumeOccupato;
   }

   public Categoria getCategoria()
   {
      return categoria;
   }

   public void setCategoria(Categoria categoria)
   {
      this.categoria = categoria;
   }
}
