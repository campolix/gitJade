package testes;


//import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class BuscaDF extends Agent {

	private static final long serialVersionUID = 1L;
	
	//private AID[] agentesOS;
	
	protected void setup(){
		
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName("os");
		sd.setType("os");
		dfd.addServices(sd);
		try{
			DFAgentDescription[] result = DFService.search(this, dfd);
			System.out.println(result.length + " results" );
			if(result.length>0)
				System.out.println(" " + result[0].getName() );
			
			/*
			 * for (int i = 1; i < result.length; i++){
				String out = result[i].getName().getLocalName();
				System.out.println(out);
				
				Iterator iter = result[i].getAllServices();
				while (iter.hasNext()) {
					ServiceDescription SD = (ServiceDescription) iter.next();
					out += " " +SD.getName();
					System.out.println(out);
				}
				
					
			}// fim: for
			*/
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		System.out.println("--AgOS--> Agente <"+ getAID().getLocalName()+"> Iniciado");

		
	
				
		
	}//fim do setup()
	
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
	
	
} //fim da classe BuscaDF
