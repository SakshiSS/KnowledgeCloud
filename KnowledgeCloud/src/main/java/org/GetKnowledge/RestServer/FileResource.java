package org.GetKnowledge.RestServer;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by Divya on 4/21/2017.
 */
@Path("file")
public class FileResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @Context
    ServletContext servletContext;

    @POST
    @Path("/send")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        System.out.println("File uploadFile");

        String filePath = servletContext.getRealPath("serverUploads");

//        try {
//
//            //createFolderIfNotExists("d:/op");
//        } catch (SecurityException se) {
//            return Response.status(500)
//                    .entity("Can not create destination folder on server")
//                    .build();
//        }
//        //String[] filename1=fileDetail.getFileName().split("/");
//        System.out.println("name"+filename1[0]);
//        System.out.println("");
        String FileLocation = filePath+File.separator+ fileDetail.getFileName();
        System.out.println("File Location"+FileLocation);
        try {
            saveToFile(uploadedInputStream,FileLocation);

        } catch (IOException e) {
            return Response.status(500).entity("Can not save file").build();
        }

        return Response.status(200)
                .entity("File saved to " + FileLocation).build();
    }

    private void saveToFile(InputStream inStream, String target)
            throws IOException {
OutputStream out = null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		FileWriter fileWriter =
                new FileWriter(target);
		BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);
		String op="";
		while((op=br.readLine())!=null){
			bufferedWriter.write(op);
		}//while
		
		bufferedWriter.close();
		
    }

    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }




    @GET
    @Path("/get/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)

    public Response downloadFilebyPath(@PathParam("filename") String fileName) {
        System.out.println("FileName"+fileName);

        return download(fileName);

    }//downloadFilebyPath

    private Response download(String fileName) {

        System.out.println("FileName"+fileName);
        String fileLocation = "C:/Users/Sakshi Sachdev/Desktop/Test/"+ fileName;
        File file = new File(fileLocation);

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"result.txt\"");

        return response.build();
    }//download


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}
