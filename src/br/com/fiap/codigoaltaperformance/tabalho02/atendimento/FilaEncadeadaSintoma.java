package br.com.fiap.codigoaltaperformance.tabalho02.atendimento;

public class FilaEncadeadaSintoma {
      
	
	  private class NO
      {
           String dado;
           NO prox;
      }

      private NO ini;
      private NO fim;

      public void init()
      {
          ini = fim = null;
      }

      public boolean isEmpty()
      {
          return ini == null && fim == null;
      }

      public void enqueue(String elem)
      {
          NO novo = new NO();
          novo.dado = elem;
          novo.prox = null;
          if (isEmpty())
              ini = novo;
          else
              fim.prox = novo;
          fim =  novo;

      }

      public String dequeue()
      {
          String v = ini.dado;
          ini = ini.prox;
          if (ini == null)
              fim = null;
          return v;

      }
      public String first()
      {
          return ini.dado;
      }
      
      public void mostrar() {
    	  NO aux = ini;
    	  while(aux != null) {
    		  System.out.println(aux.dado);
    		  aux = aux.prox;
    	  }
      }
      

}
