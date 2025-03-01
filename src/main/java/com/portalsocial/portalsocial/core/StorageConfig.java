package com.portalsocial.portalsocial.core;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.portalsocial.portalsocial.service.FotoStorageService;
import com.portalsocial.portalsocial.service.storage.LocalFotoStorageService;
import com.portalsocial.portalsocial.service.storage.S3FotoStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    private StorageProperties storageProperties;

    public StorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    AmazonS3 amazonS3() {

        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegiao())
                .build();

    }

    @Bean
    FotoStorageService fotoStorageService() {
        if ( StorageProperties.TipoStorage.S3.equals(storageProperties.getTipo()) ){
            return new S3FotoStorageService(amazonS3(), storageProperties );
        }
        return new LocalFotoStorageService(storageProperties);

    }

}
