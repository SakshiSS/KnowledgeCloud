package org.GetKnowledge.Jena;

import freemarker.ext.util.ModelFactory;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.tdb.TDBFactory;

import java.io.*;

/**
 * Created by Divya on 4/9/2017.
 */
public class JenaCrud {

    public static void uploadRDF(File rdf, String serviceURI,String datasetName)
            throws IOException {

//        Dataset dataset = DatasetFactory.create(datasetName);
//        dataset.begin(ReadWrite.WRITE);

        //TDBFactory.createDataset(datase;tName);
        Model m = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();

//        Model m = dataset.getDefaultModel();
//        InputStream fileInputStream = new FileInputStream(rdf);
//        //Lang lang = new Lang(RDFLanguages.strLangRDFXML);
//
//        RDFDataMgr.read(m, fileInputStream, Lang.RDFXML) ;


        try (FileInputStream in = new FileInputStream(rdf)) {
            m.read(in, null, "RDF/XML");
        }

        DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);
        accessor.putModel(m);
        System.out.println("Uploaded successfully");
//        dataset.commit();
//        dataset.end();
    }//uploadRDF

    public static void getJsonFile(String serviceURI, String query,String path,String fileName) throws IOException {

        QueryExecution q1 = QueryExecutionFactory.sparqlService(serviceURI,query);
        ResultSet results1 = q1.execSelect();

        FileOutputStream fop = null;
        //File file=new File("C:/Users/Sakshi Sachdev/Desktop/jsonop");
        //String pathNew = getServletContext().getRealPath("uploads");
        File file = new File(path+fileName+".json");
        fop = new FileOutputStream(file);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outputStream, results1);
        String json = new String(outputStream.toByteArray());
        System.out.println(json);
        byte[] contentInBytes = json.getBytes();
        fop.write(contentInBytes);
        fop.flush();
        fop.close();

        System.out.println("Completed writing json file");




    }//execSelect


//    public static void main(File file) throws IOException{
//        uploadRDF(new File("C:/Users/Sakshi Sachdev/Desktop/BNBBasic_sample.RDF"),"http://localhost:3030/testupload");
//
//        getJsonFile("http://localhost:3030/testupload/query",
//                "SELECT * WHERE { ?subject ?predicate ?object}");
//		/*execSelectAndPrint(
//				"http://localhost:3030/Prism/query",
//                 "SELECT * WHERE { ?subject ?predicate ?object} limit 2 offset 3");*/
//        //execSelectAndPrint("http://localhost:3030/Prism/query","SELECT (count(*) AS ?count) { ?s ?p ?o }");
//
//
//    }//main
}
