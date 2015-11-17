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

/**
 * PortScan for network service discovery
 */
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
        
    /**
     * Constructor.
     * @param target to be scanned
     */
    public PortScan(String target) throws UnknownHostException {
        this.target = target;
        this.startPort = 1;
        this.endPort = 65535;
        this.ia = InetAddress.getByName(this.target);
        this.openPortList = new ArrayList<Integer>();
        this.filteredPortList = new ArrayList<Integer>();
        this.closedPortList = new ArrayList<Integer>();
        this.reservedPort = new ArrayList<Integer>();
        this.reservedPort.addAll(0, reservedPort()); 
    }

    /**
     * Scan all 65,535 ports and reports its status.
     */    
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
        
    /**
     * Scan a target in a certain range of ports.
     * @param startPort starting number of the range to scan the target
     * @param endPort final number of the range to scan the target
     */   
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
        
    /**
     * Scan the ports of the main network services.
     */ 
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
    
    /** 
     * Scan the first 1023 ports on the target.
     */      
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
    
    /**
     * Populates the result vector with the doors of the main network 
     * services.
     */    
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
    
    /**
     * @return the target name 
     */
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

    /**
     * @return list of open ports 
     */
    public ArrayList<Integer> getOpenPortList() {
        return openPortList;
    }

    /**
     * @return list port refused
     */
    public ArrayList<Integer> getClosedPortList() {
        return closedPortList;
    }

    /**
     * @return list port filtering  
     */
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
     * @return list of reserved ports
     */
    public ArrayList<Integer> getReservedPort() {
        return reservedPort;
    }

    /**
     * @param ia the ia to set
     */
    public void setIa(InetAddress ia) {
        this.ia = ia;
    }

    /**
     * @param the target name
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @param set list of open ports 
     */
    public void setOpenPortList(ArrayList<Integer> openPortList) {
        this.openPortList = openPortList;
    }

    /**
     * @param set list of refused ports
     */
    public void setClosedPortList(ArrayList<Integer> closedPortList) {
        this.closedPortList = closedPortList;
    }

    /**
     * @param set list of timeout ports
     */
    public void setFilteredPortList(ArrayList<Integer> filteredPortList) {
        this.filteredPortList = filteredPortList;
    }

    /**
     * @param set list of reserved ports
     */
    public void setReservedPort(ArrayList<Integer> reservedPort) {
        this.reservedPort = reservedPort;
    }    
}
