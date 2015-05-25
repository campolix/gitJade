package br.edu.ufg.master.base;



public class VariaveisBase {
	public static double precoComercializado = 250.0; 
	public static double demanda = 4000;
	
	public static double getPrecoComercializado() {
		return precoComercializado;
	}
	public static void setPrecoComercializado(double precoComercializado) {
		VariaveisBase.precoComercializado = precoComercializado;
	}
	public static double getDemanda() {
		return demanda;
	}
	public static void setDemanda(double demanda) {
		VariaveisBase.demanda = demanda;
	}
	
	
}
