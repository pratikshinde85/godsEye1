/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.thirdeye_facematch;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceRecord;
import java.io.File;
//import com.amazonaws.services.rekognition.model.Image;
//import com.amazonaws.services.rekognition.model.IndexFacesRequest;
//import com.amazonaws.services.rekognition.model.IndexFacesResult;
//import com.amazonaws.services.rekognition.model.S3Object;
//import java.util.List;
//
//public class collection_add_image {
//    public static final String collectionId = "Records";
//    public static final String bucket = "godseyestorage";
//    public static final String photo = "f1-003-01.jpg";
//
//    public static void main(String[] args) throws Exception {
//
//        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
//
//        Image image = new Image()
//                .withS3Object(new S3Object()
//                .withBucket(bucket)
//                .withName(photo));
//        
//        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
//                .withImage(image)
//                .withCollectionId(collectionId)
//                .withExternalImageId(photo)
//                .withDetectionAttributes("DEFAULT");
//
//        IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);
//        
//        System.out.println("Results for " + photo);
//        System.out.println("Faces indexed:");
//        List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
//        for (FaceRecord faceRecord : faceRecords) {
//            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
//            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
//        }
//    }
//}


//new code
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.S3Object;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class collection_add_image{
    public static final String COLLECTION_ID = "Records";
    public static final String BUCKET = "godseyestorage2";
    public static final String IMAGE_FOLDER_PATH = "C:\\Users\\rutik\\Documents\\NetBeansProjects\\Project Code (forensic face sketch)\\godsEye_FaceMatch\\src\\main\\java\\com\\mycompany\\thirdeye_facematch\\faces";
    
    public static void main(String[] args) {
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
        .withRegion("us-east-1")
        .build();


        File imageFolder = new File(IMAGE_FOLDER_PATH);
        File[] imageFiles = imageFolder.listFiles();

        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                if (imageFile.isFile()) {
                    try {
                        Image image = new Image()
                                .withS3Object(new S3Object()
                                        .withBucket(BUCKET)
                                        .withName(imageFile.getName()));
                        // Replace spaces with underscores in the file name
                        System.out.println(imageFile.getName());
                        String externalImageId = imageFile.getName().replace(" ", "_");
                        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                                .withImage(image)
                                .withCollectionId(COLLECTION_ID)
                                .withExternalImageId(externalImageId)
                                .withDetectionAttributes("DEFAULT");

                        IndexFacesResult indexFacesResult = rekognitionClient.indexFaces(indexFacesRequest);

                        System.out.println("Results for " + imageFile.getName());
                        System.out.println("Faces indexed:");
                        List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
                        for (FaceRecord faceRecord : faceRecords) {
                            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
                            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("No files found in the specified folder.");
        }
    }
}