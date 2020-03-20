package com.arpit.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class IntegracaoController {

    private static final String JDBC_driver = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://192.169.40.183:3306/scplus?serverTimezone=UTC";
    private static final String JDBC_USR = "externo2";
    private static final String JDBC_PAS = "Senha@2020";
    /*
    private static final String JDBC_driver = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/scplus?serverTimezone=UTC";
    private static final String JDBC_USR = "externo2";
    private static final String JDBC_PAS = "Senha@2020";
    
    private static final String JDBC_driver = "org.postgresql.Driver";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/smartenvironment";
    private static final String JDBC_USR = "postgres";
    private static final String JDBC_PAS = "postgres";
    */
    private static final String SQL_INSERT = "INSERT INTO mensagem (cliente, endpoint, gateway, sensor, data_hora, um_medida, valor) VALUES (?,?,?,?,?,?,?);";
    private static Connection conexao;
    
    @Bean
    public static Connection getConexao() {

        try {   
			Class.forName(JDBC_driver);
			conexao = DriverManager.getConnection(JDBC_URL, JDBC_USR, JDBC_PAS); 
			if (conexao != null) {
				System.out.println("Conexao com o banco MySQL com sucesso!"); 
			}
	        
	    } catch (Exception me) {
	        if (me instanceof MqttException) {
	            System.out.println("reason " + ((MqttException) me).getReasonCode());
	        }
	        System.out.println("msg " + me.getMessage());
	        System.out.println("loc " + me.getLocalizedMessage());
	        System.out.println("cause " + me.getCause());
	        System.out.println("excep " + me);
	        me.printStackTrace();
	    }
		
        return conexao;
    }
    
    
    private static String broker = "tcp://167.114.153.23:1883";
    private static String clientId = "mosquittoinicialiserTeste";
    private static String topico = "test";
    private static int qos = 0;

    private static IMqttClient instance;
    
    @Bean
    public static IMqttClient getInstance() {
        try {
        	/*
            if (instance == null) {
            	instance = new MqttClient(broker, topico);
            }

            System.out.println(clientId + " Conectando do broker: " + broker);
            
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            if (!instance.isConnected()) {
                instance.connect(options);

            }
            */
            MemoryPersistence persistence = new MemoryPersistence();
            MqttClient clientMqtt = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            
            System.out.println(clientId + " Conectando do broker public: " + broker);
            clientMqtt.connect(connOpts);        
            System.out.println(clientId + " Conectado do broker public: " + broker);
                
                
            clientMqtt.setCallback(new MqttCallback() {
					
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {				        
		        	byte[] payload = message.getPayload();    
		            System.out.println("Menssagem: " + message.getPayload());
		        	
		            if (payload[0] != 111) {
		                System.out.println("Recebendo");
		                System.out.println("topico: " + topic);
			                
			            JSONObject val = new JSONObject(new String(message.getPayload()));
		                System.out.println(val);
						
		                String cliente = val.get("cliente").toString();
		                System.out.println(cliente);
		                String endpoint = val.get("endpoint").toString();
		                System.out.println(endpoint);
		                String gateway = val.get("gateway").toString();
		                System.out.println(gateway);
		                String sensor = val.get("sensor").toString();
		                System.out.println(sensor);
		                String data_hora = val.get("data_hora").toString();
		                System.out.println(data_hora);
		                String um_medida = val.get("um_medida").toString();
		                System.out.println(um_medida);
		                String valor = val.get("valor").toString();
		                System.out.println(valor);
			            System.out.println("");

			            float valor2 = Float.valueOf(valor);
		                
		                if (conexao != null) {                   	
			                try { 
			                    PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT);
			                    stmt.setString(1, cliente);
				                stmt.setString(2, endpoint);
				                stmt.setString(3, gateway);
				                stmt.setString(4, sensor);
				                stmt.setString(5, data_hora);
				                stmt.setString(6, um_medida);
								stmt.setFloat(7, valor2);
			                    stmt.execute();
			                    
			                    stmt.close();
			    	        } catch (Exception me) {
			    	            if (me instanceof MqttException) {
			    	                System.out.println("reason " + ((MqttException) me).getReasonCode());
			    	            }
			    	            System.out.println("msg " + me.getMessage());
			    	            System.out.println("loc " + me.getLocalizedMessage());
			    	            System.out.println("cause " + me.getCause());
			    	            System.out.println("excep " + me);
			    	            me.printStackTrace();
			    	        }
			                
				            System.out.println("Gravacao no banco MySQL com sucesso!");
		                }
		                
		            }
					
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void connectionLost(Throwable cause) {
					// TODO Auto-generated method stub
					
				}
			}); 
            while (true) {
	            clientMqtt.subscribe(topico, qos);
            } 
            
        } catch (MqttException e) {
            e.printStackTrace();
        }

        return instance;
    }

    private IntegracaoController() {

    }
    
	//@Bean
    public static void main(String[] args) {
        
    }
    
}
