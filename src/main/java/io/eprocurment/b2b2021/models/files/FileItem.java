package io.eprocurment.b2b2021.models.files;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileItem {
    @Id
    private String id;

    private String title;
    private Boolean isCovert;

    private Integer order;
    private Integer itemPk;

    private byte[] file;

}
