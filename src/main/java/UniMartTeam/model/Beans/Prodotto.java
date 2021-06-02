package UniMartTeam.model.Beans;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Prodotto
{
   private int codiceIAN;
   private String nome, foto, descrizione;
   private float prezzo, peso, volumeOccupato;
   private Categoria categoria;
   private List<Possiede> possiedeList;
   private List<Composto> compostoList;

   public int getCodiceIAN()
   {
      return codiceIAN;
   }

   @Override
   public String toString() {
      return "Prodotto{" +
              "codiceIAN=" + codiceIAN +
              ", nome='" + nome + '\'' +
              ", foto='" + foto + '\'' +
              ", descrizione='" + descrizione + '\'' +
              ", prezzo=" + prezzo +
              ", peso=" + peso +
              ", volumeOccupato=" + volumeOccupato +
              ", categoria=" + categoria.getNome() +
              '}';
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

   public void setPossiedeList(List<Possiede> possiedeList)
   {
      this.possiedeList = possiedeList;
   }

   public void addPossiedeList(Possiede element)
   {
      if (element != null)
         possiedeList.add(element);
   }

   public void uploadFoto(Part filePart, String path) throws IOException
   {
      try(InputStream is = filePart.getInputStream())
      {
         String fileName = getCodiceIAN() + "_" + filePart.getSubmittedFileName();
         Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);

         setFoto(fileName);
      }
   }

   public List<Possiede> getPossiedeList()
   {
      return possiedeList;
   }

   public void setCompostoList(List<Composto> compostoList)
   {
      this.compostoList = compostoList;
   }

   public void addCompostoList(Composto element)
   {
      if (element != null)
         compostoList.add(element);
   }

   public List<Composto> getCompostoList()
   {
      return compostoList;
   }
}
