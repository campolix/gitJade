package br.edu.ufg.master.base;



public class Usina {
	
	private String nome;
	private double volumeMin = 0;
	private double volumeMax = 0;
	private double geracaoMin = 0;
	private double geracaoMax = 0;
	private double[] afluencias = new double[6];
	private double precoEnergia = 0;
	
	public Usina(){
	}
	
	public Usina(Double precoEnergia, String nome){
		this.precoEnergia = precoEnergia;
		this.nome = nome;
	}
	
	public Usina(double precoEnergia, String nome, double volumeMin, double volumeMax, double geracaoMin, double geracaoMax ){
		this.nome = nome;
		this.precoEnergia = precoEnergia;
		this.volumeMin=volumeMin;
		this.volumeMax=volumeMax;
		this.geracaoMin=geracaoMin;
		this.geracaoMax=geracaoMax;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getVolumeMin() {
		return volumeMin;
	}
	public void setVolumeMin(double volumeMin) {
		this.volumeMin = volumeMin;
	}
	public double getVolumeMax() {
		return volumeMax;
	}
	public void setVolumeMax(double volumeMax) {
		this.volumeMax = volumeMax;
	}
	public double getGeracaoMin() {
		return geracaoMin;
	}
	public void setGeracaoMin(double geracaoMin) {
		this.geracaoMin = geracaoMin;
	}
	public double getGeracaoMax() {
		return geracaoMax;
	}
	public void setGeracaoMax(double geracaoMax) {
		this.geracaoMax = geracaoMax;
	}
	public double[] getAfluencias() {
		return afluencias;
	}
	public void setAfluencias(double[] afluencias) {
		this.afluencias = afluencias;
	}
	public double getPrecoEnergia() {
		return precoEnergia;
	}
	public void setPrecoEnergia(double precoEnergia) {
		this.precoEnergia = precoEnergia;
	}
	
	
	
}
