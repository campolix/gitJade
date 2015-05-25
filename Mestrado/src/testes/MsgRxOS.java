package testes;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class MsgRxOS extends Agent{

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
		
		addBehaviour(new OSRecebeDados());
		
	}// fim Setup();
	
	protected void takeDown(){
		//Desregistrando do DF
		try{
			DFService.deregister(this);
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		System.out.println("----> Agente "+getAID().getLocalName()+"terminando.");
	}// fim takeDown
	
	
	public class OSRecebeDados extends CyclicBehaviour {
		static final long serialVersionUID = 1L;

		public void action(){
			
			ACLMessage msg = blockingReceive();			
			if(msg == null){
				block();
				return;
			}
			
			if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology() == "Mercado"){
				
				
				System.out.println("---->Agente <"+ getAID().getLocalName()+">:\n" +
						"           Recebi uma msg do agente: <"+msg.getSender().getLocalName()+"\n" +
						"           A performativa da msg é: "+ msg.getPerformative()+"\n" +
						"           A ontologoia da mensagem é: "+msg.getOntology()+"\n" +
						"           O conteúdo da mensagem é: "+msg.getContent());
				
				msg.setPerformative(ACLMessage.REQUEST);
				msg.setSender(myAgent.getAID());
				msg.addReceiver(new AID("usina", AID.ISLOCALNAME));
				send(msg);
				
				
				System.out.println("---->Agente <"+ getAID().getLocalName()+">:\n" +
						"           Envia uma msg ao: usina \n" +
						"           A performativa da msg é: "+ msg.getPerformative()+"\n" +
						"           A ontologoia da mensagem é: "+msg.getOntology()+"\n" +
						"           O conteúdo da mensagem é: "+msg.getContent());
					
				block();		
			}
			
			if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology() == "Usina"){
				System.out.println("---->Agente <"+ getAID().getLocalName()+">:\n" +
						"           Recebi uma msg do agente: <"+msg.getSender().getLocalName()+"\n" +
						"           A performativa da msg é: "+ msg.getPerformative()+"\n" +
						"           A ontologoia da mensagem é: "+msg.getOntology()+"\n" +
						"           O conteúdo da mensagem é: "+msg.getContent());
				block();
			}
			//System.out.println("--AgOS--> Agente <"+ getAID().getLocalName()+"> Esperando próxima mensagem");
			
		
		} // fim do action()
	
		}// fim classe OSRecebeDados
}

