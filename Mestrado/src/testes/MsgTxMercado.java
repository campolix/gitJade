package testes;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class MsgTxMercado extends Agent{

	private static final long serialVersionUID = 1L;
	
	protected void setup(){
		// Registrando no DF
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName("os");
		sd.setType("os");
		dfd.addServices(sd);
		try{
			DFService.register(this, dfd);
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		System.out.println("----> Agente <"+getLocalName()+">: \n" +
				"           Agente Iniciado e Serviço Registrado no DF");
	
	
	addBehaviour(new EnviaMsg());
	
	
	}// fim Setup();
	
	protected void takeDown(){
		//Desregistrando do DF
		try{
			DFService.deregister(this);
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		//System.out.println("--AgMercado--> Agente "+getAID().getLocalName()+"terminando.");
	}// fim takeDown
	
	public class EnviaMsg extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(new AID("os", AID.ISLOCALNAME));
			msg.setOntology("Mercado");
			msg.setContent("100");
			send(msg);
			System.out.println("----> Agente <"+getLocalName()+">: \n" +
					"           Envia msg Mercado");
		}
		
	}
	
} //fim classe MgsTX

