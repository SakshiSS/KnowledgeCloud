package org.GetKnowledge.Persistance;


import org.apache.jena.base.Sys;
import org.glassfish.jersey.client.ClientConfig;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;


import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;
import java.io.*;

public class RestClient {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		RestClient r=new RestClient();
//	       r.uploadFile();
//		  // r.downloadFile();
//
//
//	}//main
	
	public boolean uploadFile(File file){
		 //Send File to Server
		boolean success = false;
	    Client client= ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
		 
	    final FileDataBodyPart filePart = new FileDataBodyPart("file", file);
	    FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
	    final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.field("pqr", "xyz").bodyPart(filePart);
		try {
			final WebTarget target = client.target("http://localhost:8080/KnowledgeCloud/rest/file/send");
			final Response response = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
			System.out.println("inside upload method of client");
			//Use response object to verify upload success
			System.out.println("The status of response " + response.getStatus());
			if (response.getStatus() == 200) {
				success = true;
			}


			try {
				formDataMultiPart.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				multipart.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return success;
	 
	}//File
	
	public void downloadFile(){
		
		  //Get File From Server
		
				 String httpURL = "http://localhost:8080/FileUpload/webapi/file/get/xyz.txt";
			     String responseString="";
				try {
					responseString = testDownload(httpURL);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     System.out.println("Res::"+responseString);

	}//downloadFile

	public static String testDownload(String httpURL) throws IOException {
	 	 ClientConfig clientConfig = new ClientConfig();
		 clientConfig.register(MultiPartFeature.class);
		 Client client =  ClientBuilder.newClient(clientConfig);
         client.property("accept", "application/txt");
         WebTarget webTarget = client.target(httpURL);
        
         Invocation.Builder invocationBuilder = webTarget.request();
         Response response = invocationBuilder.get();
        
         int responseCode = response.getStatus();
         System.out.println("Response code: " + responseCode);
        
       
         InputStream inputStream = response.readEntity(InputStream.class);
         String DownloadPath = "C:/Users/Sakshi Sachdev/Desktop/Result/result.txt";
         OutputStream outputStream = new FileOutputStream(DownloadPath);
         byte[] buffer = new byte[1024];
         int bytesRead; 
         while ((bytesRead = inputStream.read(buffer)) != -1) {
       	 
            outputStream.write(buffer, 0, bytesRead);
         }
         String responseString = "downloaded successfully at " + DownloadPath;
        
         return responseString;
		 
		 
	 }//test

}
