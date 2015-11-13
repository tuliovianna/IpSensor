package com.example.IpSensor;

/**
 * Copyright (C) 2015 LabEPI
 *
 * @author Marcos Tulio de Lima Vianna
 * @author Joao Paulo de Souza Medeiros
 */

import java.net.*;
import java.io.IOException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

    public class PortScan {

        //
        private String target;

        // 
        private int startPort;

        //
        private int endPort;

        //
        private InetAddress ia; 
        
        // 
        private String hostName;
        
        //
        private ArrayList<Integer> openPortList;

        //
        private ArrayList<Integer> closedPortList;
        
        //
        private ArrayList<Integer> filteredPortList;
        
        //
        private ArrayList<Integer> reservedPort;
        
        public PortScan(String target) throws UnknownHostException {
            this.target = target;
            this.startPort = 1;
            this.endPort = 65535;
            this.ia = InetAddress.getByName(this.target);
            //this.hostName = ia.getHostName(); 
            this.openPortList = new ArrayList<Integer>();
            this.filteredPortList = new ArrayList<Integer>();
            this.closedPortList = new ArrayList<Integer>();
            this.reservedPort = new ArrayList<Integer>();
            this.reservedPort.addAll(0, reservedPort()); 
        }

        public static void main(String[] args) throws UnknownHostException {
            Scanner reader = new Scanner(System.in);
            System.out.print("Target: ");
            String target = reader.next();
            PortScan scan = new PortScan(target);
            scan.scanMainPort();
            
            
        }
        
        public String scanAll() {
        	StringBuffer output = new StringBuffer();
        	for (int port = this.startPort; port <= this.endPort; port++) {
            	try {
                	Socket socket = new Socket(); 
					socket.connect(new InetSocketAddress(this.ia, port), 1000);
					socket.close();
					System.out.println("Port " + port + " open"); 
					openPortList.add(port);
					output.append("Port " + port + " open" + "\n");
				} catch (IOException e) {
					if (e.getMessage().contains("ECONNREFUSED")) {
                        System.out.println("Port " + port + " closed");
                        closedPortList.add(port);
                        output.append("Port " + port + " closed" + "\n");
					} else {
						System.out.println("Port " + port + " filtered");
                        filteredPortList.add(port);
                        output.append("Port " + port + " filtered" + "\n");
					}
				 }  
            }
            
            String capture = output.toString();
            return capture; 
        }
        
        public String scanRangePort(int startPort, int endPort) {
        	StringBuffer output = new StringBuffer();
        	for (int port = startPort; port <= endPort; port++) {
            	try {
                	Socket socket = new Socket(); 
					socket.connect(new InetSocketAddress(this.ia, port), 1000);
					socket.close();
					System.out.println("Port " + port + " open"); 
					openPortList.add(port);
					output.append("Port " + port + " open" + "\n");
				} catch (IOException e) {
					if (e.getMessage().contains("ECONNREFUSED")) {
                        System.out.println("Port " + port + " closed");
                        closedPortList.add(port);
                        output.append("Port " + port + " closed" + "\n");
					} else {
						System.out.println("Port " + port + " filtered");
                        filteredPortList.add(port);
                        output.append("Port " + port + " filtered" + "\n");
					}
				}  
           }
            
            String capture = output.toString();
            return capture; 
        }
        
        public String scanMainPort() {
        	 StringBuffer output = new StringBuffer();
        	 for (Integer port: this.reservedPort) {      
                try {
                    Socket socket = new Socket(); 
    				socket.connect(new InetSocketAddress(this.ia, port), 1000);
    				socket.close();
    				System.out.println("Port " + port + " open"); 
    				openPortList.add(port);
    				output.append("Port " + port + " open" + "\n");
    			} catch (IOException e) {
    				if (e.getMessage().contains("ECONNREFUSED")) {
    					System.out.println("Port " + port + " closed");
    					closedPortList.add(port);
    					output.append("Port " + port + " closed" + "\n");
    				} else {
    					System.out.println("Port " + port + " filtered");
                        filteredPortList.add(port);
                        output.append("Port " + port + " filtered" + "\n");
    				}
    			}
        	 }
        	 String capture = output.toString();
		     return capture;
        }
        
        public String scanReservedPort() {
            StringBuffer output = new StringBuffer();
            for (int port = 1; port <= 50; port++) {      
                try {
                	Socket socket = new Socket(); 
					socket.connect(new InetSocketAddress(this.ia, port), 1000);
					socket.close();
					System.out.println("Port " + port + " open"); 
					openPortList.add(port);
					output.append("Port " + port + " open" + "\n");
				} catch (IOException e) {
					if (e.getMessage().contains("ECONNREFUSED")) {
                        System.out.println("Port " + port + " closed");
                        closedPortList.add(port);
                        output.append("Port " + port + " closed" + "\n");
					} else {
						System.out.println("Port " + port + " filtered");
                        filteredPortList.add(port);
                        output.append("Port " + port + " filtered" + "\n");
					}
				}  
           }
            
            String capture = output.toString();
            return capture; 
        }
        
        public ArrayList<Integer> reservedPort() {
            ArrayList<Integer> result = new ArrayList<Integer>();  
            result.add(80);   // HTTP
            result.add(631);   // Protocolo de impressão na internet     
            result.add(161);   // Protocolo simples de gerenciamento de rede
            result.add(137);   // Serviço NetBIOS 
            result.add(123);   // Protocolo de tempo na rede    
            result.add(138);   // Protocolo de tempo na rede    
            result.add(1434);   // Monitor Microsoft SQL    
            result.add(445);  // Serviços de diretório Microsoft  
            result.add(135);  // Microsoft RPC Serviço de localização
            result.add(67);  // DHCP (Protocolo de configuração dinâmica do Host)
            
            return result;
        }
 
    public String getHostName() {
        return hostName;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the startPort
     */
    public int getStartPort() {
        return startPort;
    }

    /**
     * @param startPort the startPort to set
     */
    public void setStartPort(int startPort) {
        this.startPort = startPort;
    }

    /**
     * @return the endPort
     */
    public int getEndPort() {
        return endPort;
    }

    public ArrayList<Integer> getOpenPortList() {
        return openPortList;
    }

    public ArrayList<Integer> getClosedPortList() {
        return closedPortList;
    }

    public ArrayList<Integer> getFilteredPortList() {
        return filteredPortList;
    }
    
    /**
     * @param endPort the endPort to set
     */
    public void setEndPort(int endPort) {
        this.endPort = endPort;
    }

    /**
     * @return the ia
     */
    public InetAddress getIa() {
        return ia;
    }

    /**
     * @param ia the ia to set
     */
    public void setIa(InetAddress ia) {
        this.ia = ia;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setOpenPortList(ArrayList<Integer> openPortList) {
        this.openPortList = openPortList;
    }

    public void setClosedPortList(ArrayList<Integer> closedPortList) {
        this.closedPortList = closedPortList;
    }

    public void setFilteredPortList(ArrayList<Integer> filteredPortList) {
        this.filteredPortList = filteredPortList;
    }

    public void setReservedPort(ArrayList<Integer> reservedPort) {
        this.reservedPort = reservedPort;
    }

    public ArrayList<Integer> getReservedPort() {
        return reservedPort;
    }
    
    
}
