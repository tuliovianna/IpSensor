package com.example.IpSensor;

/**
 * Copyright (C) 2015 LabEPI
 *
 * @author Marcos Tulio de Lima Vianna
 * @author Joao Paulo de Souza Medeiros
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * An wrapper for the ping Unix network utility.
 */
public class PingWrapper {

    //
    private String command; 

    //
    private String url;

    //
    private int amount;

    //
    private int interval;

    //
    private int ttl;
    
    //
    private ArrayList<Long> rtt;

    //
    private int wait;

    //
    private ArrayList<String> hops;

    //
    private ArrayList<String> ips;
        
    //
    private ArrayList<String> domains;
   
    /**
     * Constructor.
     * @param url remote host URL
     */
    public PingWrapper(String url) throws UnknownHostException {
        this.url = url;
        this.amount = 1;
        this.interval = 0;
        this.ttl = 1;
        this.rtt = new ArrayList<Long>();
        this.wait = 1;
        this.hops = new ArrayList<String>();
        this.ips = new ArrayList<String>();
        this.domains = new ArrayList<String>();
    }

    /**
     * The main method.
     * @param args the arguments passed through command line
     */

    /**
     * Print a list of the data captured with ping.
     */
    public void printList() {
        for (String l: this.hops) {
            System.out.println(l);
        }
    }

    /**
     * Execute a ping command and returns the result.
     * @param command the command to be executed
     * @return the result of running the ping command
     * @throws errors caused by failure text reading
     */
    public String ping(String command) {
        StringBuffer output = new StringBuffer();
        Process process;
        String line = "";

        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String capture = output.toString();
        return capture;
    }

    public void traceroute() {
        String command;
        String output;
        long init;  
        long end;
        long diff;

        for (int i = this.ttl; i <= 30; i++) {
            command = "ping " + "-c " + this.amount + " -t " + i + " -W " + this.wait + " " + this.url;
            init = System.currentTimeMillis();
            output = ping(command);
            end = System.currentTimeMillis();
            this.rtt.add(end - init); 
            hops.add(output + " rtt: " + this.rtt + "\n");
            init = 0;
            if (stop(output, url)) {
                break;
            }
        }
    }

    /**
     * Resolve the IP address of an URL.
     * @param url the name to be resolved
     * @return the IP address from an URL
     * @throws errors caused by the failure to resolve domain names
     */
    public String findIp(String url) {
        String ip = "";

        try {
              InetAddress target = InetAddress.getByName(url);
              ip = target.getHostAddress();
        } catch(UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * Checks if a traceroute output contains the target.
     * @param output  the output string
     * @param address the IP address of the target
     * @return true if the target is in the output string, false otherwise
     */
    public boolean stop(String output, String address) {
        String target = findIp(address);
        
        if(output.contains(target) && output.contains("from") || output.contains(address) && output.contains("from")) {
            return true;
        }
        return false;
    }

    /**
    * Formats the data obtained for a default output
    */
    public String outputFormat() {
        String regexIp = "\\d{1,3}(\\.\\d{1,3}){3}";
        String regexAddress = "\\b((?=[a-z0-9-]{1,63}\\.)[a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z]{2,63}\\b";
        int k = 1; 
        StringBuffer output = new StringBuffer();
        int count = 0;

        Pattern patternIp = Pattern.compile(regexIp);
        Pattern patternAddress = Pattern.compile(regexAddress);

        for (String s: this.hops) {
            StringTokenizer st = new StringTokenizer(s, "\n");
            
            while (st.hasMoreTokens()) {
                String line = st.nextToken();
                Matcher matcher = patternIp.matcher(line);

                if (matcher.find() &&  (line.contains("From"))) {
                    this.ips.add(k + " - " + matcher.group());
                    k++;
                    }
                if (line.contains("received") && !line.contains("+1 errors") && !line.contains("1 received") && !matcher.find()) {
                    this.ips.add(k + " - " + "***");
                    k++;
                }
                if (stop(line, url)) {
                    k++;
                    this.ips.add(k - 1 + " - " + findIp(url) + " "); 
                } 
            }         
        }
         
        for (String s: this.hops) {
            StringTokenizer ts = new StringTokenizer(s);
            

            while (ts.hasMoreTokens()) {
                String line = ts.nextToken();
                Matcher matcher2 = patternAddress.matcher(line); 

                if (matcher2.find() && !matcher2.group().equals(this.url))  {
                    this.domains.add(matcher2.group()); 
                } 
                else {
                    if (line.contains("icmp") || line.equals(" ")) {
                        this.domains.add("***"); 
                    }   
                }   
            }
        }
        
        for (int i = 0; i < this.ips.size(); i++) {
            //System.out.println(this.ips.get(i) + "  ---  " + this.domains.get(i)); 
            //System.out.println(i+1 + " - " + this.ips.get(i));
        }
        
        for(int i = 0; i < this.ips.size(); i++) {
            //output.append(this.ips.get(i) + "  rtt: " + this.rtt.get(i) +" ms" + "\n"); 
        }
        
        
        for(String s: this.ips) {
        	output.append(s + "  rtt: " + this.rtt.get(count) +" ms" + "\n");
        	count++;
        }
        
        String capture = output.toString();
        
        return capture; 
    } 

    /**
    * @return command the command to be executed
    */
    public String getCommand() {
        return this.command;
    }

    /**
    * @return stop after sending count echo_requests packets
    */
    public int getAmount() {
        return this.amount;
    }

    /**
    * @return target computer
    */
    public String getUrl() {
        return this.url;
    }

    /**
    * @return timeout in seconds to send the next packet
    */
    public int getInterval() {
        return this.interval;
    }

    /**
    * @return time to wait for a response, in seconds.
    */
    public int getWait() {
        return this.wait;
    }

    /**
    * @return set the IP Time to Live
    */
    public int getTtl() {
        return this.ttl;
    }

    public ArrayList<Long> getRtt() {
        return rtt;
    }
    
    /**
    * @return list of captured data traceroute 
    */
    public ArrayList<String> getHops() {
        return this.hops;
    }

    public ArrayList<String> getIps() {
        return this.ips;
    }

    public ArrayList<String> getDomains() {
        return this.domains;
    }

    /**
    * @param command the command to be executed
    */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
    * @param stop after sending count echo_requests packets
    */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
    * @param target computer
    */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
    * @param timeout in seconds to send the next packet
    */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
    * @param time to wait for a response, in seconds.
    */
    public void setWait(int wait) {
        this.wait = wait;
    }

    /**
    * @param set the IP Time to Live
    */
    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public void setRtt(ArrayList<Long> rtt) {
        this.rtt = rtt;
    }
    
    /**
    * @param list of captured data traceroute 
    */
    public void setHops(ArrayList<String> hops) {
        this.hops = hops;
    }

  
}
