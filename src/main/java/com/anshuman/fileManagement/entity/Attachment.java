package com.anshuman.fileManagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;
    private String fileName;
    private String fileType;

    //@Lob is used to set the data type as blob in the mysql database
    @Lob
    private byte[] data;

    public Attachment(String fileName,String fileType,byte[] data){
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
