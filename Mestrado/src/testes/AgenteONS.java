package testes;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

// parametros de inicialização
//-gui Sudeste:AgenteMercado;ons:AgenteOS;tucurui:AgenteUsina(100,Tucurui,0,5000,50,4000);BeloMonte:AgenteUsina(80,BeloMonte,0,8000,50,5000);
// Argumentos quartel: -gui Sudeste:AgenteMercado;ons:AgenteOS;BeloMonte:AgenteUsina(100,Furnas,0,2000,50,1500);Itaipu:AgenteUsina(150,Itaipu,0,5000,10,4500);Tucurui:AgenteUsina(120,tucurui,0,15000,80,10500);

public class AgenteONS extends Agent{

	private static final long serialVersionUID = 1L;	
	private double precoMercado;
	private double carga;
	//private AID[] hidro;
	
	
	protected void setup(){
		
		//-------------  Registrando Agente no DF
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
		
		System.out.println("--AgOS--> Agente <"+ getAID().getLocalName()+"> Iniciado");
		System.out.println("--AgOS--> Agente <"+ getAID().getLocalName()+"> registrou Serviço no DF");

		
		// -----------------  comportamentos ------------------
		
		addBehaviour(new OSRecebeDadosMercado());
		//System.out.println("--AgOS--> comportamento OSRecebeDados passou");
		//addBehaviour(new InformaUsinaPrecoMercado());
		//addBehaviour(new OnsRecebeDadosMercado());
		//addBehaviour(new OnsRecebeDadosUsinas());		
		//addBehaviour(new Despacho());
		//System.out.println("------------- Próximo passo: Criar metodo que lista usinas pelo preço----------");
		
		
	} //fim Setup

	
	
	
	// -----------------   Classes Privadas do OS
	
	
	
	public class OSRecebeDadosMercado extends CyclicBehaviour {
		static final long serialVersionUID = 1L;

		public void action(){
			
			//Recebendo Mensagem Com Preço do mercado		
			MessageTemplate mt1 = MessageTemplate.MatchOntology("Mercado");
			ACLMessage msg = myAgent.receive(mt1);
			
			if(msg != null){	
				String preco = msg.getContent();
				precoMercado = Double.parseDouble(preco);
				System.out.println("--AgOS--> OS recebe mensagem de <"+msg.getSender().getLocalName()+">\n O conteúdo da mensagem é: "+msg.getContent());
				
				
				//  ----- Informando preço do Mercado para agentes usina
				InformaUsinaDadosMercado();				
					
				}else{
					System.out.println("--AgOS--> OS esperando Preço da Energia");
					
					block();
			}			
			
		}
	} // fim classe OSRecebeDadosMercado
	
	
	
	
	public class OSRecebeDados extends CyclicBehaviour {
		static final long serialVersionUID = 1L;

		public void action(){
			
			ACLMessage msg = blockingReceive();
			if(msg == null){
				block();
				return;
			}
			
			if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology() == "Mercado"){
				//addBehaviour(new InformaUsinaDadosMercado());
				
				System.out.println("--AgOS--> A mensagem veio de <"+msg.getSender().getLocalName());
				System.out.println("A performativa da msg é: "+ msg.getPerformative());
				System.out.println("A ontologoia da mensagem é: "+msg.getOntology());
				System.out.println("O conteúdo da mensagem é: "+msg.getContent());
				
				String preco = msg.getContent();
				precoMercado = Double.parseDouble(preco);
				addBehaviour(new InformaUsinaDadosMercado());	
				
			}else if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology() == "Usina"){
				System.out.println("--AgOS--> A mensagem veio de <"+msg.getSender().getLocalName());
				System.out.println("A performativa da msg é: "+ msg.getPerformative());
				System.out.println("A ontologoia da mensagem é: "+msg.getOntology());
				System.out.println("O conteúdo da mensagem é: "+msg.getContent());
				
			}/*else{
					System.out.println("--AgOS--> OS esperando Preço da Energia");
			
					block();
			}	*/
		}
	} // fim classe OSRecebeDados
	
	private class InformaUsinaDadosMercado extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID("tucurui", AID.ISLOCALNAME));
			msg.setOntology("Preco-Energia");
			msg.setContent(String.valueOf(precoMercado));
			myAgent.send(msg);
			System.out.println("--AgOS--> OS envia <"+msg.getContent()+"> para Agente Usina");
			//System.out.println("--AgOS--> ###### teste 2"+msg.getContent());

		}
		
	} //Fim da classe: InformaPrecoEnergia
	
	
	private class Teste extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			
			System.out.println("--AgOS--> ######### Teste ##########");
			//System.out.println("--AgOS--> ###### teste 2"+msg.getContent());

		}
		
	} 
	
	protected void takeDown(){
		//Desregistrando do DF
		try{
			DFService.deregister(this);
		}
		catch(FIPAException fe){
			fe.printStackTrace();
		}
		
		//System.out.println("--AgOS--> Agente "+getAID().getLocalName()+"terminando.");
		//System.out.println("--AgMercado--> Agente "+getAID().getLocalName()+"terminando.");

	}

	
	public void InformaUsinaDadosMercado(){

		
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(new AID("tucurui", AID.ISLOCALNAME));
			msg.setOntology("Preco-Energia");
			msg.setContent(String.valueOf(precoMercado));
			send(msg);
			System.out.println("--AgOS--> OS envia <"+msg.getContent()+"> para Agente Usina");
			//System.out.println("--AgOS--> ###### teste 2"+msg.getContent());

		
		
	} //Fim do método:  InformaUsinaDadosMercado()
	
	
	public double getPrecoMercado() {
		return precoMercado;
	}
	public void setPrecoMercado(double precoMercado) {
		this.precoMercado = precoMercado;
	}
	public double getCarga() {
		return carga;
	}
	public void setCarga(double carga) {
		this.carga = carga;
	}
	
}


// --------------- Códigos aproveitados

/*//inicio class RecebeDadosUsinas
private class RecebeDadosUsinas extends CyclicBehaviour{
	private static final long serialVersionUID = 1L;

	public void action(){
		MessageTemplate mt1 = MessageTemplate.MatchOntology("Dados de Usina");
		//MessageTemplate mt2 = MessageTemplate.MatchContent("conteudo");
		//MessageTemplate mt3 = MessageTemplate.and(mt1, mt2);
		ACLMessage msg = receive(mt1);
		
		if(msg != null){
			String conteudo = msg.getContent();
			String remetente = msg.getSender().getLocalName();
			System.out.println("ONS recebe mensagem de "+remetente+" O conteúdo da mensagem é : "+conteudo);	
			
				//Método que lista as usinas por preço
				
				//confimar recebimento de dados da usina
				
				ACLMessage confirma = new ACLMessage(ACLMessage.INFORM);
				confirma.addReceiver(new AID(remetente, AID.ISLOCALNAME));
				confirma.setLanguage("Portugues");
				confirma.setOntology("Confirmação de resposta");
				confirma.setContent("Usina " +msg.getSender().getLocalName()+", recebi e atualizei os dados");
				send(confirma);						
				
		}else{
			//System.out.println("ONS esperando algum status delta");
			block();
		}
	}
	
	
}

private class Despacho extends OneShotBehaviour{
		
		private static final long serialVersionUID = 1L;

		public void action(){
			System.out.println("--AgOS--> Comportamento Despacho iniciado");
			
			//listaUsinaDespacho();
			System.out.println("--AgOS--> Carregou metodo listaUsina do comportamento Despacho");
		}
		
		
		
	}
	
	private class OnsRecebeDadosUsinas extends CyclicBehaviour{
		private static final long serialVersionUID = 1L;

			public void action(){
				MessageTemplate mt1 = MessageTemplate.MatchOntology("Dados de Usina");
				//MessageTemplate mt2 = MessageTemplate.MatchContent("conteudo");
				//MessageTemplate mt3 = MessageTemplate.and(mt1, mt2);
				ACLMessage msg = myAgent.receive(mt1);
				
				if(msg != null){
					String conteudo = msg.getContent();
					String remetente = msg.getSender().getLocalName();
					System.out.println("--AgOns--> ONS recebe mensagem do Agente: "+remetente+". O conteúdo da mensagem é o preço da Energia: R$"+conteudo);	
					
						//Método que lista as usinas por preço
						
						//confimar recebimento de dados da usina
						/*
						ACLMessage confirma = new ACLMessage(ACLMessage.INFORM);
						confirma.addReceiver(new AID(remetente, AID.ISLOCALNAME));
						confirma.setLanguage("Portugues");
						confirma.setOntology("Confirmação de resposta");
						confirma.setContent("Usina " +msg.getSender().getLocalName()+", recebi e atualizei os dados");
						send(confirma);						
						
				}else{
					//System.out.println("--AgOns-->ONS esperando algum status delta");
					block();
				}
			}
		
	}
	
	private class OnsRecebeDadosMercado extends CyclicBehaviour{
	
		private static final long serialVersionUID = 1L;
				
		public void action() {
			MessageTemplate mt1 = MessageTemplate.MatchOntology("Dados de Mercado");
			MessageTemplate mt2 = MessageTemplate.MatchContent("Preco-energia");
			MessageTemplate mt3 = MessageTemplate.and(mt1, mt2);
			ACLMessage msg = receive( mt3);
			
			if(msg != null){	
				
				String conteudo = msg.getContent();
				String remetente = msg.getSender().getLocalName();
				System.out.println("--AgONS--> OS recebe mensagem de "+msg.getSender().getLocalName()+"\n O conteúdo da mensagem é : "+conteudo);	
				if (conteudo.equalsIgnoreCase("status")){
					System.out.println("--AgOS--> OS Leu: " +msg.getContent()+" de " + msg.getSender().getLocalName());
					System.out.println("--AgOS--> Atualizando dados do Mercado!");
					atualizaDadosMercado();
					
					//confimar recebimento de dados do mercado
					ACLMessage confirma = new ACLMessage(ACLMessage.INFORM);
					confirma.addReceiver(new AID(remetente, AID.ISLOCALNAME));
					confirma.setLanguage("Portugues");
					confirma.setOntology("Confirmação de resposta");
					confirma.setContent(msg.getSender().getLocalName()+", recebi e atualizei os dados");
					send(confirma);
				}else{
					
					System.out.println("--AgOS--> OS esperando algum status");	
				}
				
				
			}else{
				block();
				}
			}
		 private void atualizaDadosMercado(){
			setPrecoMercado(AgenteMercado.precoComercializado);
			setCarga(AgenteMercado.demanda);
			System.out.println("--AgOS--> Preço comercializado no Mercado: " +getPrecoMercado());
			System.out.println("--AgOS--> Demanda no Mercado: " +getCarga());

		}
		 
	}
*//// fim Classe comportamento Ons Recebe dados Usinas
