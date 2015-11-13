package com.example.IpSensor;

import java.io.IOException;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.*;
import org.xmlpull.v1.XmlPullParserException;

public class PingWrapperDAO {
	private static final String URL = "http://192.168.21.34:8080/MongoWS/services/MongoDAO?wsdl";
	private static final String NAMESPACE = "http://exemploMongo.com.br";
	private static final String INSERIR = "inserirPing";
	
	public boolean inserirPing(Ping ping) throws HttpResponseException, IOException, XmlPullParserException {
		
		
		SoapObject inserirPing = new SoapObject(NAMESPACE, INSERIR); 
		
		SoapObject p = new SoapObject(NAMESPACE, "ping");
		p.addProperty("url", ping.getUrl());
		p.addProperty("hops", ping.getHops());
		
		inserirPing.addSoapObject(p); 
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(inserirPing);
		envelope.implicitTypes = true;
		
			HttpTransportSE http = new HttpTransportSE(URL);
			http.call("urn:" + INSERIR, envelope);
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			return Boolean.parseBoolean(resposta.toString());
			
		
	}
	
}
