package testes;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class Solicitante extends Agent{

	private static final long serialVersionUID = 1L;
	
	protected void setup(){
		// Registrando no DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getLocalName());
		sd.setType("buscador");
		dfd.addServices(sd);
		try{
			DFService.register(this, dfd);
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		System.out.println("--AgOS--> Agente <"+ getAID().getLocalName()+"> Iniciado");
		System.out.println("--AgOS--> Agente <"+ getAID().getLocalName()+"> registrou Serviço no DF");
	}// fim Setup();
	
	protected void takeDown(){
		//Desregistrando do DF
		try{
			DFService.deregister(this);
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		System.out.println("--AgMercado--> Agente "+getAID().getLocalName()+"terminando.");
	}// fim takeDown
	
} //fim classe AgenteHumano

