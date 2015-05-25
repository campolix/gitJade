package testes;


import br.edu.ufg.master.base.Usina;
import jade.core.Agent;

public class Teste extends Agent{
	private static final long serialVersionUID = 1L;
	
	
	private String nome;
	private String volumeMin;
	private String volumeMax;
	private String geracaoMin;
	private String geracaoMax;
	//private double[] afluencias = new double[6];
	private String precoEnergia;	
	
	protected void setup(){
		//Iniciando agente Usina
		System.out.println(" Agente <"+ getAID().getLocalName()+"> Iniciado");
		
		//Capturando dados de entrada do agente
		//Formato de entrada: (double precoEnergia, String nome, double volumeMin, double volumeMax, double geracaoMin, double geracaoMax)
		Object[] args = getArguments();
		if(args != null && args.length>0){
			precoEnergia = (String) args[0];
			nome = (String) args[1];
			volumeMin = (String) args[2];
			volumeMax = (String) args[3];
			geracaoMin = (String) args[4];
			geracaoMax = (String) args[5];
			
			Usina u = new Usina();
			//u.setPrecoEnergia(Double.parseDouble((String) args[0]));
			u.setPrecoEnergia(Double.parseDouble(precoEnergia));
			u.setNome(nome);
			u.setVolumeMin(Double.parseDouble(volumeMin));
			u.setVolumeMax(Double.parseDouble(volumeMax));
			u.setGeracaoMin(Double.parseDouble(geracaoMin));
			u.setGeracaoMax(Double.parseDouble(geracaoMax));
				
			System.out.println("Nome da Usina: "+u.getNome());
			System.out.println("Preço da Energia: "+u.getPrecoEnergia());
			System.out.println("Volume Min: "+u.getVolumeMin());
			System.out.println("Volume Max: "+u.getVolumeMax());
			System.out.println("Geracao Min: "+u.getGeracaoMin());
			System.out.println("Geracao Max: "+u.getGeracaoMax());
			
		} else{
			System.out.println("fim");
			doDelete();
		}
	}
	protected void takeDown(){
		System.out.println(" Agente <"+ getAID().getLocalName()+"> Terminado");
	}
}
