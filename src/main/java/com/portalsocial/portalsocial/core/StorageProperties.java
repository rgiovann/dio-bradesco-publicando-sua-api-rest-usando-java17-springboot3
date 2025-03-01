package com.portalsocial.portalsocial.core;
import java.nio.file.Path;

import com.amazonaws.regions.Regions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Component
@ConfigurationProperties("portalsocial.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TipoStorage tipo = TipoStorage.LOCAL;

    public enum TipoStorage{
        LOCAL, S3
    }


    @Setter
    @Getter
    public class Local
    {
        private Path diretorioFotos;
    }

    @Setter
    @Getter
    public class S3
    {
        private String idChaveAcesso;
        private String chaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private Regions regiao;
        private String diretorioFotos;


    }

}