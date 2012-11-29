package com.pacote.casaOnline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.zip.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CasaOnlineAppActivity extends Activity {
    private String arquivo ="arq.txt";
    private int n_erros, tela_controle;
    private boolean server_out;
    private int quant=0;
	private String[] dispositivos=null;
	
    public void CarregarTelaPrincipal(){
    	setContentView(R.layout.main);
    	Button login = (Button) findViewById(R.ir.goPage);
    	login.setOnClickListener(new View.OnClickListener(){
    			 public void onClick(View arg0) {
    				 EditText usuario_campo = (EditText) findViewById (R.usuario.user_);
    				 String usuario=usuario_campo.getText().toString();
    					
    				 EditText senha_campo = (EditText) findViewById (R.senha.pass_);
    				 String senha = senha_campo.getText().toString();
    					
    				 EditText chave_campo = (EditText) findViewById (R.chave.key_);
    				 String chave = chave_campo.getText().toString();
    				 
    				 boolean chave_ok, usuario_ok,senha_ok;
    				 if (chave.length()==6){
    					 int v=0;
	    				 for (int i=0;i<chave.length();i++){
	    					 for (int j=0;j<10;j++){
	    					     if ((chave.charAt(i)+"").equalsIgnoreCase(""+j)){
	    					    	 v++;
	    					     }
	    					 }
	    				 }
	    				 if (v==6){
	    					 chave_ok=true;
	    				 }
	    				 else{
	    					 chave_ok=false;
	    				 }
    				 }
    				 else{
    					 chave_ok=false;
    				 }
    				 if (usuario.length()==10 && senha.length()==6){
    					 usuario_ok=true;
    					 senha_ok=true;
    				 }
    				 else{
    					 usuario_ok=false;
    					 senha_ok=false;
    				 }
    				 if (usuario_ok && senha_ok && chave_ok){
    					 String[]x={usuario+"&"+chave+"&"+senha+"&cad",""};
    					 String x_encoded=Base64.criptografa(x);
    					 
    					 String nome_usuario = null, data=null, hora=null, endereco="https://localhost/sco_and_1.php?"+x_encoded;
					 	 boolean acesso_ok=false, servidor_ok=true;
						 try {  
					         // Create a URL for the desired page  
							 URL url = new URL(endereco);
					         // Read all the text returned by the server  
					         BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
					         String str;  
					         while ((str = in.readLine()) != null) {  
					             // str is one line of text; readLine()  
					             if (str.contains("1&")){
					            	 String[] info=str.substring(2).split("&");
					            	 nome_usuario=info[0];
					            	 data=info[2];
					            	 hora=info[3];
					            	 Base64.usuario=usuario;
					            	 Base64.senha=senha;
					            	 Base64.chave=chave;
					            	 Base64.nome=nome_usuario;
					            	 Base64.data=data;
					            	 Base64.hora=hora;
					            	 //gravarArquivo(usuario+"&"+chave+"&"+senha+"&"+nome_usuario);
					                 acesso_ok=true;
					                 servidor_ok=true;
					             }
					             else if (str.contains("0")){
					             	 acesso_ok=false;
					             	 servidor_ok=true;
					             }
					             else if (str.contains("4&")){
					             	 acesso_ok=false;
					             	 servidor_ok=false;
					             }
					         }  
					         in.close();  
					     } 
						 catch (MalformedURLException e) {  
					     } 
						 catch (IOException e) {  
					     }
						 
						 if (acesso_ok && servidor_ok){
    						 CarregaTela1_2(usuario, senha, chave, nome_usuario, data, hora);
    					     Base64.teste=true;
    					 }
    					 else if (!acesso_ok && servidor_ok)
    						 n_erros++;
    					 else if (acesso_ok && !servidor_ok)
    						 CarregaTelaErro();
    					 
    					 if (n_erros==1)
    					     Toast.makeText(CasaOnlineAppActivity.this, "Erro 02 - Acesso negado. Dados incorretos ou usuário não cadastrado!\nVocê tem mais duas tentativas.", Toast.LENGTH_SHORT).show();
    					 else 
    						 if (n_erros==2)
    					         Toast.makeText(CasaOnlineAppActivity.this, "Erro 02 - Acesso negado. Dados incorretos ou usuário não cadastrado!\nVocê tem mais uma tentativa.", Toast.LENGTH_SHORT).show();
    						 else 
        						 if (n_erros==3){
        					         Toast.makeText(CasaOnlineAppActivity.this, "Erro 02 - Acesso negado. Dados incorretos ou usuário não cadastrado!\nSaindo do aplicativo.", Toast.LENGTH_SHORT).show();    					 
    				                 finish();
        						 }
    			     }
    				 else{
    					 Toast.makeText(CasaOnlineAppActivity.this, "Erro 01 - Dados com tamanhos incorretos!", Toast.LENGTH_SHORT).show();
    				 }
    			 }
        });

    }
    
    public void CarregaTela1_2(final String usuario, final String senha, final String chave, final String nome_user, final String data, final String hora) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tela1_2);
		
		String[]x={usuario+"&"+chave+"&"+senha+"&cad",""};
		 String x_encoded=Base64.criptografa(x);
		 
		 String nome_usuario = null, data_ = null, hora_ = null, endereco="https://localhost/sco_and_1.php?"+x_encoded;
	 	 boolean acesso_ok=false, servidor_ok=true;
		 try {  
	         // Create a URL for the desired page  
			 URL url = new URL(endereco);
	         // Read all the text returned by the server  
	         BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
	         String str;  
	         while ((str = in.readLine()) != null) {  
	             // str is one line of text; readLine()  
	             if (str.contains("1&")){
	            	 String[] info=str.substring(2).split("&");
	            	 nome_usuario=info[0];
	            	 data_=info[2];
	            	 hora_=info[3];
	            	 Base64.usuario=usuario;
	            	 Base64.senha=senha;
	            	 Base64.chave=chave;
	            	 Base64.nome=nome_usuario;
	            	 Base64.data=data;
	            	 Base64.hora=hora;
	            	 //gravarArquivo(usuario+"&"+chave+"&"+senha+"&"+nome_usuario);
	                 servidor_ok=true;
	             }
	             else if (str.contains("0")){
	             	 servidor_ok=true;
	             }
	             else if (str.contains("4&")){
	             	 servidor_ok=false;
	             }
	         }  
	         in.close();  
	     } 
		 catch (MalformedURLException e) {  
	     } 
		 catch (IOException e) {  
	     }
		if (servidor_ok){
			TextView boas_vindas = (TextView) findViewById(R.tela_mediana.boas_vindas);
			boas_vindas.setText("Olá, "+nome_user+"! \nSeu último acesso foi \nno dia "+data_+" e às "+hora_+"!");
		}
		else{
			CarregaTelaErro();
		}
		String[]x1={usuario+"&"+chave+"&"+senha+"=inf?",""};
		String x_encoded1=Base64.criptografa(x1);
    	String endereco1="https://localhost/sco_and_1.php?"+x_encoded1;
		
    	String[] info = null;
		
		try {  
	        // Create a URL for the desired page  
			URL url = new URL(endereco1);
	        // Read all the text returned by the server  
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
	        String str;  
	        while ((str = in.readLine()) != null) {  
	            // str is one line of text; readLine()  
	            if (str.startsWith("1&")){
	            	info=str.substring(2).split("&");
	            	quant=Integer.parseInt(info[5]);
	            	String[] disp = new String[info.length-6];	
	            	for (int i=6;i<=info.length-1;i++)
	            		disp[i-6]=info[i];
	            	dispositivos=disp;
	            }
	        }  
	        in.close();  
	    } 
		catch (MalformedURLException e) {  
	    } 
		catch (IOException e) {  
	    }
		
		Button go = (Button) findViewById(R.tela_mediana.entrar_);
    	go.setOnClickListener(new View.OnClickListener(){
    			 public void onClick(View arg0) {
    				 tela_controle=1;
    				 CarregaTela2(usuario, senha, chave, quant, dispositivos);
    				 
    			 }
        });
    	
    	Button go_out = (Button) findViewById(R.tela_mediana.sair_);
    	go_out.setOnClickListener(new View.OnClickListener(){
    			 public void onClick(View arg0) {
    				 finish();
    			 }
        });
		
    }
    
    public void CarregaTela2(final String usuario, final String senha, final String chave, final int quant, final String[] dispositivos) {
		// TODO Auto-generated method stub
    	setContentView(R.layout.tela2);
		
		monitora_estados(usuario,senha,chave);
		
		if (!server_out){
			if (quant==1){
				TextView nome_obj1 = (TextView)findViewById (R.objeto1_.nome);
				nome_obj1.setText(dispositivos[0]);
				
				Button on_obj1 = (Button) findViewById(R.objeto1_.ligar);
		    	on_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="1";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
			    		     }
		    				 else{
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	Button off_obj1 = (Button) findViewById(R.objeto1_.desligar);
		    	off_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="0";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	Button exit = (Button) findViewById(R.tela2.button_sair);
		    	exit.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String[]x={"cliente_exit?",""};
							 String x_encoded=Base64.criptografa(x);
		    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
							
						         // Create a URL for the desired page  
								 URL url = null;
								try {
									url = new URL(endereco);
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						         // Read all the text returned by the server  
								String retorno_em_html = " ";
								try {
									retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  
						        
								if (retorno_em_html.contains("4&"))
		   						    CarregaTelaErro();
								else {
						            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
							        finish();
								}
		    			 } 
		        });
			}
			
	    	if (quant==2){
	    		TextView nome_obj1 = (TextView)findViewById (R.objeto1_.nome);
				nome_obj1.setText(dispositivos[0]);
				
				Button on_obj1 = (Button) findViewById(R.objeto1_.ligar);
		    	on_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="1";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
			    		     }
		    				 else{
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	Button off_obj1 = (Button) findViewById(R.objeto1_.desligar);
		    	off_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="0";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	TextView nome_obj2 = (TextView)findViewById (R.objeto2_.nome);
				nome_obj2.setText(dispositivos[1]);
		    	
		    	Button on_obj2 = (Button) findViewById(R.objeto2_.ligar);
		    	on_obj2.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=1){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==1){
			    						 new_estados+="1";
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button off_obj2 = (Button) findViewById(R.objeto2_.desligar);
		    	off_obj2.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=1){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==1){
			    						 new_estados+="0";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button exit = (Button) findViewById(R.tela2.button_sair);
		    	exit.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String[]x={"cliente_exit?",""};
							 String x_encoded=Base64.criptografa(x);
		    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
							
						         // Create a URL for the desired page  
								 URL url = null;
								try {
									url = new URL(endereco);
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						         // Read all the text returned by the server  
								String retorno_em_html = " ";
								try {
									retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  
						        
								if (retorno_em_html.contains("4&"))
		   						    CarregaTelaErro();
								else {
						            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
							        finish();
								}
		    			 } 
		        });
	    	}
	    	
	    	if (quant==3){
	    		TextView nome_obj1 = (TextView)findViewById (R.objeto1_.nome);
				nome_obj1.setText(dispositivos[0]);
				
				Button on_obj1 = (Button) findViewById(R.objeto1_.ligar);
		    	on_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="1";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
			    		     }
		    				 else{
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	Button off_obj1 = (Button) findViewById(R.objeto1_.desligar);
		    	off_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="0";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	TextView nome_obj2 = (TextView)findViewById (R.objeto2_.nome);
				nome_obj2.setText(dispositivos[1]);
		    	
		    	Button on_obj2 = (Button) findViewById(R.objeto2_.ligar);
		    	on_obj2.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=1){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==1){
			    						 new_estados+="1";
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button off_obj2 = (Button) findViewById(R.objeto2_.desligar);
		    	off_obj2.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=1){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==1){
			    						 new_estados+="0";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	TextView nome_obj3 = (TextView)findViewById (R.objeto3_.nome);
				nome_obj3.setText(dispositivos[2]);
		    	
		    	Button on_obj3 = (Button) findViewById(R.objeto3_.ligar);
		    	on_obj3.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=2){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==2){
			    						 new_estados+="1";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button off_obj3 = (Button) findViewById(R.objeto3_.desligar);
		    	off_obj3.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=2){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==2){
			    						 new_estados+="0";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button exit = (Button) findViewById(R.tela2.button_sair);
		    	exit.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String[]x={"cliente_exit?",""};
							 String x_encoded=Base64.criptografa(x);
		    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
							
						         // Create a URL for the desired page  
								 URL url = null;
								try {
									url = new URL(endereco);
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						         // Read all the text returned by the server  
								String retorno_em_html = " ";
								try {
									retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  
						        
								if (retorno_em_html.contains("4&"))
		   						    CarregaTelaErro();
								else {
						            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
							        finish();
								}
		    			 } 
		        });
	    	}
	    	
	    	if (quant>=4){
	    		TextView nome_obj1 = (TextView)findViewById (R.objeto1_.nome);
				nome_obj1.setText(dispositivos[0]);
				
				Button on_obj1 = (Button) findViewById(R.objeto1_.ligar);
		    	on_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="1";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
			    		     }
		    				 else{
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	Button off_obj1 = (Button) findViewById(R.objeto1_.desligar);
		    	off_obj1.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 new_estados+="0";
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=0){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    		    }
		        });
		    	
		    	TextView nome_obj2 = (TextView)findViewById (R.objeto2_.nome);
				nome_obj2.setText(dispositivos[1]);
		    	
		    	Button on_obj2 = (Button) findViewById(R.objeto2_.ligar);
		    	on_obj2.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=1){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==1){
			    						 new_estados+="1";
			    					 }
			    				}
			    				
			    				troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button off_obj2 = (Button) findViewById(R.objeto2_.desligar);
		    	off_obj2.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=1){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==1){
			    						 new_estados+="0";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	TextView nome_obj3 = (TextView)findViewById (R.objeto3_.nome);
				nome_obj3.setText(dispositivos[2]);
		    	
		    	Button on_obj3 = (Button) findViewById(R.objeto3_.ligar);
		    	on_obj3.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=2){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==2){
			    						 new_estados+="1";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button off_obj3 = (Button) findViewById(R.objeto3_.desligar);
		    	off_obj3.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=2){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==2){
			    						 new_estados+="0";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	TextView nome_obj4 = (TextView)findViewById (R.objeto4_.nome);
				nome_obj4.setText(dispositivos[3]);
		    	
		    	Button on_obj4 = (Button) findViewById(R.objeto4_.ligar);
		    	on_obj4.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=3){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==3){
			    						 new_estados+="1";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button off_obj4 = (Button) findViewById(R.objeto4_.desligar);
		    	off_obj4.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String estados = getEstados(usuario,senha,chave), new_estados="";
		    				 
		    				 if (!estados.equalsIgnoreCase("erro")){
			    				 for (int i=0;i<estados.length(); i++){
			    					 if (i!=3){
			    					     new_estados+=estados.charAt(i);	 
			    					 }
			    					 else if (i==3){
			    						 new_estados+="0";
			    					 }
			    				 }
			    				
			    				 troca_estados(usuario,senha,chave,new_estados);
		    				 }
		    				 else {
		    				     CarregaTelaErro();	
		    				 }
		    			 }
		        });
		    	
		    	Button go = (Button) findViewById(R.tela_posterior.go);
		    	go.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 tela_controle=2;
		    				 CarregaTela_parte2(usuario, senha, chave);
		    				 
		    			 }
		        });
			
				Button exit = (Button) findViewById(R.tela2.button_sair);
		    	exit.setOnClickListener(new View.OnClickListener(){
		    			 public void onClick(View arg0) {
		    				 String[]x={"cliente_exit?",""};
							 String x_encoded=Base64.criptografa(x);
		    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
							
						         // Create a URL for the desired page  
								 URL url = null;
								try {
									url = new URL(endereco);
								} catch (MalformedURLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						         // Read all the text returned by the server  
								String retorno_em_html = " ";
								try {
									retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}  
						        
								if (retorno_em_html.contains("4&"))
		   						    CarregaTelaErro();
								else {
						            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
							        finish();
								}
		    			 } 
		        });
	    	}
	    }
		else{
			CarregaTelaErro();	
		}
	}
    
    public void CarregaTela_parte2(final String usuario, final String senha, final String chave){
    	setContentView(R.layout.tela2_parte2);
    	
    	monitora_estados(usuario,senha,chave);
    	
    	if (!server_out){
    		if (quant==5){
    			TextView nome_obj5 = (TextView)findViewById (R.objeto5_.nome);
    			nome_obj5.setText(dispositivos[4]);
        		
    	    	Button on_obj5 = (Button) findViewById(R.objeto5_.ligar);
    	    	on_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj5 = (Button) findViewById(R.objeto5_.desligar);
    	    	off_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button go = (Button) findViewById(R.tela_anterior.go_back);
    	    	go.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 tela_controle=1;
    	    				 CarregaTela2(usuario, senha, chave, quant, dispositivos);
    	    			 }
    	        });
    	    	
    	    	Button exit_2 = (Button) findViewById(R.tela2_p2.bt_sair);
    	    	exit_2.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String[]x={"cliente_exit?",""};
    						 String x_encoded=Base64.criptografa(x);
    	    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
    						
    					         // Create a URL for the desired page  
    							 URL url = null;
    							try {
    								url = new URL(endereco);
    							} catch (MalformedURLException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
    					         // Read all the text returned by the server  
    							String retorno_em_html = " ";
    							try {
    								retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
    								
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}  
    							
    							if (retorno_em_html.contains("4&"))
    	    						 CarregaTelaErro();
    							else {
    					            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
    						        finish();
    							}
    	    			 } 
    	        });
    	    }
    		
    		if (quant==6){
    			TextView nome_obj5 = (TextView)findViewById (R.objeto5_.nome);
    			nome_obj5.setText(dispositivos[4]);
        		
    	    	Button on_obj5 = (Button) findViewById(R.objeto5_.ligar);
    	    	on_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj5 = (Button) findViewById(R.objeto5_.desligar);
    	    	off_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	TextView nome_obj6 = (TextView)findViewById (R.objeto6_.nome);
    			nome_obj6.setText(dispositivos[5]);
    	    	
    	    	Button on_obj6 = (Button) findViewById(R.objeto6_.ligar);
    	    	on_obj6.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=5){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==5){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj6 = (Button) findViewById(R.objeto6_.desligar);
    	    	off_obj6.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=5){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==5){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button go = (Button) findViewById(R.tela_anterior.go_back);
    	    	go.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 tela_controle=1;
    	    				 CarregaTela2(usuario, senha, chave, quant, dispositivos);
    	    			 }
    	        });
    	    	
    	    	Button exit_2 = (Button) findViewById(R.tela2_p2.bt_sair);
    	    	exit_2.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String[]x={"cliente_exit?",""};
    						 String x_encoded=Base64.criptografa(x);
    	    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
    						
    					         // Create a URL for the desired page  
    							 URL url = null;
    							try {
    								url = new URL(endereco);
    							} catch (MalformedURLException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
    					         // Read all the text returned by the server  
    							String retorno_em_html = " ";
    							try {
    								retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
    								
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}  
    							
    							if (retorno_em_html.contains("4&"))
    	    						 CarregaTelaErro();
    							else {
    					            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
    						        finish();
    							}
    	    			 } 
    	        });
    		}
    		
    		if (quant==7){
    			TextView nome_obj5 = (TextView)findViewById (R.objeto5_.nome);
    			nome_obj5.setText(dispositivos[4]);
        		
    	    	Button on_obj5 = (Button) findViewById(R.objeto5_.ligar);
    	    	on_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj5 = (Button) findViewById(R.objeto5_.desligar);
    	    	off_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	TextView nome_obj6 = (TextView)findViewById (R.objeto6_.nome);
    			nome_obj6.setText(dispositivos[5]);
    	    	
    	    	Button on_obj6 = (Button) findViewById(R.objeto6_.ligar);
    	    	on_obj6.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=5){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==5){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj6 = (Button) findViewById(R.objeto6_.desligar);
    	    	off_obj6.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=5){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==5){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	TextView nome_obj7 = (TextView)findViewById (R.objeto7_.nome);
    			nome_obj7.setText(dispositivos[6]);
    	    	
    	    	Button on_obj7 = (Button) findViewById(R.objeto7_.ligar);
    	    	on_obj7.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=6){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==6){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj7 = (Button) findViewById(R.objeto7_.desligar);
    	    	off_obj7.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=6){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==6){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button go = (Button) findViewById(R.tela_anterior.go_back);
    	    	go.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 tela_controle=1;
    	    				 CarregaTela2(usuario, senha, chave, quant, dispositivos);
    	    			 }
    	        });
    	    	
    	    	Button exit_2 = (Button) findViewById(R.tela2_p2.bt_sair);
    	    	exit_2.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String[]x={"cliente_exit?",""};
    						 String x_encoded=Base64.criptografa(x);
    	    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
    						
    					         // Create a URL for the desired page  
    							 URL url = null;
    							try {
    								url = new URL(endereco);
    							} catch (MalformedURLException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
    					         // Read all the text returned by the server  
    							String retorno_em_html = " ";
    							try {
    								retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
    								
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}  
    							
    							if (retorno_em_html.contains("4&"))
    	    						 CarregaTelaErro();
    							else {
    					            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
    						        finish();
    							}
    	    			 } 
    	        });
    		}
    		
    		if (quant>=8){
    			TextView nome_obj5 = (TextView)findViewById (R.objeto5_.nome);
    			nome_obj5.setText(dispositivos[4]);
        		
    	    	Button on_obj5 = (Button) findViewById(R.objeto5_.ligar);
    	    	on_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj5 = (Button) findViewById(R.objeto5_.desligar);
    	    	off_obj5.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=4){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==4){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	TextView nome_obj6 = (TextView)findViewById (R.objeto6_.nome);
    			nome_obj6.setText(dispositivos[5]);
    	    	
    	    	Button on_obj6 = (Button) findViewById(R.objeto6_.ligar);
    	    	on_obj6.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=5){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==5){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj6 = (Button) findViewById(R.objeto6_.desligar);
    	    	off_obj6.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=5){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==5){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	TextView nome_obj7 = (TextView)findViewById (R.objeto7_.nome);
    			nome_obj7.setText(dispositivos[6]);
    	    	
    	    	Button on_obj7 = (Button) findViewById(R.objeto7_.ligar);
    	    	on_obj7.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=6){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==6){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj7 = (Button) findViewById(R.objeto7_.desligar);
    	    	off_obj7.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=6){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==6){
    		    						 new_estados+="0";
    		    					 }
    		    				 }
    		    				
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	TextView nome_obj8 = (TextView)findViewById (R.objeto8_.nome);
    			nome_obj8.setText(dispositivos[7]);
    	    	
    	    	Button on_obj8 = (Button) findViewById(R.objeto8_.ligar);
    	    	on_obj8.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=7){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==7){
    		    						 new_estados+="1";
    		    					 }
    		    				 }
    		    				 
    		    				 troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button off_obj8 = (Button) findViewById(R.objeto8_.desligar);
    	    	off_obj8.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String estados = getEstados(usuario,senha,chave), new_estados="";
    	    				 
    	    				 if (!estados.equalsIgnoreCase("erro")){
    		    				 for (int i=0;i<estados.length(); i++){
    		    					 if (i!=7){
    		    					     new_estados+=estados.charAt(i);	 
    		    					 }
    		    					 else if (i==7){
    		    						 new_estados+="0";
    		    					 }
    		    				}
    		    				
    		    				troca_estados(usuario,senha,chave,new_estados);
    	    				 }
    	    				 else {
    	    				     CarregaTelaErro();	
    	    				 }
    	    			 }
    	        });
    	    	
    	    	Button go = (Button) findViewById(R.tela_anterior.go_back);
    	    	go.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 tela_controle=1;
    	    				 CarregaTela2(usuario, senha, chave, quant, dispositivos);
    	    			 }
    	        });
    	    	
    	    	Button exit_2 = (Button) findViewById(R.tela2_p2.bt_sair);
    	    	exit_2.setOnClickListener(new View.OnClickListener(){
    	    			 public void onClick(View arg0) {
    	    				 String[]x={"cliente_exit?",""};
    						 String x_encoded=Base64.criptografa(x);
    	    			 	 String endereco="https://localhost/sco_and_1.php?"+x_encoded;
    						
    					         // Create a URL for the desired page  
    							 URL url = null;
    							try {
    								url = new URL(endereco);
    							} catch (MalformedURLException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
    					         // Read all the text returned by the server  
    							String retorno_em_html = " ";
    							try {
    								retorno_em_html = new Scanner(url.openConnection().getInputStream()).useDelimiter("\\Z").next();
    								
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}  
    							
    							if (retorno_em_html.contains("4&"))
    	    						 CarregaTelaErro();
    							else {
    					            Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
    						        finish();
    							}
    	    			 } 
    	        });
    		}
    	}
    	else{
    		CarregaTelaErro();
    	}
    }
    
    public void CarregaTelaErro(){
    	setContentView(R.layout.tela_erro_servidorcrash);
    	
    	Button go_out = (Button) findViewById(R.tela_erro.button_sair);
    	go_out.setOnClickListener(new View.OnClickListener(){
    			 public void onClick(View arg0) {
    				 Toast.makeText(CasaOnlineAppActivity.this, "Saindo...", Toast.LENGTH_SHORT).show();
				     finish();
    			 }
        });
    }
    
    public String getEstados (String usuario, String senha, String chave){
    	String[]x={usuario+"&"+chave+"&"+senha+"&status",""};
		String x_encoded=Base64.criptografa(x);
    	String endereco="https://localhost/sco_and_1.php?"+x_encoded;
		String estados = null;
		
		try {  
	        // Create a URL for the desired page  
			URL url = new URL(endereco);
	        // Read all the text returned by the server  
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
	        String str;  
	        while ((str = in.readLine()) != null) {  
	            // str is one line of text; readLine()  
	            if (str.startsWith("1&")){
	            	estados=str.substring(2);
	            }
	            else if (str.startsWith("4&"))
					 estados="erro";
	        }  
	        in.close();  
	    } 
		catch (MalformedURLException e) {  
	    } 
		catch (IOException e) {  
	    }
		estados=estados.substring(8-quant);
		
		if (estados.equalsIgnoreCase("erro"))
		    server_out=true;
		else {
			if (tela_controle==1)
			    setEstados(estados);
			else   
				if (tela_controle==2)
					setEstados2(estados);
		    server_out=false;
		}
	     return estados;
    }
    
    public void setEstados(String estados){
    	TextView obj1 = (TextView) findViewById(R.objeto1_.nome);
    	TextView estado_obj1 = (TextView) findViewById (R.objeto1_.estado);
		Button on_obj1 = (Button) findViewById(R.objeto1_.ligar);
    	Button off_obj1 = (Button) findViewById(R.objeto1_.desligar);
    	
    	TextView s2 = (TextView) findViewById (R.id.s2);
    	TextView obj2 = (TextView) findViewById(R.objeto2_.nome);
    	TextView estado_obj2 = (TextView) findViewById (R.objeto2_.estado);
		Button on_obj2 = (Button) findViewById(R.objeto2_.ligar);
    	Button off_obj2 = (Button) findViewById(R.objeto2_.desligar);
    	
    	TextView s3 = (TextView) findViewById (R.id.s3);
    	TextView obj3 = (TextView) findViewById(R.objeto3_.nome);
    	TextView estado_obj3 = (TextView) findViewById (R.objeto3_.estado);
		Button on_obj3 = (Button) findViewById(R.objeto3_.ligar);
    	Button off_obj3 = (Button) findViewById(R.objeto3_.desligar);
    	
    	TextView s4 = (TextView) findViewById (R.id.s4);
    	TextView obj4 = (TextView) findViewById(R.objeto4_.nome);
    	TextView estado_obj4 = (TextView) findViewById (R.objeto4_.estado);
		Button on_obj4 = (Button) findViewById(R.objeto4_.ligar);
    	Button off_obj4 = (Button) findViewById(R.objeto4_.desligar);
    	
    	if (quant==1){
	    	if (estados.charAt(0)=='0'){
				estado_obj1.setText("    Desligado");
				on_obj1.setEnabled(true);
				off_obj1.setEnabled(false);
				obj1.setTextColor(Color.RED);
			}
			else if (estados.charAt(0)=='1'){
				estado_obj1.setText("    Ligado");
				on_obj1.setEnabled(false);
				off_obj1.setEnabled(true);
				obj1.setTextColor(Color.GREEN);
			}
	    	
	    	s2.setVisibility(View.GONE);
	    	obj2.setVisibility(View.GONE);
	    	estado_obj2.setVisibility(View.GONE);
	    	on_obj2.setVisibility(View.GONE);
	    	off_obj2.setVisibility(View.GONE);
	    	
	    	s3.setVisibility(View.GONE);
	    	obj3.setVisibility(View.GONE);
	    	estado_obj3.setVisibility(View.GONE);
	    	on_obj3.setVisibility(View.GONE);
	    	off_obj3.setVisibility(View.GONE);
	    	
	    	s4.setVisibility(View.GONE);
	    	obj4.setVisibility(View.GONE);
	    	estado_obj4.setVisibility(View.GONE);
	    	on_obj4.setVisibility(View.GONE);
	    	off_obj4.setVisibility(View.GONE);
	    	
	    	Button go = (Button) findViewById(R.tela_posterior.go);
	    	go.setVisibility(View.GONE);
	    }
    	
    	if (quant==2){
    		if (estados.charAt(0)=='0'){
				estado_obj1.setText("    Desligado");
				on_obj1.setEnabled(true);
				off_obj1.setEnabled(false);
				obj1.setTextColor(Color.RED);
			}
			else if (estados.charAt(0)=='1'){
				estado_obj1.setText("    Ligado");
				on_obj1.setEnabled(false);
				off_obj1.setEnabled(true);
				obj1.setTextColor(Color.GREEN);
			}
	    	
	    	if (estados.charAt(1)=='0'){
				estado_obj2.setText("    Desligado");
				on_obj2.setEnabled(true);
				off_obj2.setEnabled(false);
				obj2.setTextColor(Color.RED);
			}
			else if (estados.charAt(1)=='1'){
				estado_obj2.setText("    Ligado");
				on_obj2.setEnabled(false);
				off_obj2.setEnabled(true);
				obj2.setTextColor(Color.GREEN);
			}
	    	
	    	s3.setVisibility(View.GONE);
	    	obj3.setVisibility(View.GONE);
	    	estado_obj3.setVisibility(View.GONE);
	    	on_obj3.setVisibility(View.GONE);
	    	off_obj3.setVisibility(View.GONE);
	    	
	    	s4.setVisibility(View.GONE);
	    	obj4.setVisibility(View.GONE);
	    	estado_obj4.setVisibility(View.GONE);
	    	on_obj4.setVisibility(View.GONE);
	    	off_obj4.setVisibility(View.GONE);
	    	
	    	Button go = (Button) findViewById(R.tela_posterior.go);
	    	go.setVisibility(View.GONE);
    	}
    	
    	if (quant==3){
    		if (estados.charAt(0)=='0'){
				estado_obj1.setText("    Desligado");
				on_obj1.setEnabled(true);
				off_obj1.setEnabled(false);
				obj1.setTextColor(Color.RED);
			}
			else if (estados.charAt(0)=='1'){
				estado_obj1.setText("    Ligado");
				on_obj1.setEnabled(false);
				off_obj1.setEnabled(true);
				obj1.setTextColor(Color.GREEN);
			}
	    	
	    	if (estados.charAt(1)=='0'){
				estado_obj2.setText("    Desligado");
				on_obj2.setEnabled(true);
				off_obj2.setEnabled(false);
				obj2.setTextColor(Color.RED);
			}
			else if (estados.charAt(1)=='1'){
				estado_obj2.setText("    Ligado");
				on_obj2.setEnabled(false);
				off_obj2.setEnabled(true);
				obj2.setTextColor(Color.GREEN);
			}
	    	
	    	if (estados.charAt(2)=='0'){
				estado_obj3.setText("    Desligado");
				on_obj3.setEnabled(true);
				off_obj3.setEnabled(false);
				obj3.setTextColor(Color.RED);
			}
			else if (estados.charAt(2)=='1'){
				estado_obj3.setText("    Ligado");
				on_obj3.setEnabled(false);
				off_obj3.setEnabled(true);
				obj3.setTextColor(Color.GREEN);
			}
	    	
	    	s4.setVisibility(View.GONE);
	    	obj4.setVisibility(View.GONE);
	    	estado_obj4.setVisibility(View.GONE);
	    	on_obj4.setVisibility(View.GONE);
	    	off_obj4.setVisibility(View.GONE);
	    	
	    	Button go = (Button) findViewById(R.tela_posterior.go);
	    	go.setVisibility(View.GONE);
    	}
    	
    	if (quant>=4){
    		if (estados.charAt(0)=='0'){
				estado_obj1.setText("    Desligado");
				on_obj1.setEnabled(true);
				off_obj1.setEnabled(false);
				obj1.setTextColor(Color.RED);
			}
			else if (estados.charAt(0)=='1'){
				estado_obj1.setText("    Ligado");
				on_obj1.setEnabled(false);
				off_obj1.setEnabled(true);
				obj1.setTextColor(Color.GREEN);
			}
	    	
	    	if (estados.charAt(1)=='0'){
				estado_obj2.setText("    Desligado");
				on_obj2.setEnabled(true);
				off_obj2.setEnabled(false);
				obj2.setTextColor(Color.RED);
			}
			else if (estados.charAt(1)=='1'){
				estado_obj2.setText("    Ligado");
				on_obj2.setEnabled(false);
				off_obj2.setEnabled(true);
				obj2.setTextColor(Color.GREEN);
			}
	    	
	    	if (estados.charAt(2)=='0'){
				estado_obj3.setText("    Desligado");
				on_obj3.setEnabled(true);
				off_obj3.setEnabled(false);
				obj3.setTextColor(Color.RED);
			}
			else if (estados.charAt(2)=='1'){
				estado_obj3.setText("    Ligado");
				on_obj3.setEnabled(false);
				off_obj3.setEnabled(true);
				obj3.setTextColor(Color.GREEN);
			}
	    	
	    	if (estados.charAt(3)=='0'){
				estado_obj4.setText("    Desligado");
				on_obj4.setEnabled(true);
				off_obj4.setEnabled(false);
				obj4.setTextColor(Color.RED);
			}
			else if (estados.charAt(3)=='1'){
				estado_obj4.setText("    Ligado");
				on_obj4.setEnabled(false);
				off_obj4.setEnabled(true);
				obj4.setTextColor(Color.GREEN);
			}
	    	
	    	Button go = (Button) findViewById(R.tela_posterior.go);
	    	go.setVisibility(View.VISIBLE);
    	}
    	
    }
    
    public void setEstados2(String estados){
    	TextView obj5 = (TextView) findViewById(R.objeto5_.nome);
    	TextView estado_obj5 = (TextView) findViewById (R.objeto5_.estado);
		Button on_obj5 = (Button) findViewById(R.objeto5_.ligar);
    	Button off_obj5 = (Button) findViewById(R.objeto5_.desligar);
    	
    	TextView s6 = (TextView) findViewById (R.id.s6);
    	TextView obj6 = (TextView) findViewById(R.objeto6_.nome);
    	TextView estado_obj6 = (TextView) findViewById (R.objeto6_.estado);
		Button on_obj6 = (Button) findViewById(R.objeto6_.ligar);
    	Button off_obj6 = (Button) findViewById(R.objeto6_.desligar);
    	
    	TextView s7 = (TextView) findViewById (R.id.s7);
    	TextView obj7 = (TextView) findViewById(R.objeto7_.nome);
    	TextView estado_obj7 = (TextView) findViewById (R.objeto7_.estado);
		Button on_obj7 = (Button) findViewById(R.objeto7_.ligar);
    	Button off_obj7 = (Button) findViewById(R.objeto7_.desligar);
    	
    	TextView s8 = (TextView) findViewById (R.id.s8);
    	TextView obj8 = (TextView) findViewById(R.objeto8_.nome);
    	TextView estado_obj8 = (TextView) findViewById (R.objeto8_.estado);
		Button on_obj8 = (Button) findViewById(R.objeto8_.ligar);
    	Button off_obj8 = (Button) findViewById(R.objeto8_.desligar);
    	
    	if (quant==5){
	    	if (estados.charAt(4)=='0'){
				estado_obj5.setText("    Desligado");
				on_obj5.setEnabled(true);
				off_obj5.setEnabled(false);
				obj5.setTextColor(Color.RED);
			}
			else if (estados.charAt(4)=='1'){
				estado_obj5.setText("    Ligado");
				on_obj5.setEnabled(false);
				off_obj5.setEnabled(true);
				obj5.setTextColor(Color.GREEN);
			}
	    	
	    	s6.setVisibility(View.GONE);
	    	obj6.setVisibility(View.GONE);
	    	estado_obj6.setVisibility(View.GONE);
	    	on_obj6.setVisibility(View.GONE);
	    	off_obj6.setVisibility(View.GONE);
	    	
	    	s7.setVisibility(View.GONE);
	    	obj7.setVisibility(View.GONE);
	    	estado_obj7.setVisibility(View.GONE);
	    	on_obj7.setVisibility(View.GONE);
	    	off_obj7.setVisibility(View.GONE);
	    	
	    	s8.setVisibility(View.GONE);
	    	obj8.setVisibility(View.GONE);
	    	estado_obj8.setVisibility(View.GONE);
	    	on_obj8.setVisibility(View.GONE);
	    	off_obj8.setVisibility(View.GONE);
    	}
    	
    	if (quant==6){
    		if (estados.charAt(4)=='0'){
				estado_obj5.setText("    Desligado");
				on_obj5.setEnabled(true);
				off_obj5.setEnabled(false);
				obj5.setTextColor(Color.RED);
			}
			else if (estados.charAt(4)=='1'){
				estado_obj5.setText("    Ligado");
				on_obj5.setEnabled(false);
				off_obj5.setEnabled(true);
				obj5.setTextColor(Color.GREEN);
			}
    		
    		if (estados.charAt(5)=='0'){
				estado_obj6.setText("    Desligado");
				on_obj6.setEnabled(true);
				off_obj6.setEnabled(false);
				obj6.setTextColor(Color.RED);
			}
			else if (estados.charAt(5)=='1'){
				estado_obj6.setText("    Ligado");
				on_obj6.setEnabled(false);
				off_obj6.setEnabled(true);
				obj6.setTextColor(Color.GREEN);
			}
    		
    		s7.setVisibility(View.GONE);
    		obj7.setVisibility(View.GONE);
	    	estado_obj7.setVisibility(View.GONE);
	    	on_obj7.setVisibility(View.GONE);
	    	off_obj7.setVisibility(View.GONE);
	    	
	    	s8.setVisibility(View.GONE);
	    	obj8.setVisibility(View.GONE);
	    	estado_obj8.setVisibility(View.GONE);
	    	on_obj8.setVisibility(View.GONE);
	    	off_obj8.setVisibility(View.GONE);
    	}
    	
    	if (quant==7){
    		if (estados.charAt(4)=='0'){
				estado_obj5.setText("    Desligado");
				on_obj5.setEnabled(true);
				off_obj5.setEnabled(false);
				obj5.setTextColor(Color.RED);
			}
			else if (estados.charAt(4)=='1'){
				estado_obj5.setText("    Ligado");
				on_obj5.setEnabled(false);
				off_obj5.setEnabled(true);
				obj5.setTextColor(Color.GREEN);
			}
    		
    		if (estados.charAt(5)=='0'){
				estado_obj6.setText("    Desligado");
				on_obj6.setEnabled(true);
				off_obj6.setEnabled(false);
				obj6.setTextColor(Color.RED);
			}
			else if (estados.charAt(5)=='1'){
				estado_obj6.setText("    Ligado");
				on_obj6.setEnabled(false);
				off_obj6.setEnabled(true);
				obj6.setTextColor(Color.GREEN);
			}
    		
    		if (estados.charAt(6)=='0'){
				estado_obj7.setText("    Desligado");
				on_obj7.setEnabled(true);
				off_obj7.setEnabled(false);
				obj7.setTextColor(Color.RED);
			}
			else if (estados.charAt(6)=='1'){
				estado_obj7.setText("    Ligado");
				on_obj7.setEnabled(false);
				off_obj7.setEnabled(true);
				obj7.setTextColor(Color.GREEN);
			}
    		
    		s8.setVisibility(View.GONE);
    		obj8.setVisibility(View.GONE);
	    	estado_obj8.setVisibility(View.GONE);
	    	on_obj8.setVisibility(View.GONE);
	    	off_obj8.setVisibility(View.GONE);
    	}
    	
    	if (quant>=8){
    		if (estados.charAt(4)=='0'){
				estado_obj5.setText("    Desligado");
				on_obj5.setEnabled(true);
				off_obj5.setEnabled(false);
				obj5.setTextColor(Color.RED);
			}
			else if (estados.charAt(4)=='1'){
				estado_obj5.setText("    Ligado");
				on_obj5.setEnabled(false);
				off_obj5.setEnabled(true);
				obj5.setTextColor(Color.GREEN);
			}
    		
    		if (estados.charAt(5)=='0'){
				estado_obj6.setText("    Desligado");
				on_obj6.setEnabled(true);
				off_obj6.setEnabled(false);
				obj6.setTextColor(Color.RED);
			}
			else if (estados.charAt(5)=='1'){
				estado_obj6.setText("    Ligado");
				on_obj6.setEnabled(false);
				off_obj6.setEnabled(true);
				obj6.setTextColor(Color.GREEN);
			}
    		
    		if (estados.charAt(6)=='0'){
				estado_obj7.setText("    Desligado");
				on_obj7.setEnabled(true);
				off_obj7.setEnabled(false);
				obj7.setTextColor(Color.RED);
			}
			else if (estados.charAt(6)=='1'){
				estado_obj7.setText("    Ligado");
				on_obj7.setEnabled(false);
				off_obj7.setEnabled(true);
				obj7.setTextColor(Color.GREEN);
			}
    		
    		if (estados.charAt(7)=='0'){
				estado_obj8.setText("    Desligado");
				on_obj8.setEnabled(true);
				off_obj8.setEnabled(false);
				obj8.setTextColor(Color.RED);
			}
			else if (estados.charAt(7)=='1'){
				estado_obj8.setText("    Ligado");
				on_obj8.setEnabled(false);
				off_obj8.setEnabled(true);
				obj8.setTextColor(Color.GREEN);
			}
    	}
	}
    
    public void monitora_estados(String usuario, String senha, String chave){
    	String[]x={usuario+"&"+chave+"&"+senha+"&status",""};
		String x_encoded=Base64.criptografa(x);
    	String endereco="https://localhost/sco_and_1.php?"+x_encoded;
		String estados = null;
		
		try {  
	        // Create a URL for the desired page  
			URL url = new URL(endereco);
	        // Read all the text returned by the server  
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
	        String str;  
	        while ((str = in.readLine()) != null) {  
	            // str is one line of text; readLine()  
	            if (str.startsWith("1&")){
	            	estados=str.substring(2);
	            }
	            else if (str.startsWith("4&"))
					 estados="erro";
	        }  
	        in.close();  
	    } 
		catch (MalformedURLException e) {  
	    } 
		catch (IOException e) {  
	    }
		if (estados.equalsIgnoreCase("erro"))
		    server_out=true;
		else {
			if (tela_controle==1)
			    setEstados(estados);
			else
				if (tela_controle==2)
					setEstados2(estados);
		    server_out=false;
		}
    }
    
    public void troca_estados(String usuario, String senha, String chave, String estados){
    	
    	for (int i=1;i<=8;i++){
    		if (i==estados.length()){
    			String complete="";
    			for (int j=1;j<(8-estados.length());j++)
    				complete+="0";
    			estados=complete+estados;
    		}
    	}
    	
    	String[]x={usuario+"&"+chave+"&"+senha+"@"+estados,""};
		String[] x_={calc_crc(x[0])+"&"+Base64.criptografa(x),""};
		String x_encoded=Base64.criptografa(x_);
    	String endereco="https://localhost/sco_and_1.php?"+x_encoded;
		String new_estados = null;
		
		try {  
	        // Create a URL for the desired page  
			URL url = new URL(endereco);
	        // Read all the text returned by the server  
			
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
	        String str;  
	        while ((str = in.readLine()) != null) {  
	            // str is one line of text; readLine()  
	            if (str.startsWith("0") || str.startsWith("1")){
	            	new_estados=str;
	            }
	            else if (str.startsWith("4&"))
					 CarregaTelaErro();
	        }  
	        in.close();  
	    } 
		catch (MalformedURLException e) {  
	    } 
		catch (IOException e) {  
	    }
		
		new_estados=new_estados.substring(8-quant);
		
		if (new_estados!=null){
		    if (tela_controle==1)
			    setEstados(new_estados);
		    else 
		    	if (tela_controle==2)
		    		setEstados2(new_estados);
		    
		}
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String[]dados=lerArquivo().split("\n");
        if (!Base64.teste){
            CarregarTelaPrincipal();
            n_erros=0;
        }
        else 
        	if (Base64.teste){
        	    //String[]info=dados[dados.length-1].split("&");
        	    //CarregaTela1_2(info[0],info[2],info[1],info[3]);
        		CarregaTela1_2(Base64.usuario,Base64.senha,Base64.chave,Base64.nome, Base64.data, Base64.hora);
        	}
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_app, menu);
	    return true;
    }
		
   
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		
	    switch (item.getItemId()) {
	    case R.id.ajuda:
	    	alertDialog.setTitle("Ajuda");
        	alertDialog.setMessage(getText(R.string.ajuda));
        	alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
			      
			   }
			});
        	alertDialog.show();
	        return true;
	        
	    case R.id.sobre:
        	alertDialog.setTitle("Sobre o Sistema Casa Online");
        	alertDialog.setMessage(getText(R.string.sobre));
        	alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
			      
			   }
			});
        	alertDialog.show();
	        return true;
	        
	    case R.id.config:
	    	if (!Base64.usuario.equalsIgnoreCase("") && !Base64.chave.equalsIgnoreCase("") && !Base64.senha.equalsIgnoreCase("")){
	        	setContentView(R.layout.tela_config_senha);
	        	
	        	final EditText old_pass = (EditText) findViewById (R.tela_config.old_pass);
	        	final EditText new_pass = (EditText) findViewById (R.tela_config.new_pass);
	        	final EditText new_pass_repeat = (EditText) findViewById (R.tela_config.new_pass_repeat);
	        	
	        	Button config = (Button) findViewById (R.tela_config.change);   
	        	config.setOnClickListener(new View.OnClickListener(){
	   			public void onClick(View arg0) {
	   				if (new_pass.getText().toString().equalsIgnoreCase(new_pass_repeat.getText().toString())){
			   			if (new_pass.getText().toString().length()==6 && new_pass_repeat.getText().toString().length()==6){	
	   					    String[]x={Base64.usuario+"&"+Base64.chave+"&"+old_pass.getText()+"?"+new_pass.getText()+"?"};
			   				String x_encoded=Base64.criptografa(x);
			   		    	String endereco="https://localhost/sco_and_1.php?"+x_encoded;
			   				String resp = null;
			   				
			   				try {  
			   			        // Create a URL for the desired page  
			   					URL url = new URL(endereco);
			   			        // Read all the text returned by the server  
			   			        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));  
			   			        String str;  
			   			        while ((str = in.readLine()) != null) {  
			   			            // str is one line of text; readLine()  
			   			            if (str.startsWith("1")){
			   			            	resp=str.substring(2);
			   			            }
			   			            else 
			   			            	if (str.startsWith("0"))
			   							    resp="erro";
			   			        }  
			   			        in.close();  
			   			    } 
			   				catch (MalformedURLException e) {  
			   			    } 
			   				catch (IOException e) {  
			   			    }
			   				if (resp.equalsIgnoreCase("ok")){
			   					Toast.makeText(CasaOnlineAppActivity.this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show();
							    Base64.senha=new_pass.getText().toString(); 
							    CarregaTela1_2(Base64.usuario,Base64.senha,Base64.chave,Base64.nome, Base64.data, Base64.hora);
			   				}
			   				else {
			   					if (resp.equalsIgnoreCase("erro")){
			   						Toast.makeText(CasaOnlineAppActivity.this, "Dados incorretos...", Toast.LENGTH_SHORT).show();
			   					}
			   				}
			   			}
			   			else {
			   		    	Toast.makeText(CasaOnlineAppActivity.this, "A senha deve ter 6 caracteres...", Toast.LENGTH_SHORT).show();
				   		}
			   						
			   	    }
	   				else 
	   					if (!(new_pass.getText().toString().equalsIgnoreCase(new_pass_repeat.getText().toString()))){
	   					    Toast.makeText(CasaOnlineAppActivity.this, "As campos da senha nova estão com conteúdos diferentes.", Toast.LENGTH_SHORT).show();
	   				    }
	   				
	   			}
	   			
	            });
	        	
	        	Button cancel = (Button) findViewById (R.tela_config.cancela);
	        	cancel.setOnClickListener(new View.OnClickListener(){
		   			public void onClick(View arg0) {
		   				CarregaTela1_2(Base64.usuario,Base64.senha,Base64.chave,Base64.nome, Base64.data, Base64.hora);
		   			}
	        	});
	        }
	    	else {
	    		alertDialog.setTitle("Atenção");
	        	alertDialog.setMessage(getText(R.string.info_erro_config));
	        	alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
				      
				   }
				});
	        	alertDialog.show();
	    	}
        	return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
    
	public long calc_crc(String str){
		// get bytes from string
		byte bytes[] = str.getBytes();
		 
		Checksum checksum = new CRC32();
		
		// update the current checksum with the specified array of bytes
		checksum.update(bytes, 0, bytes.length);
		 
		// get the current checksum value
		long checksumValue = checksum.getValue();
		 
		return checksumValue;
	}
	
    private void gravarArquivo(String conteudo) {
        try {
            FileOutputStream outStream = null;
            try {
                FileOutputStream out = this.openFileOutput(arquivo, Context.MODE_APPEND);
                out.write(conteudo.getBytes());
                out.write("\n".getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private String lerArquivo() {
        String conteudo="";
    	try {
            File file = this.getFilesDir();
            File textfile = new File(file + "/" + arquivo);
            FileInputStream input = this.openFileInput(arquivo);
            byte[] buffer = new byte[(int) textfile.length()];
            input.read(buffer);
            conteudo=new String(buffer);
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return conteudo;
    }
}