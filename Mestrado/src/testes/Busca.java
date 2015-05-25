package testes;


import java.util.Iterator;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class Busca extends Agent{

	private static final long serialVersionUID = 1L;
	
	protected void setup(){
		// Registrando no DF
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName("administrador");
		sd.setType("rede");
		template.addServices(sd);
		try{
			DFAgentDescription[] result = DFService.search(this, template);
			for (int i = 1; i < result.length; i++){
				String out = result[i].getName().getLocalName();
				Iterator iter = result[i].getAllServices();
				while (iter.hasNext()) {
					ServiceDescription SD = (ServiceDescription) iter.next();
					out += " " +SD.getName();
					System.out.println(out);
				}
					
			}// fim: for
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

