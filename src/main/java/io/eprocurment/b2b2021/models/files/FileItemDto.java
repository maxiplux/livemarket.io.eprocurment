package io.eprocurment.b2b2021.models.files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileItemDto {
    private String id;

    private String title;
    private Boolean isCovert;

    private Integer order;
    private Integer itemPk;
    private String file;
}
