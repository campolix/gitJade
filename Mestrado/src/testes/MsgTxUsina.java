package testes;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class MsgTxUsina extends Agent{

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
	
	
	addBehaviour(new RecebeMsg());
	
	
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
	
	public class RecebeMsg extends CyclicBehaviour{

		private static final long serialVersionUID = 1L;
		private String precoString;
		private Double precoComercializado;
		@Override
		public void action() {
			
			System.out.println("---->Agente "+getLocalName()+":\n" +
					"            waiting for REQUEST message...");
			ACLMessage msg = blockingReceive();
			
			if (msg == null){
				block();
				return;
			}
			
			if (msg.getPerformative()==ACLMessage.REQUEST){
				
				System.out.println("---->Agente <"+ getAID().getLocalName()+">:\n" +
						"           Recebi uma msg do agente: <"+msg.getSender().getLocalName()+"\n" +
						"           A performativa da msg é: "+ msg.getPerformative()+"\n" +
						"           A ontologoia da mensagem é: "+msg.getOntology()+"\n" +
						"           O conteúdo da mensagem é: "+msg.getContent());
			
			precoString = msg.getContent();	
			precoComercializado = Double.parseDouble(precoString+10);
			precoString = String.valueOf(precoComercializado);
			
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			reply.setOntology("Usina");
			reply.setContent(precoString);
			myAgent.send(reply);
			System.out.println("---->Agente <"+ getAID().getLocalName()+">:\n" +
					"           Muda performativa para INFORM da msg recebida: <"+reply.getSender().getLocalName()+"\n" +
					"           A nova performativa da msg é: "+ reply.getPerformative()+"\n" +
					"           A ontologoia da mensagem é: "+reply.getOntology()+"\n" +
					"           O conteúdo da mensagem é: "+reply.getContent());
			
			}
		}
		
	}// fim RecebeMsg
	
} //fim classe MgsTX

